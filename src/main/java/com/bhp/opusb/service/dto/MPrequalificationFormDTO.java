package com.bhp.opusb.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;

import com.bhp.opusb.domain.enumeration.MPrequalificationProcess;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.bhp.opusb.domain.MPrequalificationInformation} entity.
 */
public class MPrequalificationFormDTO extends MPrequalificationInformationDTO {
    private static final long serialVersionUID = 1L;
    private MPrequalificationProcess step;
    
    public MPrequalificationProcess getStep() {
        return step;
    }
    public void setStep(MPrequalificationProcess step) {
        this.step = step;
    }
}
