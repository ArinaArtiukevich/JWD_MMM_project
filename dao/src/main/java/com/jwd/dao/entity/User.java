package com.jwd.dao.entity;

import com.jwd.dao.entity.enumType.Gender;
import com.jwd.dao.entity.enumType.UserRole;

public class User {
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String login;
    private Gender gender;
    private UserRole userRole;


    public User() {
    }

    public User(Long idUser, String firstName, String lastName, String email, String city, String login, Gender gender, UserRole userRole) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.login = login;
        this.gender = gender;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, String email, String city, String login, Gender gender, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.login = login;
        this.gender = gender;
        this.userRole = userRole;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() == this.getClass()) return false;
        User user = (User) o;
        boolean firstNameEquals = (this.firstName == null && user.firstName == null)
                || (this.firstName != null && this.firstName.equals(user.firstName));
        boolean lastNameEquals = (this.lastName == null && user.lastName == null)
                || (this.lastName != null && this.lastName.equals(user.lastName));
        boolean emailEquals = (this.email == null && user.email == null)
                || (this.email != null && this.email.equals(user.email));
        boolean cityEquals = (this.city == null && user.city == null)
                || (this.city != null && this.city.equals(user.city));
        boolean loginEquals = (this.login == null && user.login == null)
                || (this.login != null && this.login.equals(user.login));
        boolean genderEquals = (this.gender == user.gender);
        boolean userRoleEquals = (this.userRole == user.userRole);
        return firstNameEquals && cityEquals && emailEquals && lastNameEquals && loginEquals && genderEquals && userRoleEquals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (firstName == null ? 0 : firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        hash = 31 * hash + (city == null ? 0 : city.hashCode());
        hash = 31 * hash + (login == null ? 0 : login.hashCode());
        hash = 31 * hash + gender.hashCode();
        hash = 31 * hash + userRole.hashCode();
        return hash;
    }


    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idUser +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", login='" + login + '\'' +
                ", gender=" + gender +
                ", userRole=" + userRole +
                '}';
    }
}