package org.mrbluesky.vo.output;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mrbluesky.vo.common.MetaData;
import org.mrbluesky.vo.entity.BatchJob;
import org.mrbluesky.vo.entity.Error;
import org.mrbluesky.vo.graph.BatchNode;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BatchGraphResponse {

  private Error error;
  private List<BatchNode> batchNodes;

}
