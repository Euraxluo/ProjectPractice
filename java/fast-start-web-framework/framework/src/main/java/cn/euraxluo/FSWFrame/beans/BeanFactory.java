package cn.euraxluo.FSWFrame.beans;

import cn.euraxluo.FSWFrame.MybatisPlugin.MapperScan;
import cn.euraxluo.FSWFrame.MybatisPlugin.MybatisFactory;
import cn.euraxluo.FSWFrame.core.ConfScanner;
import cn.euraxluo.FSWFrame.exception.IOCException;
import cn.euraxluo.FSWFrame.log.LogFactory;
import cn.euraxluo.FSWFrame.web.mvc.Controller;
import cn.euraxluo.FSWFrame.web.mvc.RestController;
import org.apache.ibatis.annotations.Mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * FSWFrame.euraxluo.cn.beans
 * BeanFactory
 * 2019/11/23 18:47
 * author:Euraxluo
 * TODO Users add a class for framework management
 * simple thinking:
 * doCreate() move to getBean(), get Bean need to check rely on,if getBean() success ,Cache in initBean
 */
public class BeanFactory {
    private static Logger logger = LogFactory.getLogger();
    private static String mybatisMapper = ConfScanner.getConf("mybatis.javaClientGenerator.Package");
    //Multithreading
    private static Map<Class<?>,Object> createBean = new ConcurrentHashMap<>();
    private static Map<Class<?>,Object> populateBean = new ConcurrentHashMap<>();
    private static Map<Class<?>,Object> initBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> cls){
        return initBean.get(cls);
    }
    public static void initBean(List<Class<?>> classList) throws IOCException, InstantiationException, IllegalAccessException {
        List<Class<?>> needCreate = new ArrayList<>(classList);
        while (needCreate.size() != 0 ){
            int originSize = needCreate.size();
            for (int i=0;i<needCreate.size();i++){
                if (doCreate(needCreate.get(i))){
                    needCreate.remove(i);
                }
            }
            if (needCreate.size() == originSize){
                throw new IOCException("cycle dependency");
            }
        }
        logger.info(LogFactory.fs("Bean Factory init bean{BeanSize}",initBean.size()));
    }

    /**
     * create Bean
     * @param cls
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean doCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (cls.isAnnotationPresent(MapperScan.class)){
            mybatisMapper = cls.getDeclaredAnnotation(MapperScan.class).value();
        }
        //check is need to create bean
        if (!cls.isAnnotationPresent(Bean.class)
                && !cls.isAnnotationPresent(Controller.class)
                && !cls.isAnnotationPresent(RestController.class)){
            return true;
        }
        if (cls.isAnnotation()){
            return true;
        }

        Object bean = initBean.get(cls);
        if (bean == null){
            bean = populateBean.get(cls);
            if (bean == null){
                bean = createBean.get(cls);
            }
            if (bean == null){
                //must have a null params construct func
                bean = cls.newInstance();
                //need to cache this
                createBean.put(cls,bean);
            }
        }

        //loops through each field
        for (Field field : cls.getDeclaredFields()){
            //if field have autowired , need to DI
            if (field.isAnnotationPresent(AutoWired.class)){
                Class<?> fieldType = field.getType();
                //add Mapper
                if (fieldType.getPackage().getName().equals(mybatisMapper)){
                    Object mapper =  MybatisFactory.getMapper(fieldType);
                    if (mapper != null){
                        initBean.put(fieldType,mapper);
                    }
                }
                //Get from the final bean
                Object intactBean = initBean.get(fieldType);
                if (intactBean == null){
                    //If the final one doesn't, take it from the one to be filled
                    Object missAttrBean = populateBean.get(fieldType);
                    if (missAttrBean == null){
                        //If there is no defect in those attributes, go and get the attributes that are all empty
                        Object blankAttrBean = createBean.get(fieldType);
                        if (blankAttrBean != null){
                            //Make it accessible
                            field.setAccessible(true);
                            field.set(bean,blankAttrBean);
                            //If you get it, upgrade it
                            populateBean.put(cls, bean);
                            createBean.remove(cls);
                        }
                        //Return it to false whether it is obtained or not, and make it load next time
                        return false;
                    }else {
                        //DI
                        field.setAccessible(true);//Make it accessible
                        field.set(bean,missAttrBean);
                    }
                }else {
                    //DI
                    field.setAccessible(true);//Make it accessible
                    field.set(bean,intactBean);
                }
            }
        }
        initBean.put(cls, bean);
        createBean.remove(cls);
        populateBean.remove(cls);
        return true;
    }

}
