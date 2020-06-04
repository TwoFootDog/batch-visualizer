package org.mrbluesky.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mrbluesky.service.TpBatchJobSelectService;
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
@RequestMapping("/tp")
public class TpBatchController {

  private static Logger logger = LogManager.getLogger(TpBatchController.class);
  private TpBatchJobSelectService tpBatchJobSelectService;

  @Autowired
  public TpBatchController(TpBatchJobSelectService tpBatchJobSelectService) {
    this.tpBatchJobSelectService = tpBatchJobSelectService;
  }

  @GetMapping(value = "/{batchName}")
  public BatchJobSelectResponse getTpBatchInformation(
      @PathVariable(required = false) String batchName,
      @PageableDefault Pageable pageable) {

    return tpBatchJobSelectService.getBatchJob(batchName, pageable);

  }

  @GetMapping(value = {"", "/"})
  public BatchJobSelectResponse getAllTpBatchInformation(
      Pageable pageable) {

    return tpBatchJobSelectService.getAllBatchJobs(pageable);

  }

}
