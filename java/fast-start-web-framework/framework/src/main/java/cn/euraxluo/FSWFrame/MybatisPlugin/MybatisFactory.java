package cn.euraxluo.FSWFrame.MybatisPlugin;

import cn.euraxluo.FSWFrame.log.LogFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.MybatisPlugin
 * MybatisFactory
 * 2019/11/26 13:46
 * author:Euraxluo
 * TODO
 */
public class MybatisFactory {
    private static SqlSession sqlSession;
    private static MapperRegistry registry;
    private static Logger logger = LogFactory.getLogger();
    static {
        try {
//            ResourceBundle.getBundle("config");
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSession = new SqlSessionFactoryBuilder().build(inputStream).openSession(true);
            registry =  sqlSession.getConfiguration().getMapperRegistry();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static SqlSession getSession(){
        return sqlSession;
    }

    public static Object getMapper(Class<?> cls){
        if (!registry.hasMapper(cls) && cls.isInterface()){
            registry.addMapper(cls);
            logger.info(LogFactory.fs("Mybatis Registry Factory add Mapper Class{mapper}",cls.getName()));

        }
        return registry.getMapper(cls,sqlSession);
    }
}
