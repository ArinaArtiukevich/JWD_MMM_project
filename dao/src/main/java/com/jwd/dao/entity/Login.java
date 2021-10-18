package com.jwd.dao.entity;


public class Login {

    private Long idClient;
    private String login;
    private String password;

    public Login() {}

    public Login(Long idClient, String login, String password) {
        this.idClient = idClient;
        this.login = login;
        this.password = password;
    }
    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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
        Login login = (Login) o;
        boolean loginEquals = (this.login == null && login.login == null)
                || (this.login != null && this.login.equals(login.login));
        boolean passwordEquals = (this.password == null && login.password == null)
                || (this.password != null && this.password.equals(login.password));
        boolean idEquals = (this.idClient == null && login.idClient == null)
                || (this.idClient != null && this.idClient.equals(login.idClient));

        return loginEquals && passwordEquals && idEquals;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (idClient == null ? 0 : idClient.hashCode());
        hash = 31 * hash + (login == null ? 0 : login.hashCode());
        hash = 31 * hash + (password == null ? 0 : password.hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "id=" + idClient +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
