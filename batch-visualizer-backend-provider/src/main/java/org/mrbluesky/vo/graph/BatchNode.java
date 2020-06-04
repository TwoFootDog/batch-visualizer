package org.mrbluesky.vo.graph;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchNode {

  private String batchId;
  private String batchName;
  private List<String> next;
  private List<String> before;
  private int accessedCount;
  private int accessibleCount;
  private int depth;
  private int posX;
  private int posY;

  public BatchNode(String batchId, String batchName) {

    this.batchId = batchId;
    this.batchName = batchName;
    this.next = new ArrayList<>();
    this.before = new ArrayList<>();
    this.depth = 0;
    this.posX = 0;
    this.posY = 0;
  }

  public void setNext(String nextNode){

    next.add(nextNode);

  }

  public void setBefore(String beforeNode){

    before.add(beforeNode);

  }

}
