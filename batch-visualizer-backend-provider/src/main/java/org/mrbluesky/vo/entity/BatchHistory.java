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
@Table(name="COM_BATEXC_HIS")
public class BatchHistory {

  @EmbeddedId
  private BatchHistoryId batchHistoryId;
  @Column(name = "bat_id")
  private String batchId;
  @Column(name = "bat_exe_sts")
  private String batchExecStatus;
  @Column(name = "bat_exe_expl")
  private String batchExecExplanation;
  @Column(name = "bat_exe_fr_dt")
  private Timestamp batchExecFromDate;
  @Column(name = "bat_exe_to_dt")
  private Timestamp batchExecToDate;
  @Column(name = "bat_exe_read_cnt")
  private Integer batchExecReadCount;
  @Column(name = "bat_exe_proc_cnt")
  private Integer batchExecProcessCount;
  @Column(name = "bat_exe_ecpt_cnt")
  private Integer batchExecExceptionCount;
  @Column(name = "bat_exe_inp_file_nm")
  private String batchExecInputFileName;
  @Column(name = "bat_exe_prt_file_nm")
  private String batchExecOutputFileName;
  @Column(name="regr_id")
  private String resgiterId;
  @Column(name="reg_dt")
  private Timestamp registerDate;
  @Column(name="updr_id")
  private String updaterId;
  @Column(name="upd_dt")
  private Timestamp updateDate;
  @Column(name = "mst_bat_id")
  private String masterBatchId;
  @Column(name = "fr_trc_no")
  private String fromTraceNumber;
  @Column(name = "to_trc_no")
  private String toTraceNumber;

}
