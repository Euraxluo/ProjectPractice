package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.Posts;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface PostsMapper {
    @Delete({
        "delete from posts",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into posts (title, summary, ",
        "label_img, cat_id, ",
        "user_id, user_name, ",
        "is_valid, created_at, ",
        "updated_at, content)",
        "values (#{title,jdbcType=VARCHAR}, #{summary,jdbcType=VARCHAR}, ",
        "#{labelImg,jdbcType=VARCHAR}, #{catId,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER}, #{userName,jdbcType=VARCHAR}, ",
        "#{isValid,jdbcType=BIT}, #{createdAt,jdbcType=INTEGER}, ",
        "#{updatedAt,jdbcType=INTEGER}, #{content,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Posts record);

    @Select({
        "select",
        "id, title, summary, label_img, cat_id, user_id, user_name, is_valid, created_at, ",
        "updated_at, content",
        "from posts",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="label_img", property="labelImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_id", property="catId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.BIT),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER),
        @Result(column="updated_at", property="updatedAt", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    Posts selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, title, summary, label_img, cat_id, user_id, user_name, is_valid, created_at, ",
        "updated_at, content",
        "from posts"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="label_img", property="labelImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="cat_id", property="catId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_valid", property="isValid", jdbcType=JdbcType.BIT),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER),
        @Result(column="updated_at", property="updatedAt", jdbcType=JdbcType.INTEGER),
        @Result(column="content", property="content", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Posts> selectAll();

    @Update({
        "update posts",
        "set title = #{title,jdbcType=VARCHAR},",
          "summary = #{summary,jdbcType=VARCHAR},",
          "label_img = #{labelImg,jdbcType=VARCHAR},",
          "cat_id = #{catId,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "user_name = #{userName,jdbcType=VARCHAR},",
          "is_valid = #{isValid,jdbcType=BIT},",
          "created_at = #{createdAt,jdbcType=INTEGER},",
          "updated_at = #{updatedAt,jdbcType=INTEGER},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Posts record);
}