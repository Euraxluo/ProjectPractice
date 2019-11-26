package cn.euraxluo.FSWFrame.dao;

import cn.euraxluo.FSWFrame.pojo.User;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface UserMapper {
    @Delete({
        "delete from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user (username, auth_key, ",
        "password_hash, password_reset_token, ",
        "email_validate_token, email, ",
        "role, status, ",
        "avatar, vip_lv, created_at, ",
        "updated_at)",
        "values (#{username,jdbcType=VARCHAR}, #{authKey,jdbcType=VARCHAR}, ",
        "#{passwordHash,jdbcType=VARCHAR}, #{passwordResetToken,jdbcType=VARCHAR}, ",
        "#{emailValidateToken,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, ",
        "#{role,jdbcType=SMALLINT}, #{status,jdbcType=SMALLINT}, ",
        "#{avatar,jdbcType=VARCHAR}, #{vipLv,jdbcType=INTEGER}, #{createdAt,jdbcType=INTEGER}, ",
        "#{updatedAt,jdbcType=INTEGER})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(User record);

    @Select({
        "select",
        "id, username, auth_key, password_hash, password_reset_token, email_validate_token, ",
        "email, role, status, avatar, vip_lv, created_at, updated_at",
        "from user",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_key", property="authKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="password_hash", property="passwordHash", jdbcType=JdbcType.VARCHAR),
        @Result(column="password_reset_token", property="passwordResetToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="email_validate_token", property="emailValidateToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="role", property="role", jdbcType=JdbcType.SMALLINT),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
        @Result(column="vip_lv", property="vipLv", jdbcType=JdbcType.INTEGER),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER),
        @Result(column="updated_at", property="updatedAt", jdbcType=JdbcType.INTEGER)
    })
    User selectByPrimaryKey(Integer id);

    @Select({
        "select",
        "id, username, auth_key, password_hash, password_reset_token, email_validate_token, ",
        "email, role, status, avatar, vip_lv, created_at, updated_at",
        "from user"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="username", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_key", property="authKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="password_hash", property="passwordHash", jdbcType=JdbcType.VARCHAR),
        @Result(column="password_reset_token", property="passwordResetToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="email_validate_token", property="emailValidateToken", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="role", property="role", jdbcType=JdbcType.SMALLINT),
        @Result(column="status", property="status", jdbcType=JdbcType.SMALLINT),
        @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
        @Result(column="vip_lv", property="vipLv", jdbcType=JdbcType.INTEGER),
        @Result(column="created_at", property="createdAt", jdbcType=JdbcType.INTEGER),
        @Result(column="updated_at", property="updatedAt", jdbcType=JdbcType.INTEGER)
    })
    List<User> selectAll();

    @Update({
        "update user",
        "set username = #{username,jdbcType=VARCHAR},",
          "auth_key = #{authKey,jdbcType=VARCHAR},",
          "password_hash = #{passwordHash,jdbcType=VARCHAR},",
          "password_reset_token = #{passwordResetToken,jdbcType=VARCHAR},",
          "email_validate_token = #{emailValidateToken,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "role = #{role,jdbcType=SMALLINT},",
          "status = #{status,jdbcType=SMALLINT},",
          "avatar = #{avatar,jdbcType=VARCHAR},",
          "vip_lv = #{vipLv,jdbcType=INTEGER},",
          "created_at = #{createdAt,jdbcType=INTEGER},",
          "updated_at = #{updatedAt,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(User record);
}