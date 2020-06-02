package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdValidationRuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdValidationRule.class);
        AdValidationRule adValidationRule1 = new AdValidationRule();
        adValidationRule1.setId(1L);
        AdValidationRule adValidationRule2 = new AdValidationRule();
        adValidationRule2.setId(adValidationRule1.getId());
        assertThat(adValidationRule1).isEqualTo(adValidationRule2);
        adValidationRule2.setId(2L);
        assertThat(adValidationRule1).isNotEqualTo(adValidationRule2);
        adValidationRule1.setId(null);
        assertThat(adValidationRule1).isNotEqualTo(adValidationRule2);
    }
}
