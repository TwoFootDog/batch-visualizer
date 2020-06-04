package org.mrbluesky.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mrbluesky.persistence.BoBatchJobRepository;
import org.mrbluesky.persistence.ErrorRepository;
import org.mrbluesky.vo.common.MetaData;
import org.mrbluesky.vo.entity.BatchJob;
import org.mrbluesky.vo.entity.BoBatchJob;
import org.mrbluesky.vo.entity.BoBatchJobId;
import org.mrbluesky.vo.entity.Error;
import org.mrbluesky.vo.output.BatchJobSelectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Data
@EqualsAndHashCode
public class BoBatchJobSelectService {
  private BoBatchJobRepository boBatchJobRepository;
  private ErrorRepository errorRepository;
  private final Logger logger =  LoggerFactory.getLogger(this.getClass());

  @Autowired
  public BoBatchJobSelectService(BoBatchJobRepository boBatchJobRepository, ErrorRepository errorRepository){

    this.boBatchJobRepository = boBatchJobRepository;
    this.errorRepository = errorRepository;

  }

  public BatchJobSelectResponse getAllBatchJobs(Pageable pageable) {

    Page<BoBatchJob> boBatchJobPage = this.boBatchJobRepository.findAll(pageable);
    List<BoBatchJob> boBatchJobs = boBatchJobPage.getContent();
    List<BatchJob> batchJobs = new ArrayList<>(boBatchJobs);

    String errorCode = "0000";
    Error error = errorRepository.findByErrorCode(errorCode);
    MetaData metaData = new MetaData(batchJobs.size(), boBatchJobPage.getTotalPages(), boBatchJobPage.getSize());

    return new BatchJobSelectResponse(error, batchJobs, metaData);

  }

  public BatchJobSelectResponse getBatchJob(String batchId, Pageable pageable) {

    Error error;
    Page<BoBatchJob> boBatchJobPage;
    BatchJobSelectResponse batchJobResponse;
    String errorCode;

    if(batchId == null) {

      errorCode = "1003";
      logger.info("유효하지 않은 입력값");
      error = errorRepository.findByErrorCode(errorCode);
      batchJobResponse = new BatchJobSelectResponse(error, null, null);

      return batchJobResponse;

    } else {

      boBatchJobPage = this.boBatchJobRepository
          .findByBatchJobId(new BoBatchJobId("A", batchId), pageable);
      List<BoBatchJob> boBatchJobs = boBatchJobPage.getContent();

      if (boBatchJobs.size() == 0) {

        errorCode = "1004";
        logger.info("배치JOB미존재");
        error = errorRepository.findByErrorCode(errorCode);
        batchJobResponse = new BatchJobSelectResponse(error, null, null);

        return batchJobResponse;

      } else {

        errorCode = "0000";
        error = errorRepository.findByErrorCode(errorCode);

        List<BatchJob> batchJobs = new ArrayList<>(boBatchJobs);
        MetaData metaData = new MetaData(batchJobs.size(), boBatchJobPage.getTotalPages(), boBatchJobPage.getSize());
        batchJobResponse = new BatchJobSelectResponse(error, batchJobs, metaData);
        return batchJobResponse;

      }

    }

  }
}
