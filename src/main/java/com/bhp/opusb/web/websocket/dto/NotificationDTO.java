package com.bhp.opusb.web.websocket.dto;

public final class NotificationDTO {
  private final ProcessStatus status;
  private final String message;
  private final ProcessType type;

  public NotificationDTO(ProcessStatus status, String message, ProcessType type) {
    this.status = status;
    this.message = message;
    this.type = type;
  }

  public ProcessStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public ProcessType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "BackgroundProcessStatus [message=" + message + ", status=" + status + ", type=" + type + "]";
  }
}
