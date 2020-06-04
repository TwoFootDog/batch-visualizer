package edu.project.authorization.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
//@EqualsAndHashCode(of = "userId")
@Table(name = "mbr_mst")
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "user_passwd", nullable = false, length = 100)
    private String userPasswd;

    @Column(name = "user_email", nullable = false, unique = true, length = 50)
    private String userEmail;

    @Column(name = "user_first_name", nullable = false, length = 50)
    private String userFirstName;

    @Column(name = "user_last_name", nullable = false, length = 50)
    private String userLastName;

    @Column(name = "reg_id", length = 20)
    private String regId;

    @CreationTimestamp
    @Column(name = "reg_dt")
    private Date regDt;

    @Column(name = "upd_id",  length = 20)
    private String updId;

    @CreationTimestamp
    @Column(name = "upd_dt")
    private Date updDt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rno")
    private MemberRoleVO memberRole;



/*    DROP TABLE MBR_MST;
    DROP TABLE ROL_MST;

    CREATE TABLE MBR_MST (
            ID BIGINT AUTO_INCREMENT PRIMARY KEY ,
            USER_ID VARCHAR(50) NOT NULL,
    USER_PASSWD VARCHAR(200) NOT NULL,
    USER_EMAIL VARCHAR(50) NOT NULL,
    USER_FIRST_NAME VARCHAR(50) NOT NULL,
    USER_LAST_NAME VARCHAR(50) NOT NULL,
    REG_ID VARCHAR(20),
    REG_DT DATETIME,
    UPD_ID VARCHAR(20),
    UPD_DT DATETIME
)ENGINE=INNODB;

# ALTER TABLE MBR_MST ADD CONSTRAINT PRIMARY KEY (ID);

    CREATE TABLE ROL_MST (
            RNO BIGINT AUTO_INCREMENT PRIMARY KEY ,
            MBR_MST_ID VARCHAR(50),
    ROLE_NAME VARCHAR(50) NOT NULL,
    REG_ID VARCHAR(20),
    REG_DT DATETIME,
    UPD_ID VARCHAR(20),
    UPD_DT DATETIME
)ENGINE=INNODB;

# ALTER TABLE MBR_MST ADD CONSTRAINT PRIMARY KEY (RNO);*/
}
