package FSWFrame.euraxluo.cn.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
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
    public static List<Class<?>> scanClasses(String packageName){
        List<Class<?>> classList = new ArrayList<>();
        String packagePath = packageName.replace(".","/");
        //init Class Loader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(packagePath);
            while (resources.hasMoreElements()){
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

