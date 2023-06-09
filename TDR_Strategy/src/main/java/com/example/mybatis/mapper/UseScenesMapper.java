package com.example.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.domain.UseScenes;
import com.example.domain.UseScenesExample;

@Mapper
public interface UseScenesMapper {
//	//追加
//	@Select({
//		"select ",
//		"    u.use_scene_name ",
//		"from ",
//		"    relationships r",
//		"    INNER JOIN schedules s ON r.schedule_id = s.id ",
//		"    INNER JOIN use_scenes u ON r.use_scene_id = u.id", 
//		"where r.schedule_id = #{schedule_id}"
//	})
//	List<UseScenes> selectByUseSeenes(Integer schedule_id);
//	//ここまで
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    long countByExample(UseScenesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int deleteByExample(UseScenesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Delete({
        "delete from use_scenes",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Insert({
        "insert into use_scenes (id, use_scene_name)",
        "values (#{id,jdbcType=INTEGER}, #{useSceneName,jdbcType=VARCHAR})"
    })
    int insert(UseScenes row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int insertSelective(UseScenes row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    List<UseScenes> selectByExample(UseScenesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Select({
        "select",
        "id, use_scene_name",
        "from use_scenes",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.example.mybatis.mapper.UseScenesMapper.BaseResultMap")
    UseScenes selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByExampleSelective(@Param("row") UseScenes row, @Param("example") UseScenesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByExample(@Param("row") UseScenes row, @Param("example") UseScenesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    int updateByPrimaryKeySelective(UseScenes row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table use_scenes
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    @Update({
        "update use_scenes",
        "set use_scene_name = #{useSceneName,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UseScenes row);
}