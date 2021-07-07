package com.bhp.opusb.service.dto;

import java.time.Instant;
import java.util.Date;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MBidding} entity.
 */
public class DashboardMyDocument extends AbstractAuditingDTO {

    private String DocumentNo;
    private String Title;
    private String Status;
    private Instant date;


    public String getDocumentNo() {
        return DocumentNo;
    }

    public void setDocumentNo(String documentNo) {
        DocumentNo = documentNo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DashboardMyDocument{" +
            "DocumentNo='" + DocumentNo + '\'' +
            ", Title='" + Title + '\'' +
            ", Status='" + Status + '\'' +
            ", date=" + date +
            '}';
    }
}
