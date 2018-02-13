package com.likeit.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

    //0 - admin, 1 - moderator, 2 - just user

    private int id;
    private short role;
    private String login;
    private String password;
    private String email;
    private String gender;
    private LocalDateTime registrationDate;
    private String name;
    private String surname;
    private String bio;
    private boolean banned;
    private int questionsCount;
    private int answersCount;
    private double averageMark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBanned() {
        return banned;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public int getAnswersCount() {
        return answersCount;
    }

    public void setAnswersCount(int answersCount) {
        this.answersCount = answersCount;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    @Override
    public int hashCode() {
        return 107 * id + role + login.hashCode() + password.hashCode() +
                + email.hashCode() + gender.hashCode() + registrationDate.hashCode() +
                + name.hashCode() + surname.hashCode() + bio.hashCode() +
                + Boolean.hashCode(banned) + questionsCount + answersCount +
                + Double.hashCode(averageMark);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        if (!super.equals(user)) {
            return false;
        }
        return id == user.id && role == user.role && login.equals(user.login)
                && password.equals(user.password) && email.equals(user.email)
                && gender.equals(user.gender) && registrationDate.equals(user.registrationDate)
                && name.equals(user.name) && surname.equals(user.surname) && bio.equals(user.bio)
                && banned == user.banned && questionsCount == user.questionsCount
                && answersCount == user.answersCount && averageMark == user.averageMark;
    }

    @Override
    public String toString() {
        return "id : " + id +
                "\nrole : " + role +
                "\nlogin : " + login +
                "\npassword : " + password +
                "\nname : " + name +
                "\nsurname : " + surname +
                "\nemail : " + email +
                "\ngender : " + gender +
                "\nregistration date : " + registrationDate +
                "\nbio" + bio +
                "\nis banned " + banned +
                "\nquestions count " + questionsCount +
                "\nanswers count " + answersCount +
                "\naverage mark " + averageMark;
    }

}
