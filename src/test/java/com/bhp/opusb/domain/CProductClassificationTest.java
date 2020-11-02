package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductClassificationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductClassification.class);
        CProductClassification cProductClassification1 = new CProductClassification();
        cProductClassification1.setId(1L);
        CProductClassification cProductClassification2 = new CProductClassification();
        cProductClassification2.setId(cProductClassification1.getId());
        assertThat(cProductClassification1).isEqualTo(cProductClassification2);
        cProductClassification2.setId(2L);
        assertThat(cProductClassification1).isNotEqualTo(cProductClassification2);
        cProductClassification1.setId(null);
        assertThat(cProductClassification1).isNotEqualTo(cProductClassification2);
    }
}
