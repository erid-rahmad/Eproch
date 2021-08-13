package com.bhp.opusb.service.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO representing XXBHP_PREQ_HEADERS_V.
 */
public class MRequisitionPayloadDTO extends AbstractAuditingDTO {

    private static final long serialVersionUID = 1L;

    @JsonProperty("ORG_ID")
    private Long organizationCode;

    @JsonProperty("ORG_NAME")
    private String organizationName;

    @JsonProperty("CREATION_DATE")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MMM-yy")
    private LocalDate dateTrx;

    @JsonProperty("REQUISITION_HEADER_ID")
    private Long requisitionHeaderId;

    @JsonProperty("SEGMENT1")
    private String documentNo;

    @JsonProperty("DESCRIPTION")
    private String description;

    public Long getOrganizationCode() {
        return organizationCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Long getRequisitionHeaderId() {
        return requisitionHeaderId;
    }

    public void setRequisitionHeaderId(Long requisitionHeaderId) {
        this.requisitionHeaderId = requisitionHeaderId;
    }

    public LocalDate getDateTrx() {
        return dateTrx;
    }

    public void setDateTrx(LocalDate dateTrx) {
        this.dateTrx = dateTrx;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setOrganizationCode(Long organizationCode) {
        this.organizationCode = organizationCode;
    }


}
