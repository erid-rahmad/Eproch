package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationMethodTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationMethod.class);
        CPrequalificationMethod cPrequalificationMethod1 = new CPrequalificationMethod();
        cPrequalificationMethod1.setId(1L);
        CPrequalificationMethod cPrequalificationMethod2 = new CPrequalificationMethod();
        cPrequalificationMethod2.setId(cPrequalificationMethod1.getId());
        assertThat(cPrequalificationMethod1).isEqualTo(cPrequalificationMethod2);
        cPrequalificationMethod2.setId(2L);
        assertThat(cPrequalificationMethod1).isNotEqualTo(cPrequalificationMethod2);
        cPrequalificationMethod1.setId(null);
        assertThat(cPrequalificationMethod1).isNotEqualTo(cPrequalificationMethod2);
    }
}
