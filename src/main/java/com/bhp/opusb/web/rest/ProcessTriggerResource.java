package com.bhp.opusb.web.rest;

import java.net.URISyntaxException;
import java.util.Map;

import com.bhp.opusb.service.trigger.ProcessTriggerService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for triggering a process.
 */
@RestController
@RequestMapping("/api")
public class ProcessTriggerResource {

  private final Logger log = LoggerFactory.getLogger(ProcessTriggerResource.class);

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private ProcessTriggerService triggerService;

  public ProcessTriggerResource(ProcessTriggerService triggerService) {
    this.triggerService = triggerService;
  }

  /**
   * {@code post  /process-trigger} : Execute a process.
   *
   * @param name the process name to execute.
   * @param params the process parameters.
   * @return the {@link ResponseEntity} with status {@code 200 (ok)} and with
   *         body 1, or with status {@code 400 (bad request)} if
   *         the name is not provided.
   * @throws URISyntaxException if the location uri syntax is incorrect.
   */
  @PostMapping("/process-trigger")
  public ResponseEntity<Integer> runProcess(@RequestParam String name, @RequestBody Map<String, Object> params)
      throws URISyntaxException {
    log.debug("rest request to run process: {}", name);
    if (name == null) {
      throw new BadRequestAlertException("Process name is not defined", "ProcessTrigger", "processundefined");
    }

    triggerService.executeProcess(name, params);
    return ResponseEntity.ok().body(1);
  }

}