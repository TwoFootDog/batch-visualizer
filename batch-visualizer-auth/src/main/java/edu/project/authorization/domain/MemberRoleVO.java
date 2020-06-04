package edu.project.authorization.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of ="rno")
@Table(name = "rol_mst")
public class MemberRoleVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rno;

    @Column(name = "role_name")
    private String roleName;

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
}
