package com.bhp.opusb.service.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MContract} entity.
 */
public class MContractToPoDTO extends AbstractAuditingDTO {

  private MContractDTO mContractDTO;
  private Long paymentTermId;
  private Long warehouseId;
  private Long documentTypeId;
  private List<MContractLineDTO> mContractLineDTOS;

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public MContractDTO getmContractDTO() {
        return mContractDTO;
    }

    public void setmContractDTO(MContractDTO mContractDTO) {
        this.mContractDTO = mContractDTO;
    }

    public Long getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(Long paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public Long getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(Long documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public List<MContractLineDTO> getmContractLineDTOS() {
        return mContractLineDTOS;
    }

    public void setmContractLineDTOS(List<MContractLineDTO> mContractLineDTOS) {
        this.mContractLineDTOS = mContractLineDTOS;
    }

    @Override
    public String toString() {
        return "MContractToPoDTO{" +
            "mContractDTO=" + mContractDTO +
            ", paymentTermId=" + paymentTermId +
            ", warehouseId=" + warehouseId +
            ", documentTypeId=" + documentTypeId +
            ", mContractLineDTOS=" + mContractLineDTOS +
            '}';
    }
}
