package com.bhp.opusb.repository.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.bhp.opusb.domain.enumeration.ADColumnType;

public class ColumnTypeMapper {
  private static final Map<Integer, ADColumnType> JH_COLUMN_TYPES;

  static {
    JH_COLUMN_TYPES = new HashMap<>();
    JH_COLUMN_TYPES.put(Types.NUMERIC, ADColumnType.BIG_DECIMAL);
    JH_COLUMN_TYPES.put(Types.DECIMAL, ADColumnType.BIG_DECIMAL);
    JH_COLUMN_TYPES.put(Types.BIGINT, ADColumnType.LONG);
    JH_COLUMN_TYPES.put(Types.INTEGER, ADColumnType.INTEGER);
    JH_COLUMN_TYPES.put(Types.SMALLINT, ADColumnType.INTEGER);
    JH_COLUMN_TYPES.put(Types.BOOLEAN, ADColumnType.BOOLEAN);
    JH_COLUMN_TYPES.put(Types.BIT, ADColumnType.BOOLEAN);
    JH_COLUMN_TYPES.put(Types.CHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.NCHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.VARCHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.NVARCHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.LONGVARCHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.LONGNVARCHAR, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.OTHER, ADColumnType.STRING);
    JH_COLUMN_TYPES.put(Types.TIMESTAMP, ADColumnType.INSTANT);
    JH_COLUMN_TYPES.put(Types.TIMESTAMP_WITH_TIMEZONE, ADColumnType.ZONED_DATE_TIME);
    JH_COLUMN_TYPES.put(Types.DATE, ADColumnType.LOCAL_DATE);
    JH_COLUMN_TYPES.put(Types.DOUBLE, ADColumnType.DOUBLE);
    JH_COLUMN_TYPES.put(Types.FLOAT, ADColumnType.FLOAT);
    JH_COLUMN_TYPES.put(Types.REAL, ADColumnType.FLOAT);
    JH_COLUMN_TYPES.put(Types.BINARY, ADColumnType.BLOB);
  }

  private ColumnTypeMapper() {
  }

  public static ADColumnType getColumnType(int dataType) {
    return JH_COLUMN_TYPES.getOrDefault(dataType, ADColumnType.STRING);
  }

}