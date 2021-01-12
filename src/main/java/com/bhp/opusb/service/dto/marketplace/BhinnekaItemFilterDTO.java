package com.bhp.opusb.service.dto.marketplace;

import java.util.List;

public class BhinnekaItemFilterDTO {
  private Filter filter;

  public Filter getFilter() {
    return filter;
  }

  public void setFilter(Filter filter) {
    this.filter = filter;
  }
  
  public static class Filter {
    private int code;
    private String message;
    private List<BhinnekaItemDetailDTO> result;

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public List<BhinnekaItemDetailDTO> getResult() {
      return result;
    }

    public void setResult(List<BhinnekaItemDetailDTO> result) {
      this.result = result;
    }
  }
}
