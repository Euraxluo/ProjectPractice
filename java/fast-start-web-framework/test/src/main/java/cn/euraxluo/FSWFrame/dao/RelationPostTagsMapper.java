package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.RelationPostTags;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface RelationPostTagsMapper {
    @Delete({
        "delete from relation_post_tags",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into relation_post_tags (post_id, tag_id)",
        "values (#{postId,jdbcType=INTEGER}, #{tagId,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(RelationPostTags record);

    @Select({
        "select",
        "id, post_id, tag_id",
        "from relation_post_tags",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="post_id", property="postId", jdbcType=JdbcType.INTEGER),
        @Result(column="tag_id", property="tagId", jdbcType=JdbcType.INTEGER)
    })
    RelationPostTags selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, post_id, tag_id",
        "from relation_post_tags"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="post_id", property="postId", jdbcType=JdbcType.INTEGER),
        @Result(column="tag_id", property="tagId", jdbcType=JdbcType.INTEGER)
    })
    List<RelationPostTags> selectAll();

    @Update({
        "update relation_post_tags",
        "set post_id = #{postId,jdbcType=INTEGER},",
          "tag_id = #{tagId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RelationPostTags record);
}