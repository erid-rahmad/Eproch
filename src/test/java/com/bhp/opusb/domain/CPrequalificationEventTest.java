package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationEvent.class);
        CPrequalificationEvent cPrequalificationEvent1 = new CPrequalificationEvent();
        cPrequalificationEvent1.setId(1L);
        CPrequalificationEvent cPrequalificationEvent2 = new CPrequalificationEvent();
        cPrequalificationEvent2.setId(cPrequalificationEvent1.getId());
        assertThat(cPrequalificationEvent1).isEqualTo(cPrequalificationEvent2);
        cPrequalificationEvent2.setId(2L);
        assertThat(cPrequalificationEvent1).isNotEqualTo(cPrequalificationEvent2);
        cPrequalificationEvent1.setId(null);
        assertThat(cPrequalificationEvent1).isNotEqualTo(cPrequalificationEvent2);
    }
}
