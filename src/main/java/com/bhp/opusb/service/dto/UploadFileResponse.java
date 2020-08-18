package com.bhp.opusb.service.dto;

public class UploadFileResponse {
  private String downloadUri;
  private CAttachmentDTO attachment;
  private long size;

  public String getDownloadUri() {
    return downloadUri;
  }

  public void setDownloadUri(String downloadUri) {
    this.downloadUri = downloadUri;
  }

  public CAttachmentDTO getAttachment() {
    return attachment;
  }

  public void setAttachment(CAttachmentDTO attachment) {
    this.attachment = attachment;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}