package com.bigbincome.bigbin.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "b_z_user")
public class BZUserEntity {
    private String xlh;
    private String username;
    private String password;
    private String ip;
    private String email;
    private Timestamp zcsj;
    private String phone;
    private String photo;
    private String yhqx;

    @Id
    @Column(name = "xlh")
    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "zcsj")
    public Timestamp getZcsj() {
        return zcsj;
    }

    public void setZcsj(Timestamp zcsj) {
        this.zcsj = zcsj;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "yhqx")
    public String getYhqx() {
        return yhqx;
    }

    public void setYhqx(String yhqx) {
        this.yhqx = yhqx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BZUserEntity that = (BZUserEntity) o;
        return Objects.equals(xlh, that.xlh) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(email, that.email) &&
                Objects.equals(zcsj, that.zcsj) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(photo, that.photo) &&
                Objects.equals(yhqx, that.yhqx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xlh, username, password, ip, email, zcsj, phone, photo, yhqx);
    }
}
