package com.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Schedules {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
	@Id
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.user_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.date
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Date date;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.park
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Integer park;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.holiday
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Boolean holiday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column schedules.schedule
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private String schedule;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.id
     *
     * @return the value of schedules.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.id
     *
     * @param id the value for schedules.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.user_id
     *
     * @return the value of schedules.user_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.user_id
     *
     * @param userId the value for schedules.user_id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.date
     *
     * @return the value of schedules.date
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.date
     *
     * @param date the value for schedules.date
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.park
     *
     * @return the value of schedules.park
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getPark() {
        return park;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.park
     *
     * @param park the value for schedules.park
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setPark(Integer park) {
        this.park = park;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.holiday
     *
     * @return the value of schedules.holiday
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Boolean getHoliday() {
        return holiday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.holiday
     *
     * @param holiday the value for schedules.holiday
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setHoliday(Boolean holiday) {
        this.holiday = holiday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column schedules.schedule
     *
     * @return the value of schedules.schedule
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public String getSchedule() {
        return schedule;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column schedules.schedule
     *
     * @param schedule the value for schedules.schedule
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule == null ? null : schedule.trim();
    }
}