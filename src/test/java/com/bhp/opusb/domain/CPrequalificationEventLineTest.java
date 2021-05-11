package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationEventLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationEventLine.class);
        CPrequalificationEventLine cPrequalificationEventLine1 = new CPrequalificationEventLine();
        cPrequalificationEventLine1.setId(1L);
        CPrequalificationEventLine cPrequalificationEventLine2 = new CPrequalificationEventLine();
        cPrequalificationEventLine2.setId(cPrequalificationEventLine1.getId());
        assertThat(cPrequalificationEventLine1).isEqualTo(cPrequalificationEventLine2);
        cPrequalificationEventLine2.setId(2L);
        assertThat(cPrequalificationEventLine1).isNotEqualTo(cPrequalificationEventLine2);
        cPrequalificationEventLine1.setId(null);
        assertThat(cPrequalificationEventLine1).isNotEqualTo(cPrequalificationEventLine2);
    }
}
