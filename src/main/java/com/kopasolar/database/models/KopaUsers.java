package com.kopasolar.database.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Class name: KopaUsers
 * Creater: wgicheru
 * Date:1/27/2020
 */
@Entity
public class KopaUsers {
    int userid;
    String username;
    String password;
    String email;
    String image;

    boolean active;
    int activationtoken;

    Date createdon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActivationtoken() {
        return activationtoken;
    }

    public void setActivationtoken(int activationtoken) {
        this.activationtoken = activationtoken;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }
}
