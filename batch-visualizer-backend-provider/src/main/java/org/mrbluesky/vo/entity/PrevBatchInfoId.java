package org.mrbluesky.vo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrevBatchInfoId implements Serializable {

  @Column(name="mbrsh_pgm_id")
  private String membershipProgramId;
  @Column(name="bat_id")
  private String batchId;
  @Column(name="prev_bat_id")
  private String prevBatchId;


}
