package com.bhp.opusb.workflow;

import java.math.BigDecimal;

public interface WorkflowDoc {

    public void setDocumentStatus(String documentStatus);
    public String getDocumentStatus();

    public void setApproved(Boolean isApproved);
    public Boolean isApproved();

    public void setProcessing(Boolean isProcessing);
    public Boolean isProcessing();

    public void setProcessed(Boolean isProcessed);
    public Boolean isProcessed();

    public void setDocumentAction(String docAction);
    public String getDocumentAction();

    public String getSummary();
    public String getDocumentNo();
    public BigDecimal getApprovalAmount();
}