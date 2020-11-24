package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.PostExtends;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PostExtendsMapper {
    @Delete({
        "delete from post_extends",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into post_extends (post_id, browser, ",
        "collect, praise, ",
        "comment)",
        "values (#{postId,jdbcType=INTEGER}, #{browser,jdbcType=INTEGER}, ",
        "#{collect,jdbcType=INTEGER}, #{praise,jdbcType=INTEGER}, ",
        "#{comment,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(PostExtends record);

    @Select({
        "select",
        "id, post_id, browser, collect, praise, comment",
        "from post_extends",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="post_id", property="postId", jdbcType=JdbcType.INTEGER),
        @Result(column="browser", property="browser", jdbcType=JdbcType.INTEGER),
        @Result(column="collect", property="collect", jdbcType=JdbcType.INTEGER),
        @Result(column="praise", property="praise", jdbcType=JdbcType.INTEGER),
        @Result(column="comment", property="comment", jdbcType=JdbcType.INTEGER)
    })
    PostExtends selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, post_id, browser, collect, praise, comment",
        "from post_extends"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="post_id", property="postId", jdbcType=JdbcType.INTEGER),
        @Result(column="browser", property="browser", jdbcType=JdbcType.INTEGER),
        @Result(column="collect", property="collect", jdbcType=JdbcType.INTEGER),
        @Result(column="praise", property="praise", jdbcType=JdbcType.INTEGER),
        @Result(column="comment", property="comment", jdbcType=JdbcType.INTEGER)
    })
    List<PostExtends> selectAll();

    @Update({
        "update post_extends",
        "set post_id = #{postId,jdbcType=INTEGER},",
          "browser = #{browser,jdbcType=INTEGER},",
          "collect = #{collect,jdbcType=INTEGER},",
          "praise = #{praise,jdbcType=INTEGER},",
          "comment = #{comment,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(PostExtends record);
}