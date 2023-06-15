package com.example.domain;

//import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.persistence.Entity;
import javax.persistence.Id;

//	@EntityScan
@Entity
public class Users {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
	@Id
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.name
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.introduction
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private String introduction;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.age
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Integer age;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.bookmarks
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private Boolean bookmarks;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.email
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.password
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    private String password;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.id
     *
     * @return the value of users.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.id
     *
     * @param id the value for users.id
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.name
     *
     * @return the value of users.name
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.name
     *
     * @param name the value for users.name
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.introduction
     *
     * @return the value of users.introduction
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.introduction
     *
     * @param introduction the value for users.introduction
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.age
     *
     * @return the value of users.age
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.age
     *
     * @param age the value for users.age
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.bookmarks
     *
     * @return the value of users.bookmarks
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public Boolean getBookmarks() {
        return bookmarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.bookmarks
     *
     * @param bookmarks the value for users.bookmarks
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setBookmarks(Boolean bookmarks) {
        this.bookmarks = bookmarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.email
     *
     * @return the value of users.email
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.email
     *
     * @param email the value for users.email
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.password
     *
     * @return the value of users.password
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.password
     *
     * @param password the value for users.password
     *
     * @mbg.generated Thu Jun 08 09:58:52 JST 2023
     */
    public void setPassword(String password) {
//        this.password = password == null ? null : new BCryptPasswordEncoder().encode(password.trim());
    	this.password = password == null ? null : password.trim();
    }
}