package com.bigbincome.bigbin.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "b_z_user")
public class BZUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String xlh;
    private String username;
    private String password;
    private String ip;
    private String email;
    private Timestamp zcsj;
    private String phone;
    private String photo;
    private String yhqx;

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
