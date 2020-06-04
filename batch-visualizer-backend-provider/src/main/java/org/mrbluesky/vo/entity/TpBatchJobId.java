package org.mrbluesky.vo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TpBatchJobId implements Serializable {

  @Column(name="mbrsh_pgm_id")
  private String membershipProgramId;
  @Column(name="mst_bat_id")
  private String masterBatchId;

}
