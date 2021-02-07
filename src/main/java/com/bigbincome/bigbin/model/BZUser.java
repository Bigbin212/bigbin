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
@Table(name="b_z_user")
public class BZUser {
    @Id
    @GenericGenerator(name = "xlh-uuid", strategy = "uuid")
    @GeneratedValue(generator = "xlh-uuid")
    private String xlh;
    private String username;
    private String password;

    @Lob
    private String ip;

    private String email;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private Date zcsj  = new Date();

    @Column(nullable = true, columnDefinition = "varchar(20) default ''")
    private String phone;

    @Lob
    @Column(columnDefinition="TEXT")
    private String photo;

    @Builder.Default
    private String yhqx = "1";
}
