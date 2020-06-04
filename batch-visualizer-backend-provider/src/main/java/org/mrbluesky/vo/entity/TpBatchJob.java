package org.mrbluesky.vo.entity;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
@Table(name="APR_BATSCH_MST")
public class TpBatchJob implements BatchJob {

  @EmbeddedId
  private TpBatchJobId batchJobId;
  @Column(name="mst_bat_nm")
  private String masterBatchName;
  @Column(name="host_nm")
  private String hostname;
  @Column(name="input_file_yn")
  private String inputFileYn;
  @Embedded
  private BatchFile batchFile;
  @Column(name="pre_bat_exist_yn")
  private String preBatchExistYn;
  @Column(name="file_auto_send_yn")
  private String fileAutoSendYn;
  @Column(name="multi_proc_cnt")
  private Integer multiProcessCount;
  @Column(name="report_typ")
  private String reportType;
  @Column(name="apl_yn")
  private String applyYn;
  @Column(name="regr_id")
  private String resgiterId;
  @Column(name="reg_dt")
  private Timestamp registerDate;
  @Column(name="updr_id")
  private String updaterId;
  @Column(name="upd_dt")
  private Timestamp updateDate;

}
