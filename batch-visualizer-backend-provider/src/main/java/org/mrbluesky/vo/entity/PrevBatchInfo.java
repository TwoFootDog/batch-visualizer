package org.mrbluesky.vo.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="COM_PRFBAT_MST")
public class PrevBatchInfo {

  @EmbeddedId
  private PrevBatchInfoId prevBatchInfoId;
  @Column(name="prev_bat_use_yn")
  private String prevBatchUseYn;
  @Column(name="regr_id")
  private String resgiterId;
  @Column(name="reg_dt")
  private Timestamp registerDate;
  @Column(name="updr_id")
  private String updaterId;
  @Column(name="upd_dt")
  private Timestamp updateDate;

}
