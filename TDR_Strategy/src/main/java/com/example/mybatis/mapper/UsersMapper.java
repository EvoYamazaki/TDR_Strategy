package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.Users;
import com.example.domain.UsersExample;

@Mapper
public interface UsersMapper {
	@Select({
	    "select * from users where id = #{id} limit 1"
	})
	List<Users> selectById(Integer id);
	
	//追加
	@Select({
	    "select * from users where email = #{email} limit 1"
	})
	Users selectByEmail(String email);
	
	@Select({
	    "select name from users where id = #{id} limit 1"
	})
	String selectNameById(Integer id);
	
	@Insert({
		"insert into users (name,email,password) values (#{name}, #{email}, #{password})"
	})
	  int userInsert(@Param("name") String name, @Param("email") String email, @Param("password") String password);
	
	//ここまで
	
	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    long countByExample(UsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int deleteByExample(UsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Delete({
        "delete from users",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Insert({
        "insert into users (id, name, ",
        "introduction, age, ",
        "bookmarks, email, password)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{introduction,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER}, ",
        "#{bookmarks,jdbcType=BIT}, #{email,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR})"
    })
    int insert(Users row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int insertSelective(Users row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    List<Users> selectByExample(UsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Select({
        "select",
        "id, name, introduction, age, bookmarks, email, password",
        "from users",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.example.mybatis.mapper.UsersMapper.BaseResultMap")
    Users selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByExampleSelective(@Param("row") Users row, @Param("example") UsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByExample(@Param("row") Users row, @Param("example") UsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByPrimaryKeySelective(Users row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table users
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Update({
        "update users",
        "set name = #{name,jdbcType=VARCHAR},",
          "introduction = #{introduction,jdbcType=VARCHAR},",
          "age = #{age,jdbcType=INTEGER},",
          "bookmarks = #{bookmarks,jdbcType=BIT},",
          "email = #{email,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Users row);
}