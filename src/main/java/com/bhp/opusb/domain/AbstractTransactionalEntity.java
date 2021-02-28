package com.bhp.opusb.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractTransactionalEntity<T extends AbstractTransactionalEntity<T>> extends AbstractAuditingEntity {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Column(name = "date_trx", nullable = false)
  private LocalDate dateTrx;

  @Size(max = 30)
  @Column(name = "document_no", length = 30)
  private String documentNo;

  @NotNull
  @Size(max = 10)
  @Column(name = "document_action", length = 10, nullable = false)
  private String documentAction;

  @NotNull
  @Size(max = 10)
  @Column(name = "document_status", length = 10, nullable = false)
  private String documentStatus;

  @Column(name = "approved")
  private Boolean approved;

  @Column(name = "processed")
  private Boolean processed;

  public LocalDate getDateTrx() {
    return dateTrx;
  }

  public T dateTrx(LocalDate dateTrx) {
    this.dateTrx = dateTrx;
    return (T) this;
  }

  public void setDateTrx(LocalDate dateTrx) {
    this.dateTrx = dateTrx;
  }

  public String getDocumentNo() {
    return documentNo;
  }

  public T documentNo(String documentNo) {
    this.documentNo = documentNo;
    return (T) this;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public String getDocumentAction() {
    return documentAction;
  }

  public T documentAction(String documentAction) {
    this.documentAction = documentAction;
    return (T) this;
  }

  public void setDocumentAction(String documentAction) {
    this.documentAction = documentAction;
  }

  public String getDocumentStatus() {
    return documentStatus;
  }

  public T documentStatus(String documentStatus) {
    this.documentStatus = documentStatus;
    return (T) this;
  }

  public void setDocumentStatus(String documentStatus) {
    this.documentStatus = documentStatus;
  }

  public Boolean isApproved() {
    return approved;
  }

  public T approved(Boolean approved) {
    this.approved = approved;
    return (T) this;
  }

  public void setApproved(Boolean approved) {
    this.approved = approved;
  }

  public Boolean isProcessed() {
    return processed;
  }

  public T processed(Boolean processed) {
    this.processed = processed;
    return (T) this;
  }

  public void setProcessed(Boolean processed) {
    this.processed = processed;
  }

}
