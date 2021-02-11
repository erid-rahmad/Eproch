package com.bhp.opusb.config.converter;

import com.bhp.opusb.service.dto.ImportParameterDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToImportParameterDTO implements Converter<String, ImportParameterDTO> {

  private final ObjectMapper objectMapper;

  public StringToImportParameterDTO(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public ImportParameterDTO convert(String source) {
    try {
      return objectMapper.readValue(source, ImportParameterDTO.class);
    } catch (JsonProcessingException e) {
      throw new BadRequestAlertException(e.getLocalizedMessage(), "importedEntity", "importBadConfig");
    }
  }
}
