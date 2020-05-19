package com.bhp.opusb.web.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.bhp.opusb.service.CountryService;
import com.bhp.opusb.service.dto.CountryDTO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class ImportResource {

  private final Logger log = LoggerFactory.getLogger(CountryResource.class);

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  @Autowired
  private CountryService countryService;

  @PostMapping("/upload-csv-file")
  @ResponseBody
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Model model) throws URISyntaxException {
     
    if (file.isEmpty()) {
      model.addAttribute("message", "Please select a CSV file to upload.");
      model.addAttribute("status", false);
    } else {
      // parse CSV file to create a list of `User` objects
      try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

          // create csv bean reader
          CsvToBean<CountryDTO> csvToBean = new CsvToBeanBuilder(reader)
              .withType(CountryDTO.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();

          // convert `CsvToBean` object to list of users
          List<CountryDTO> country = csvToBean.parse();
          countryService.saveAll(country);
          log.debug("Request to save all Country from import from csv : {}", country.size());

          // save users list on model
          model.addAttribute("country", country);
          model.addAttribute("status", true);

      } catch (Exception ex) {

          model.addAttribute("message", "An error occurred while processing the CSV file.");
          model.addAttribute("status", false);
          return ResponseEntity.status(400).body(ex.getMessage());
      }
      
    }

    return ResponseEntity.created(new URI("/api/upload-csv-file/")).body("upload data");
  }
  
}
