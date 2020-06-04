package org.mrbluesky.vo.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class BatchFile {

  @Column(name="file_id")
  private String fileId;

  @Column(name = "hnm_file_nm")
  private String fileName;

}
