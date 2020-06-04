package org.mrbluesky.vo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {

  private int totalRecords;
  private int totalPages;
  private int pageSize;

}
