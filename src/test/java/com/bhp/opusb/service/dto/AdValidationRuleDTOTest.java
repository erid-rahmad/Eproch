package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdValidationRuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdValidationRuleDTO.class);
        AdValidationRuleDTO adValidationRuleDTO1 = new AdValidationRuleDTO();
        adValidationRuleDTO1.setId(1L);
        AdValidationRuleDTO adValidationRuleDTO2 = new AdValidationRuleDTO();
        assertThat(adValidationRuleDTO1).isNotEqualTo(adValidationRuleDTO2);
        adValidationRuleDTO2.setId(adValidationRuleDTO1.getId());
        assertThat(adValidationRuleDTO1).isEqualTo(adValidationRuleDTO2);
        adValidationRuleDTO2.setId(2L);
        assertThat(adValidationRuleDTO1).isNotEqualTo(adValidationRuleDTO2);
        adValidationRuleDTO1.setId(null);
        assertThat(adValidationRuleDTO1).isNotEqualTo(adValidationRuleDTO2);
    }
}
