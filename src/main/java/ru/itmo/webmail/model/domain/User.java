package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private String passwordSha1;
    private long id;
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId(){ return id; }

    public void setId(long currId){ this.id = currId; }

    public void setEmail(String em) {
        this.email = em;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordSha1() {
        return passwordSha1;
    }

    public void setPasswordSha1(String passwordSha1) {
        this.passwordSha1 = passwordSha1;
    }
}
