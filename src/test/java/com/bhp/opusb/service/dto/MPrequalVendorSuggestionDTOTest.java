package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalVendorSuggestionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalVendorSuggestionDTO.class);
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO1 = new MPrequalVendorSuggestionDTO();
        mPrequalVendorSuggestionDTO1.setId(1L);
        MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO2 = new MPrequalVendorSuggestionDTO();
        assertThat(mPrequalVendorSuggestionDTO1).isNotEqualTo(mPrequalVendorSuggestionDTO2);
        mPrequalVendorSuggestionDTO2.setId(mPrequalVendorSuggestionDTO1.getId());
        assertThat(mPrequalVendorSuggestionDTO1).isEqualTo(mPrequalVendorSuggestionDTO2);
        mPrequalVendorSuggestionDTO2.setId(2L);
        assertThat(mPrequalVendorSuggestionDTO1).isNotEqualTo(mPrequalVendorSuggestionDTO2);
        mPrequalVendorSuggestionDTO1.setId(null);
        assertThat(mPrequalVendorSuggestionDTO1).isNotEqualTo(mPrequalVendorSuggestionDTO2);
    }
}
