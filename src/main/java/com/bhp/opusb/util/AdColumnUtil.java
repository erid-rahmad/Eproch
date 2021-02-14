package com.bhp.opusb.util;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.bhp.opusb.domain.enumeration.ADColumnType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdColumnUtil {

  private static final Logger log = LoggerFactory.getLogger(AdColumnUtil.class);

  private AdColumnUtil() {}

  /**
   * Format the raw value into an object based on the specified field type.
   * 
   * @param rawValue
   * @param fieldType JHipster's type of fields.
   *                  https://www.jhipster.tech/jdl/entities-fields#field-types-and-validations
   * @return
   */
  public static Object formatValue(String rawValue, ADColumnType fieldType) {
    Object result = rawValue;

    if (ADColumnType.INTEGER.equals(fieldType)) {
      try {
        result = Integer.parseInt(rawValue);
      } catch (NumberFormatException e) {
        log.warn("Failed to parse text to Integer. text: {}", rawValue);
      }
    } else if (ADColumnType.LONG.equals(fieldType)) {
      try {
        result = Long.parseLong(rawValue);
      } catch (NumberFormatException e) {
        log.warn("Failed to parse text to Long. text: {}", rawValue);
      }
    } else if (ADColumnType.FLOAT.equals(fieldType)) {
      try {
        result = Float.parseFloat(rawValue);
      } catch (NumberFormatException e) {
        log.warn("Failed to parse text to Float. text: {}", rawValue);
      }
    } else if (ADColumnType.DOUBLE.equals(fieldType)) {
      try {
        result = Double.parseDouble(rawValue);
      } catch (NumberFormatException e) {
        log.warn("Failed to parse text to Double. text: {}", rawValue);
      }
    } else if (ADColumnType.BIG_DECIMAL.equals(fieldType)) {
      result = new BigDecimal(rawValue);
    } else if (ADColumnType.BOOLEAN.equals(fieldType)) {
      result = rawValue != null && (rawValue.equals("1") || rawValue.equalsIgnoreCase("Y")
          || rawValue.equalsIgnoreCase("YES") || rawValue.equalsIgnoreCase("TRUE"));
    } else if (ADColumnType.LOCAL_DATE.equals(fieldType)) {
      // e.g. yyyy-MM-dd
      result = LocalDate.parse(rawValue, DateTimeFormatter.ISO_LOCAL_DATE);
    } else if (ADColumnType.ZONED_DATE_TIME.equals(fieldType)) {
      // e.g. yyyy-MM-ddTHH:mm:ss+08:00
      result = ZonedDateTime.parse(rawValue, DateTimeFormatter.ISO_DATE_TIME);
    } else if (ADColumnType.INSTANT.equals(fieldType)) {
      // e.g. yyyy-MM-ddTHH:mm:ssZ
      result = Instant.parse(rawValue);
    } else if (ADColumnType.DURATION.equals(fieldType)) {
      // ISO-8601 period formats PnYnMnD and PnW
      result = Period.parse(rawValue);
    }

    return result;
  }
}
