package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxCategory.class);
        CTaxCategory cTaxCategory1 = new CTaxCategory();
        cTaxCategory1.setId(1L);
        CTaxCategory cTaxCategory2 = new CTaxCategory();
        cTaxCategory2.setId(cTaxCategory1.getId());
        assertThat(cTaxCategory1).isEqualTo(cTaxCategory2);
        cTaxCategory2.setId(2L);
        assertThat(cTaxCategory1).isNotEqualTo(cTaxCategory2);
        cTaxCategory1.setId(null);
        assertThat(cTaxCategory1).isNotEqualTo(cTaxCategory2);
    }
}
