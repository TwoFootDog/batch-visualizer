package org.mrbluesky.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mrbluesky.service.BatchHistorySelectService;
import org.mrbluesky.service.TpBatchJobSelectService;
import org.mrbluesky.vo.output.BatchHistorySelectResponse;
import org.mrbluesky.vo.output.BatchJobSelectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/exechistory")
public class BatchHistoryController {

  private static Logger logger = LogManager.getLogger(TpBatchController.class);
  private BatchHistorySelectService batchHistorySelectService;

  @Autowired
  public BatchHistoryController(BatchHistorySelectService batchHistorySelectService) {
    this.batchHistorySelectService = batchHistorySelectService;
  }

  @GetMapping(value = "/{masterBatchId}")
  public BatchHistorySelectResponse getTpBatchInformation(
      @PathVariable(required = false) String masterBatchId,
      @PageableDefault Pageable pageable) {

    return batchHistorySelectService.getBatchHistories(masterBatchId, pageable);

  }

  @GetMapping(value = {"", "/"})
  public BatchHistorySelectResponse getAllTpBatchInformation(Pageable pageable) {

    return batchHistorySelectService.getAllBatchHistories(pageable);

  }

}
