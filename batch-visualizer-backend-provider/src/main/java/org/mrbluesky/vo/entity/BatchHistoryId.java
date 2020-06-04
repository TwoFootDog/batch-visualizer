package org.mrbluesky.vo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchHistoryId implements Serializable {

  @Column(name="mbrsh_pgm_id")
  private String membershipProgramId;
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="bat_exe_seq")
  private Long batchExecSequence;

}
