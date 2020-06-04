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
@Table(name="COM_BATSCH_MST")
public class BoBatchJob implements BatchJob {

  @EmbeddedId
  private BoBatchJobId batchJobId;
  @Column(name="bat_nm")
  private String batchName;
  @Column(name="bat_exe_file_path_nm")
  private String batchExecFilePathName;
  @Column(name="bat_typ")
  private String batchType;
  @Column(name="bat_exe_dt")
  private Timestamp batchExecDate;
  @Column(name="bat_expl")
  private String batchExplanation;
  @Column(name="bat_use_fg")
  private String batchUseFlag;
  @Column(name="bat_work_fg")
  private String batchWorkFlag;
  @Column(name="bat_param_cnt")
  private Integer batchParameterCount;
  @Column(name="bat_param_val_cnts1")
  private String batchParameterValue1;
  @Column(name="bat_param_val_cnts2")
  private String batchParameterValue2;
  @Column(name="bat_param_val_cnts3")
  private String batchParameterValue3;
  @Column(name="bat_param_val_cnts4")
  private String batchParameterValue4;
  @Column(name="bat_param_val_cnts5")
  private String batchParameterValue5;
  @Column(name="bat_param_val_cnts6")
  private String batchParameterValue6;
  @Column(name="bat_param_val_cnts7")
  private String batchParameterValue7;
  @Column(name="bat_param_val_cnts8")
  private String batchParameterValue8;
  @Column(name="bat_param_val_cnts9")
  private String batchParameterValue9;
  @Column(name="bat_param_val_cnts10")
  private String batchParameterValue10;
  @Column(name="regr_id")
  private String resgiterId;
  @Column(name="reg_dt")
  private Timestamp registerDate;
  @Column(name="updr_id")
  private String updaterId;
  @Column(name="upd_dt")
  private Timestamp updateDate;
  @Column(name="pat_yn")
  private String patrolYn;
  @Column(name="pat_work_fg")
  private String patrolWorkFlag;

}
