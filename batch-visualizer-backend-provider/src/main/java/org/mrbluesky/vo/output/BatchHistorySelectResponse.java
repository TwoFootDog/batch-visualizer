package org.mrbluesky.vo.output;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mrbluesky.vo.common.MetaData;
import org.mrbluesky.vo.entity.BatchHistory;
import org.mrbluesky.vo.entity.BatchJob;
import org.mrbluesky.vo.entity.Error;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BatchHistorySelectResponse implements IResponse {

  private Error error;
  private List<BatchHistory> batchJobs;
  private MetaData metaData;

}
