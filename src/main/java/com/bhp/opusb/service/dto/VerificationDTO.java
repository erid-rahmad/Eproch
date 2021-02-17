package com.bhp.opusb.service.dto;

import java.util.ArrayList;
import java.util.List;

public class VerificationDTO {
    MVerificationDTO form;
    List<MVerificationLineDTO> lines;
    List<MVerificationLineDTO> removedLines = new ArrayList<>();

    public MVerificationDTO getForm() {
        return form;
    }

    public void setForm(MVerificationDTO form) {
        this.form = form;
    }

    public List<MVerificationLineDTO> getLines() {
        return lines;
    }

    public void setLines(List<MVerificationLineDTO> lines) {
        this.lines = lines;
    }

    public List<MVerificationLineDTO> getRemovedLines() {
        return removedLines;
    }

    public void setRemovedLines(List<MVerificationLineDTO> removedLines) {
        this.removedLines = removedLines;
    }
}
