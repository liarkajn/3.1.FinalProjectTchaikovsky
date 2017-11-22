package main.java.com.likeit.web.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

    private String login;
    private String password;
    private String email;
    private LocalDateTime registrationDate;
    private String name;
    private String surname;
    private double rating;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private boolean compareFields(User user) {
        if (!login.equals(user.login) || !password.equals(user.password)
                || !email.equals(user.email) || !registrationDate.equals(user.registrationDate)
                || !name.equals(user.name) || !surname.equals(user.surname)
                || rating != user.rating) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 107 * login.hashCode() + password.hashCode() +
                + email.hashCode() + registrationDate.hashCode() +
                + name.hashCode() + surname.hashCode() + Double.valueOf(rating).hashCode();
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
        return compareFields(user);
    }

    @Override
    public String toString() {
        return "login : " + login +
                "\npassword : " + password +
                "\nname : " + name +
                "\nsurname : " + surname +
                "\nemail : " + email +
                "\nrating : " + rating +
                "\nregistration date : " + rating;
    }

}
