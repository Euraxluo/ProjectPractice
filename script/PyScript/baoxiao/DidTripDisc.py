#百度aip配置
# APP_ID = '17475392'
# API_KEY = 'MPf0GrIRIFBhrpwcPMamoGgw'
# SECRET_KEY = 'NYHaU9HV90QIz7GNNdBnzXGsoNDVkdnX'

APP_ID = '17472558'
API_KEY = '6oWy7OxMshmfN7uIso4b2p7N'
SECRET_KEY = 'VTHQ0aLGpfG7e42HnaYZBxPjTasnCDZS'

# APP_ID = '15369192'
# API_KEY = 'XzG70ll972Prnt3mcXavTGkS'
# SECRET_KEY = 'NkUpzqlz2uwr1lGKZTIOYWjNyRbyfxMW'

pdf_dir = './pdf' #path to pdfs dir
output_jpg_dir = "./bxd_jpg" #path to output jpgs dir
shenpi_data = "./shenpi.txt" #path to final generation xingchengdan
print("请新建["+pdf_dir+"]目录,并放入你的行程单PDF文件")
print("请保持目录["+output_jpg_dir+"]为空")
print("请保持文件["+shenpi_data+"]为空")

import cv2# pip install opencv-python
from aip import AipOcr# pip install baidu-aip
import sys, fitz, os# pip install PyMuPDF
from collections import OrderedDict
import time
import copy

def get_file_content(filePath):
    with open(filePath, 'rb') as fp:
        return fp.read()
def Img2Txt(fname):
    if(fname[-8:]=="_res.jpg"):
        return
    client = AipOcr(APP_ID, API_KEY, SECRET_KEY)
    image = get_file_content(fname)
    results = client.accurate(image,options={'recognize_granularity':'small'})
    if ("error_code" in results )and results["error_code"]==17:
        print("\n超过每天的免费额度\n")
        return
    else:
        results = results["words_result"] #有其他字段
    img = cv2.imread(fname)#底图
    for result in results:
        flag=''
        data=''
        time=''
        start=''
        end=''
        money=''
        for char in  result["chars"]:
            location = char["location"]
            if location["left"]<440:#小于440的(日期前)丢弃
                flag='\n'
                continue
            elif location["left"]<570:#日期前加空格
                data = data+char["char"]
                continue
            elif location["left"]<685:#时间前加空格
                time = time+char["char"]
                continue
            elif location["left"]<920:#市区信息,丢弃
                continue
            elif location["left"]<1500:#起点
                start = start+char["char"]
                continue
            elif location["left"]<2230:#终点
                end = end+char["char"]
                continue
            elif location["left"]<2400:#里程
                continue
            else:
                money = money+char["char"]
                continue
        data=data[:2]+"-"+data[-2:] if ("-" not in data) and (data) else data
        time=time[:2]+":"+time[-2:] if (":" not in time) and (time) else time
        zhishi='---' if start else ''
        print(f"{flag} {data} {time} {start} {zhishi} {end} {money}",end=' ')
        cv2.rectangle(img, (result["location"]["left"],result["location"]["top"]), (result["location"]["left"]+result["location"]["width"],result["location"]["top"]+result["location"]["height"]), (0,255,0), 2)
    cv2.imwrite(fname[:-4]+"_res.jpg", img)
def pyMuPDF_fitz(pdfPath, imagePath):
    pdfDoc = fitz.open(pdfPath)
    for pg in range(pdfDoc.pageCount):
        page = pdfDoc[pg]
        rotate = int(0)
        mat = fitz.Matrix(5,5)# 在每个方向缩放因子2
        rect = page.rect# 页面的矩形
        mp = rect.tl + (rect.br - rect.tl) * 0.208 # 计算y坐标的比例
        mp.x=0
        br = rect.br#br是页面的右下角
        br.y = br.y-100#去掉底部的页码
        clip = fitz.Rect(mp, br)# 我们想要的剪切区域
        if(pg==0):#第一页需要多切一点
            zs = rect.tl + (rect.br - rect.tl) * 0.3695 # 计算y坐标的比例
            zs.x=0
            clip = fitz.Rect(zs, br)
        pix = page.getPixmap(matrix = mat, clip = clip)
        if not os.path.exists(imagePath):#判断存放图片的文件夹是否存在
            os.makedirs(imagePath) # 若图片文件夹不存在就创建
        image_name = imagePath+'/'+pdfPath[-6:-4]+'bxd_%s.jpg' % pg
        pix.writePNG(image_name)#将图片写入指定的文件夹内
        print("outPut image Path="+image_name)
def listOfDir(dirN):
    if not os.path.exists(dirN):
        print("文件夹:"+dirN+" 不存在,将创建")
        os.mkdir(dirN)
    for i in os.listdir(dirN):
        jpg_path = os.path.join(dirN,i)
        yield jpg_path
def genXinc(path2xinc):
    with open(path2xinc,encoding='UTF-8') as f:
        for line in f.readlines():
            yield line
def genshenPi(total,line,pose={},end='元'):
    if line == '\n':
        return
    line = line.replace('  ',' ')
    fields = line.split(' ')
    if len(total) >0 and fields[1] not in total :
        pass
        # print(list(total.keys())[-1],":",total[list(total.keys())[-1]],end='\n\n')
    for field in fields:
        if field=='\n' or field=='':
            continue
        print
        for p,n_p in pose.items():
            if p in field:#如果包含这个key,那么将这个field换成values
                field=n_p
        print(field,end=' ')
    #如果这里有自己加的那些东西,只需要记得在最后一项金额的后面再加一个空格
    total[fields[1]]=round(float(total[fields[1]])+float(fields[-2]),2) if fields[1] in total else float(fields[-2])
    end = '' if end == '\n' else end
    print(end)
    
class Overwrite(object):
    def __init__(self, filename="log.txt",std=sys.stdout):
        self.log = open(filename, "a",encoding='UTF-8')
        self.terminal = std

    def write(self, message):
        self.terminal.write(message)
        self.log.write(message)
    def close(self):
        self.log.close()

    def flush(self):
        pass
def gen_xcd(shenpi_data):
    for pdf in listOfDir(pdf_dir):#pdf转图片
        pyMuPDF_fitz(pdf,output_jpg_dir)
    path = os.path.abspath(os.path.dirname(__file__))
    std = sys.stdout #原始的输出流
    sys.stdout = Overwrite(filename=shenpi_data,std=std) #重写的输出流
    for jpg in listOfDir(output_jpg_dir):
        Img2Txt(jpg)
    sys.stdout.close()
    sys.stdout=std
def gen_spb(shenpi_data,pose={}):
    tmp_shenpi_data = shenpi_data[:-4] +'_tmp.txt'
    std = sys.stdout #原始的输出流
    sys.stdout = Overwrite(filename=tmp_shenpi_data,std=std) #更新输出流
    total = OrderedDict()
    for line in genXinc(shenpi_data):
        genshenPi(total,line,pose=pose,end='元')
    # print(list(total.keys())[-1],":",total[list(total.keys())[-1]],end='\n\n')
    print("total :",round(sum([i for i in total.values()]),2))
    sys.stdout.close()
    sys.stdout=std
    return tmp_shenpi_data
def running(pose):
    gen_xcd(shenpi_data)
    tmp_shenpi_data = gen_spb(shenpi_data,pose=pose)
    os.remove(shenpi_data)
    os.rename(tmp_shenpi_data,shenpi_data)

if __name__ == "__main__":

    #Useage:
    # 1.pipenv shell
    # 2.python .\DidTripDisc.py

    #添加了这个配置会将有关键词的地点替换为你的value
    pose={
        "汉庭":"安亭汉庭酒店",
        "创新港":"创新港",
        "虹桥":"虹桥办公室",
        "啤酒":"安亭汉庭酒店",
        "博览":"博览园"
    }
    running(pose)

    #你可以在gen_xcd(shenpi_data)生成行程数据后对其进行修改添加
    #但是记得再次运行需要将gen_xcd(shenpi_data)注释掉
    ### gen_xcd(shenpi_data)
    

    ### 添加行程
    #添加的格式一般为:{日期} {时间} {出发点} --- {终点} {金额} { }
    #注意格式的最后一个字符需要以空格结尾
    #示例:{1007 1212 出发点 ==> 终点 14.12 }或者{10-07 12:12 出发点 ==> 终点 14.12 }


    #添加新的行程信息后再把上面的gen_xcd(shenpi_data)注释掉
    #取消下一行的tmp_shenpi_data = gen_spb(shenpi_data,pose=pose)的注释再次运行

    ### tmp_shenpi_data = gen_spb(shenpi_data,pose=pose)


    ### os.remove(shenpi_data)
    ### os.rename(tmp_shenpi_data,shenpi_data)
    