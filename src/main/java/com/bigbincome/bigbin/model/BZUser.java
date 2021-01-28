package com.bigbincome.bigbin.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class BZUser {
    @Id
    @GenericGenerator(name = "xlh-uuid", strategy = "uuid")
    @GeneratedValue(generator = "xlh-uuid")
    private String xlh;
    private String username;
    private String password;
    private String ip;
    private String email;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date zcsj  = new Date();

    private String phone;
    private String photo;

    @Builder.Default
    private String yhqx = "1";
}
