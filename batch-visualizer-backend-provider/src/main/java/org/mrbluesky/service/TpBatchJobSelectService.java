package org.mrbluesky.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mrbluesky.persistence.ErrorRepository;
import org.mrbluesky.persistence.TpBatchJobRepository;
import org.mrbluesky.vo.common.MetaData;
import org.mrbluesky.vo.entity.BatchJob;
import org.mrbluesky.vo.entity.Error;
import org.mrbluesky.vo.entity.TpBatchJob;
import org.mrbluesky.vo.entity.TpBatchJobId;
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
public class TpBatchJobSelectService {
  private TpBatchJobRepository batchJobRepository;
  private ErrorRepository errorRepository;
  private final Logger logger =  LoggerFactory.getLogger(this.getClass());

  @Autowired
  public TpBatchJobSelectService(TpBatchJobRepository batchJobRepository, ErrorRepository errorRepository){

    this.batchJobRepository = batchJobRepository;
    this.errorRepository = errorRepository;

  }

  public BatchJobSelectResponse getAllBatchJobs(Pageable pageable) {

    Page<TpBatchJob> tpBatchJobPage = this.batchJobRepository.findAll(pageable);
    List<TpBatchJob> tpBatchJobs = tpBatchJobPage.getContent();
    List<BatchJob> batchJobs = new ArrayList<>(tpBatchJobs);

    String errorCode = "0000";
    Error error = errorRepository.findByErrorCode(errorCode);
    MetaData metaData = new MetaData(batchJobs.size(), tpBatchJobPage.getTotalPages(), tpBatchJobPage.getSize());

    return new BatchJobSelectResponse(error, batchJobs, metaData);

  }

  public BatchJobSelectResponse getBatchJob(String masterBatchId, Pageable pageable) {

    Error error;
    Page<TpBatchJob> tpBatchJobPage;
    BatchJobSelectResponse batchJobResponse;
    String errorCode;

    if(masterBatchId == null) {

      errorCode = "1003";
      logger.info("유효하지 않은 입력값");
      error = errorRepository.findByErrorCode(errorCode);
      batchJobResponse = new BatchJobSelectResponse(error, null, null);

      return batchJobResponse;

    } else {

      tpBatchJobPage = this.batchJobRepository
          .findByBatchJobId(new TpBatchJobId("A", masterBatchId), pageable);
      List<TpBatchJob> tpBatchJobs = tpBatchJobPage.getContent();

      if (tpBatchJobs.size() == 0) {

        errorCode = "1004";
        logger.info("배치JOB미존재");
        error = errorRepository.findByErrorCode(errorCode);
        batchJobResponse = new BatchJobSelectResponse(error, null, null);

        return batchJobResponse;

      } else {

        errorCode = "0000";
        error = errorRepository.findByErrorCode(errorCode);

        List<BatchJob> batchJobs = new ArrayList<>(tpBatchJobs);
        MetaData metaData = new MetaData(batchJobs.size(), tpBatchJobPage.getTotalPages(), tpBatchJobPage.getSize());
        batchJobResponse = new BatchJobSelectResponse(error, batchJobs, metaData);
        return batchJobResponse;

      }

    }

  }

}
