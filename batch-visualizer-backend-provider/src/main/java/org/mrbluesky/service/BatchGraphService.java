package org.mrbluesky.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Data;
import org.mrbluesky.persistence.BoBatchJobRepository;
import org.mrbluesky.persistence.PrevBatchInfoRepository;
import org.mrbluesky.vo.entity.BoBatchJob;
import org.mrbluesky.vo.entity.PrevBatchInfo;
import org.mrbluesky.vo.entity.Error;
import org.mrbluesky.vo.graph.BatchNode;
import org.mrbluesky.vo.output.BatchGraphResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class BatchGraphService {

  private List<BatchNode> batches;
  private BoBatchJobRepository boBatchJobRepository;
  private PrevBatchInfoRepository prevBatchInfoRepository;
  private final Logger logger =  LoggerFactory.getLogger(this.getClass());

  @Autowired
  public BatchGraphService(BoBatchJobRepository boBatchJobRepository,
      PrevBatchInfoRepository prevBatchInfoRepository) {

    this.boBatchJobRepository = boBatchJobRepository;
    this.prevBatchInfoRepository = prevBatchInfoRepository;


  }

  public BatchGraphResponse getGraphInformation(String batchId){

    this.batches = new ArrayList<>();
    this.getAllBatches();
    this.makeBatchList();
    this.showNext(batchId);

    return new BatchGraphResponse(new Error(), this.batches);

  }

  public void getAllBatches(){

    List<BoBatchJob> batchJobs = boBatchJobRepository.findByBatchUseFlag("O");
    for(BoBatchJob batchJob : batchJobs) {

      BatchNode node = new BatchNode(batchJob.getBatchJobId().getBatchId(), batchJob.getBatchName());
      batches.add(node);

    }

    for(BatchNode node : batches){

      logger.error(node.toString());

    }

  }
  public void makeBatchList() {

    for(BatchNode node : batches) {
      // Set before
      List<PrevBatchInfo> beforeNodes =
          prevBatchInfoRepository.
              findByPrevBatchInfoIdBatchIdAndPrevBatchUseYn(node.getBatchId(), "Y");

      for(PrevBatchInfo beforeNode : beforeNodes) {
        String before = beforeNode.getPrevBatchInfoId().getPrevBatchId();
        node.setBefore(before);

      }

      // Set next
      List<PrevBatchInfo> nextNodes =
          prevBatchInfoRepository.
              findByPrevBatchInfoIdPrevBatchIdAndPrevBatchUseYn(node.getBatchId(), "Y");

      for(PrevBatchInfo nextNode : nextNodes) {
        String next = nextNode.getPrevBatchInfoId().getBatchId();
        node.setNext(next);
      }

    }

  }

  public void showNext(String batchId) {

    List<BatchNode> nodes = new ArrayList<>();
    Queue<BatchNode> q = new LinkedList<>();
    BatchNode initNode = this.getBatchNode(batchId);
    if (initNode == null) {
      logger.error("########## aaaaaaaaa");
    }

    int depth = 0;
    int x = 10;
    int y = 10;

    nodes.add(initNode);
    q.add(initNode);
    initNode.setAccessibleCount(initNode.getAccessibleCount() + 1);
    initNode.setPosX(x);
    initNode.setPosY(y);

    while (!q.isEmpty()) {

      int queueSize = q.size();
      depth++;
      x += 200;
      y = 10;

      for (int i = 0; i < queueSize; i++) {

        BatchNode parentNode = q.remove();
        parentNode.setAccessedCount(parentNode.getAccessedCount() + 1);
        for (String nextBatId : parentNode.getNext()) {

          BatchNode nextNode = this.getBatchNode(nextBatId);

          if(nextNode == null) {
            logger.debug("No such batch");
            continue;
          }
          int accessibleCount = 0;
          if(nextNode != null && nextNode.getAccessedCount() <= nextNode.getAccessibleCount()) {

            nextNode.setDepth(depth);
            q.add(nextNode);
            nextNode.setAccessibleCount(nextNode.getAccessibleCount() + 1);
            nextNode.setPosX(x);
            if(nextNode.getPosY() == 0){

              nextNode.setPosY(y);
              y += 100;

            }

          }

        }

      }

    }
    logger.error(this.batches.toString());

  }

  public BatchNode getBatchNode(String batchId){

    for(BatchNode batchNode : batches){

      if(batchNode.getBatchId().equals(batchId))
        return batchNode;

    }

    return null;

  }

}
