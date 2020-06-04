package org.mrbluesky.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mrbluesky.service.BoBatchJobSelectService;
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
@RequestMapping("/bo")
public class BoBatchController {

  private static Logger logger = LogManager.getLogger(TpBatchController.class);
  private BoBatchJobSelectService boBatchJobSelectService;

  @Autowired
  public BoBatchController(BoBatchJobSelectService boBatchJobSelectService) {
    this.boBatchJobSelectService = boBatchJobSelectService;
  }

  @GetMapping(value = "/{batchName}")
  public BatchJobSelectResponse getBoBatchInformation(
      @PathVariable(required = false) String batchName,
      @PageableDefault Pageable pageable) {

    return boBatchJobSelectService.getBatchJob(batchName, pageable);

  }

  @GetMapping(value = {"", "/"})
  public BatchJobSelectResponse getAllBoBatchInformation(
      Pageable pageable) {

    return boBatchJobSelectService.getAllBatchJobs(pageable);

  }

}
