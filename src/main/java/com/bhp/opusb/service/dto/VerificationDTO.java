package com.bhp.opusb.service.dto;

import java.util.List;

public class VerificationDTO {
    MVerificationDTO form;
    List<MVerificationLineDTO> line;
    List<MVerificationLineDTO> remove;

    public MVerificationDTO getForm() {
        return form;
    }

    public void setForm(MVerificationDTO form) {
        this.form = form;
    }

    public List<MVerificationLineDTO> getLine() {
        return line;
    }

    public void setLine(List<MVerificationLineDTO> line) {
        this.line = line;
    }

    public List<MVerificationLineDTO> getRemove() {
        return remove;
    }

    public void setRemove(List<MVerificationLineDTO> remove) {
        this.remove = remove;
    }
}
