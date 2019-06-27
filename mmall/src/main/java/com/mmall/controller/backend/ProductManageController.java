package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName ProductManageController
 * @Description TODO
 * @Author Euraxluo
 * @Date 19-1-1 下午2:17
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;
    /**
     * @description 增加,更新产品接口
     * @param [session, product]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("save.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse productSave(HttpSession session, Product product){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * @description 修改产品销售状态
     * @param [session, productId, status]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
           return iProductService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * @description 获取商品详情
     * @param [session, productId]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("detail.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse getDetail(HttpSession session,Integer productId){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * @description 后台查询结果分页接口list.do
     * @param [session, pageNum, pageSize]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("list.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //动态分页
            return iProductService.getProductList(pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * @description 后台产品search+分页
     * @param [session, productName, productId, pageNum, pageSize]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("search.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限"); }
    }

    /**
     * @description 文件上传
     * @param [session, file, request]
     * @return com.mmall.common.ServerResponse
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("upload.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");//获取相对路径
            String targetFileName = iFileService.upload(file,path);//通过service上传
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;//组装成前端可用的路径,注意配置文件中,末尾需要加反斜槓
            //使用map组装返回结果数据
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限"); }
    }

    /**
     * @description 按照前端插件simditor的要求构造我们的富文本上传接口
     * @param [session, file, request, response]
     * @return java.util.Map
     * @author Euraxluo
     * @date 19-1-1
     */
    @RequestMapping("richtest_img_upload.do")
    @ResponseBody//使用springmvc将返回值序列化为json
    public Map richtestImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        //检查用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登陆管理员");
            return resultMap;
        }
        //检验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //副文本中对于返回值有自己的要求,我们要求使用的时simditor,所以按照simditor的要求来进行返回
            /*
             * {
             *  "success":true/false
             *  "msg":"error_message"
             *  "file_path":"[real_file_path]"
             * }
             */
            String path = request.getSession().getServletContext().getRealPath("upload");//获取相对路径
            String targetFileName = iFileService.upload(file,path);//通过service上传
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;//组装成前端可用的路径,注意配置文件中,末尾需要加反斜槓
            //使用map组装返回结果数据
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("msg","无权限操作,需要管理员权限");
            return resultMap;
        }
    }
}
