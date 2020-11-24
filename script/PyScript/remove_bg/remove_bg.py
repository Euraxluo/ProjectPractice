from removebg import RemoveBg
import os
rmbg = RemoveBg("4cMvXG5MDXyv2aujJpwMfPH6","error.log")
path = '%s\picture'%os.getcwd()
for pic in os.listdir(path):
    rmbg.remove_background_from_img_file("%s\%s"%(path,pic))