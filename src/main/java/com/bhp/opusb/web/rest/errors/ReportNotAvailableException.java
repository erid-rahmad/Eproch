package com.bhp.opusb.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ReportNotAvailableException extends AbstractThrowableProblem {

  private final String entityName;
  private final String errorKey;

  public ReportNotAvailableException(String title, String entityName, String errorKey) {
    super(ErrorConstants.DEFAULT_TYPE, title, Status.NOT_FOUND);
    this.entityName = entityName;
    this.errorKey = errorKey;
  }

  public String getEntityName() {
    return entityName;
  }

  public String getErrorKey() {
    return errorKey;
  }
  
}
