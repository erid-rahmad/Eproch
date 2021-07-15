package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodSubCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodSubCriteria.class);
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria1 = new CPrequalMethodSubCriteria();
        cPrequalMethodSubCriteria1.setId(1L);
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria2 = new CPrequalMethodSubCriteria();
        cPrequalMethodSubCriteria2.setId(cPrequalMethodSubCriteria1.getId());
        assertThat(cPrequalMethodSubCriteria1).isEqualTo(cPrequalMethodSubCriteria2);
        cPrequalMethodSubCriteria2.setId(2L);
        assertThat(cPrequalMethodSubCriteria1).isNotEqualTo(cPrequalMethodSubCriteria2);
        cPrequalMethodSubCriteria1.setId(null);
        assertThat(cPrequalMethodSubCriteria1).isNotEqualTo(cPrequalMethodSubCriteria2);
    }
}
