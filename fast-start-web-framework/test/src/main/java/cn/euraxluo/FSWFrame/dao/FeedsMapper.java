package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.Feeds;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface FeedsMapper {
    @Delete({
        "delete from feeds",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into feeds (user_id, content, ",
        "created_at)",
        "values (#{userId,jdbcType=INTEGER}, #{content,jdbcType=VARCHAR}, ",
        "#{createdAt,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Feeds record);

    @Select({
        "select",
        "id, user_id, content, created_at",
        "from feeds",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER)
    })
    Feeds selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, user_id, content, created_at",
        "from feeds"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER)
    })
    List<Feeds> selectAll();

    @Update({
        "update feeds",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "content = #{content,jdbcType=VARCHAR},",
          "created_at = #{createdAt,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Feeds record);
}