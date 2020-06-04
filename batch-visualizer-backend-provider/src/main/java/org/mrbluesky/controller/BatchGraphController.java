package org.mrbluesky.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mrbluesky.service.BatchGraphService;
import org.mrbluesky.vo.output.BatchGraphResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/graph")
public class BatchGraphController {

  private static Logger logger = LogManager.getLogger(BatchGraphController.class);
  private BatchGraphService batchGraphService;

  @Autowired
  public BatchGraphController(BatchGraphService batchGraphService) {
    this.batchGraphService = batchGraphService;
  }

  @GetMapping(value = "/{batchId}")
  public BatchGraphResponse getBoBatchInformation(
                  @PathVariable(required = false) String batchId) {

    return batchGraphService.getGraphInformation(batchId);

  }

}
