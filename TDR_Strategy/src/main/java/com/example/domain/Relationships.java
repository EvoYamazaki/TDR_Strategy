package com.example.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Relationships {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column relationships.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
	@Id
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column relationships.schedule_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Integer scheduleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column relationships.use_scene_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Integer useSceneId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column relationships.id
     *
     * @return the value of relationships.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column relationships.id
     *
     * @param id the value for relationships.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column relationships.schedule_id
     *
     * @return the value of relationships.schedule_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getScheduleId() {
        return scheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column relationships.schedule_id
     *
     * @param scheduleId the value for relationships.schedule_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column relationships.use_scene_id
     *
     * @return the value of relationships.use_scene_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getUseSceneId() {
        return useSceneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column relationships.use_scene_id
     *
     * @param useSceneId the value for relationships.use_scene_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setUseSceneId(Integer useSceneId) {
        this.useSceneId = useSceneId;
    }
}