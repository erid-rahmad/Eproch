package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationStepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationStep.class);
        CPrequalificationStep cPrequalificationStep1 = new CPrequalificationStep();
        cPrequalificationStep1.setId(1L);
        CPrequalificationStep cPrequalificationStep2 = new CPrequalificationStep();
        cPrequalificationStep2.setId(cPrequalificationStep1.getId());
        assertThat(cPrequalificationStep1).isEqualTo(cPrequalificationStep2);
        cPrequalificationStep2.setId(2L);
        assertThat(cPrequalificationStep1).isNotEqualTo(cPrequalificationStep2);
        cPrequalificationStep1.setId(null);
        assertThat(cPrequalificationStep1).isNotEqualTo(cPrequalificationStep2);
    }
}
