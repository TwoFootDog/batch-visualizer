package org.mrbluesky.service;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mrbluesky.persistence.BatchHistoryRepository;
import org.mrbluesky.persistence.ErrorRepository;
import org.mrbluesky.vo.common.MetaData;
import org.mrbluesky.vo.entity.BatchHistory;
import org.mrbluesky.vo.entity.Error;
import org.mrbluesky.vo.output.BatchHistorySelectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Data
@EqualsAndHashCode
public class BatchHistorySelectService {

  private BatchHistoryRepository batchHistoryRepository;
  private ErrorRepository errorRepository;
  private final Logger logger =  LoggerFactory.getLogger(this.getClass());

  @Autowired
  public BatchHistorySelectService(BatchHistoryRepository batchHistoryRepository, ErrorRepository errorRepository){

    this.batchHistoryRepository = batchHistoryRepository;
    this.errorRepository = errorRepository;

  }

  public BatchHistorySelectResponse getAllBatchHistories(Pageable pageable) {

    Page<BatchHistory> batchHistoryPage = this.batchHistoryRepository.findAll(pageable);
    List<BatchHistory> batchHistories = batchHistoryPage.getContent();

    String errorCode = "0000";
    Error error = errorRepository.findByErrorCode(errorCode);
    MetaData metaData = new MetaData(batchHistories.size(), batchHistoryPage.getTotalPages(), batchHistoryPage.getSize());

    return new BatchHistorySelectResponse(error, batchHistories, metaData);

  }

  public BatchHistorySelectResponse getBatchHistories(String masterBatchId, Pageable pageable) {

    Error error;
    Page<BatchHistory> batchHistoryPage;
    BatchHistorySelectResponse batchHistorySelectResponse;
    String errorCode;

    if(masterBatchId == null) {

      errorCode = "1003";
      logger.info("유효하지 않은 입력값");
      error = errorRepository.findByErrorCode(errorCode);
      batchHistorySelectResponse = new BatchHistorySelectResponse(error, null, null);

      return batchHistorySelectResponse;

    } else {

      batchHistoryPage = this.batchHistoryRepository.findByBatchId(masterBatchId, pageable);
      List<BatchHistory> batchHistories = batchHistoryPage.getContent();

      if (batchHistories.size() == 0) {

        errorCode = "1010";
        logger.info("배치 history 미존재");
        error = errorRepository.findByErrorCode(errorCode);
        batchHistorySelectResponse = new BatchHistorySelectResponse(error, null, null);

        return batchHistorySelectResponse;

      } else {

        errorCode = "0000";
        error = errorRepository.findByErrorCode(errorCode);

        MetaData metaData = new MetaData(batchHistories.size(), batchHistoryPage.getTotalPages(), batchHistoryPage.getSize());
        batchHistorySelectResponse = new BatchHistorySelectResponse(error, batchHistories, metaData);
        return batchHistorySelectResponse;

      }

    }

  }

}
