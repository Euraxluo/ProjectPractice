package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.Cats;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface CatsMapper {
    @Delete({
        "delete from cats",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cats (cat_name)",
        "values (#{catName,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Cats record);

    @Select({
        "select",
        "id, cat_name",
        "from cats",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="cat_name", property="catName", jdbcType=JdbcType.VARCHAR)
    })
    Cats selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, cat_name",
        "from cats"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="cat_name", property="catName", jdbcType=JdbcType.VARCHAR)
    })
    List<Cats> selectAll();

    @Update({
        "update cats",
        "set cat_name = #{catName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Cats record);
}