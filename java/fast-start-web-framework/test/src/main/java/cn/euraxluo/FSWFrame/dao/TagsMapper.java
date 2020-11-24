package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.Tags;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface TagsMapper {
    @Delete({
        "delete from tags",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tags (tag_name, post_num)",
        "values (#{tagName,jdbcType=VARCHAR}, #{postNum,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Tags record);

    @Select({
        "select",
        "id, tag_name, post_num",
        "from tags",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="tag_name", property="tagName", jdbcType=JdbcType.VARCHAR),
        @Result(column="post_num", property="postNum", jdbcType=JdbcType.INTEGER)
    })
    Tags selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, tag_name, post_num",
        "from tags"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="tag_name", property="tagName", jdbcType=JdbcType.VARCHAR),
        @Result(column="post_num", property="postNum", jdbcType=JdbcType.INTEGER)
    })
    List<Tags> selectAll();

    @Update({
        "update tags",
        "set tag_name = #{tagName,jdbcType=VARCHAR},",
          "post_num = #{postNum,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Tags record);
}