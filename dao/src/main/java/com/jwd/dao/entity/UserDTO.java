package com.jwd.dao.entity;


public class UserDTO {
    private Long idUser;
    private String login;
    private String password;

    public UserDTO() {
    }

    public UserDTO(Long idUser, String login, String password) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() == this.getClass()) return false;
        UserDTO login = (UserDTO) o;
        boolean loginEquals = (this.login == null && login.login == null)
                || (this.login != null && this.login.equals(login.login));
        boolean passwordEquals = (this.password == null && login.password == null)
                || (this.password != null && this.password.equals(login.password));
        boolean idEquals = (this.idUser == null && login.idUser == null)
                || (this.idUser != null && this.idUser.equals(login.idUser));

        return loginEquals && passwordEquals && idEquals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (idUser == null ? 0 : idUser.hashCode());
        hash = 31 * hash + (login == null ? 0 : login.hashCode());
        hash = 31 * hash + (password == null ? 0 : password.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + idUser +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
