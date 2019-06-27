package com.lailelaodi.util;

import com.lailelaodi.pojo.Student;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyHandler
 * @Description TODO
 * @Author Euraxluo
 * @Date 18-12-3 下午4:02
 */
public class MyHandler implements ResultSetHandler<List<Student>> {
    @Override
    public List<Student> handle(ResultSet resultSet) throws SQLException {
        List<Student> list = new ArrayList<Student>();
        while (resultSet.next()){
            Student stu = new Student();
            stu.setSid(resultSet.getInt("sid"));
            stu.setSname(resultSet.getString("sname"));
            stu.setGender(resultSet.getString("gender"));
            stu.setPhone(resultSet.getString("phone"));
            stu.setHobby(resultSet.getString("hobby"));
            stu.setInfo(resultSet.getString("info"));
            stu.setBirthday(resultSet.getDate("birthday"));
            list.add(stu);

        }
        return list;
    }
}
