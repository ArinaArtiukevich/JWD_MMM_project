package com.jwd.dao.entity;

import com.jwd.dao.entity.enums.Gender;
import com.jwd.dao.entity.enums.UserRole;

public class Registration {

    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String login;
    private String password; // todo change to char[]
    private String confirmPassword; // todo change to char[]
    private Gender gender;
    private UserRole userRole;

    public Registration(String firstName, String lastName, String email, String city, String login, String password, String confirmPassword, Gender gender, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.gender = gender;
        this.userRole = userRole;
    }

    public Registration(String firstName, String lastName, String email, String city, String login, Gender gender, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.login = login;
        this.gender = gender;
        this.userRole = userRole;
    }

    public Registration(String firstName, String lastName, String email, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
    }

    public Registration(String firstName, String lastName, String email, String city, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
        Registration registration = (Registration) o;
        boolean firstNameEquals = (this.firstName == null && registration.firstName == null)
                || (this.firstName != null && this.firstName.equals(registration.firstName));
        boolean lastNameEquals = (this.lastName == null && registration.lastName == null)
                || (this.lastName != null && this.lastName.equals(registration.lastName));
        boolean emailEquals = (this.email == null && registration.email == null)
                || (this.email != null && this.email.equals(registration.email));
        boolean cityEquals = (this.city == null && registration.city == null)
                || (this.city != null && this.city.equals(registration.city));
        boolean loginEquals = (this.login == null && registration.login == null)
                || (this.login != null && this.login.equals(registration.login));
        boolean passwordEquals = (this.password == null && registration.password == null)
                || (this.password != null && this.password.equals(registration.password));
        boolean confirmPasswordEquals = (this.confirmPassword == null && registration.confirmPassword == null)
                || (this.confirmPassword != null && this.confirmPassword.equals(registration.confirmPassword));
        boolean genderEquals = (this.gender == registration.gender);
        boolean userRoleEquals = (this.userRole == registration.userRole);
        return firstNameEquals && cityEquals && emailEquals && lastNameEquals && passwordEquals && confirmPasswordEquals && loginEquals && genderEquals && userRoleEquals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (firstName == null ? 0 : firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        hash = 31 * hash + (city == null ? 0 : city.hashCode());
        hash = 31 * hash + (login == null ? 0 : login.hashCode());
        hash = 31 * hash + (password == null ? 0 : password.hashCode());
        hash = 31 * hash + (confirmPassword == null ? 0 : confirmPassword.hashCode());
        hash = 31 * hash + gender.hashCode();
        hash = 31 * hash + userRole.hashCode();
        return hash;
    }


}
