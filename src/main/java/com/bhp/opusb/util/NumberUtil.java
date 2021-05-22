package com.bhp.opusb.util;

public class NumberUtil {
  
  private NumberUtil() {}

  public static long getLongValue(Object value) {
    long result = 0;

    if (value == null) {
      return result;
    }

    if (value instanceof Long) {
      result = (Long) value;
    } else if (value instanceof Integer) {
      result = (Integer) value;
    }

    return result;
  }
}
