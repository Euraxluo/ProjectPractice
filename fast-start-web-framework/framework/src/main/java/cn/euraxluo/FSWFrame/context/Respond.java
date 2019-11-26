package cn.euraxluo.FSWFrame.context;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * fast-start-web-framework
 * cn.euraxluo.FSWFrame.context
 * Respond
 * 2019/11/24 19:05
 * author:Euraxluo
 * TODO
 */
public class Respond {
    public static void html(ServletResponse response,Object obj) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(resp.SC_OK);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(obj.toString());
        writer.close();
    }
    public static void json(ServletResponse response,Object obj) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(resp.SC_OK);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String json =  JSONObject.toJSONString(obj,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteSlashAsSpecial
        );
        writer.write(json);
        writer.close();
    }

}
