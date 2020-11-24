package cn.euraxluo.FSWFrame.core;

import cn.euraxluo.FSWFrame.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.stream.Collector;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.core
 * ClassScanner
 * 2019/11/23 13:08
 * author:Euraxluo
 * TODO
 */
public class ClassScanner {
    private static Logger logger = LogFactory.getLogger();
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String packagePath = packageName.replace(".","/");
        //init Class Loader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);
        int classListSize = 0;
        while (resources.hasMoreElements()){
            classListSize = classList.size();
            URL resource = resources.nextElement();
            if(resource.getProtocol().contains("jar")){
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath,packagePath));
            }else {
                File file = new File(resource.getPath());
                classList.addAll(getClassesFromFile(file,packagePath));

            }
        }
        logger.info(LogFactory.fs("Class Scanner scan class{num}",classListSize));
        return classList;
    }

    /**
     * get classes from file(run application to use ide)
     * @param file
     * @param packagePath
     * @return
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClassesFromFile(File file,String packagePath) throws ClassNotFoundException {
        int packageLen = packagePath.length();
        String classPathPrefix = file.toString().substring(0,(file.toString().length()-packageLen));
        List<File> files = new ArrayList<>(Collections.nCopies(1,file));
        List<Class<?>> classes = new ArrayList<>();
        while (files.size()!=0){
            File scanFile = files.remove(0);//remove the frist
            if (scanFile.isDirectory()){
                List<File> fileList = Arrays.asList(scanFile.listFiles());
                files.addAll(fileList);
            }else {
                String fileFullName = scanFile.getAbsolutePath();
                if (fileFullName.endsWith(".class")){
                    String entryName =  fileFullName.replace(File.separator,"/") .substring(classPathPrefix.length(),fileFullName.length());
                    if(entryName!=null && entryName.startsWith(packagePath) && entryName.endsWith(".class")){
                        String classFullName = entryName.replace("/",".")
                                .substring(0,entryName.length()-6);// "/" to "." delete .class
                        classes.add(Class.forName(classFullName));
                    }
                }
            }
        }
        return classes;
    }


    /**
     * get classes from jar(run jar to use)
     * @param jarFilePath
     * @param packagePath
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClassesFromJar(String jarFilePath,String packagePath) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        //enum jar entries
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            if(entryName!=null && entryName.startsWith(packagePath) && entryName.endsWith(".class")){
                String classFullName = entryName.replace("/",".")
                        .substring(0,entryName.length()-6);// "/" to "." delete .class
                classes.add(Class.forName(classFullName));
            }
        }
        return classes;
    }
}

