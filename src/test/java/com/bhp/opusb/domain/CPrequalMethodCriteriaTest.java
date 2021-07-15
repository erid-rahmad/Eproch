package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalMethodCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalMethodCriteria.class);
        CPrequalMethodCriteria cPrequalMethodCriteria1 = new CPrequalMethodCriteria();
        cPrequalMethodCriteria1.setId(1L);
        CPrequalMethodCriteria cPrequalMethodCriteria2 = new CPrequalMethodCriteria();
        cPrequalMethodCriteria2.setId(cPrequalMethodCriteria1.getId());
        assertThat(cPrequalMethodCriteria1).isEqualTo(cPrequalMethodCriteria2);
        cPrequalMethodCriteria2.setId(2L);
        assertThat(cPrequalMethodCriteria1).isNotEqualTo(cPrequalMethodCriteria2);
        cPrequalMethodCriteria1.setId(null);
        assertThat(cPrequalMethodCriteria1).isNotEqualTo(cPrequalMethodCriteria2);
    }
}
