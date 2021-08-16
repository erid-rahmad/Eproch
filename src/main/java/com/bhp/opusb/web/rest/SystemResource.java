package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.dto.MasterClock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SystemResource {
  
  private final Logger log = LoggerFactory.getLogger(SystemResource.class);

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

    /**
     * {@code GET /system/master-clock} : get the master clock.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "currentTime".
     */
    @GetMapping("/system/master-clock")
    public ResponseEntity<MasterClock> getMasterClock() {
        log.debug("REST request to get master clock");
        return ResponseEntity.ok().body(new MasterClock());
    }

}
