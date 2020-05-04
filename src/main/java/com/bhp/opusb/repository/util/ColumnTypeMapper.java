package com.bhp.opusb.repository.util;

import java.sql.Types;

import com.bhp.opusb.domain.enumeration.ADColumnType;

public class ColumnTypeMapper {

  public static ADColumnType getColumnType(int dataType) {
    if (dataType == Types.BIGINT) {
      return ADColumnType.BIG_DECIMAL;
    } else if (dataType == Types.BIT) {
      return ADColumnType.BOOLEAN;
    } else if (dataType == Types.VARCHAR) {
      return ADColumnType.STRING;
    } else if (dataType == Types.TIMESTAMP) {
      return ADColumnType.INSTANT;
    } else if (dataType == Types.TIMESTAMP_WITH_TIMEZONE) {
      return ADColumnType.ZONED_DATE_TIME;
    } else if (dataType == Types.DATE) {
      return ADColumnType.LOCAL_DATE;
    } else if (dataType == Types.DOUBLE) {
      return ADColumnType.DOUBLE;
    } else if (dataType == Types.FLOAT) {
      return ADColumnType.FLOAT;
    } else if (dataType == Types.INTEGER) {
      return ADColumnType.INTEGER;
    }

    return null;
  }

}