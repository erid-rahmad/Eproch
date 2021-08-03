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
  private List<MContractLineDTO> mContractLineDTOS;

    public MContractDTO getmContractDTO() {
        return mContractDTO;
    }

    public void setmContractDTO(MContractDTO mContractDTO) {
        this.mContractDTO = mContractDTO;
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
            ", mContractLineDTOS=" + mContractLineDTOS +
            '}';
    }
}
