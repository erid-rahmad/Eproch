package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorSuggestionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorSuggestionDTO.class);
        MVendorSuggestionDTO mVendorSuggestionDTO1 = new MVendorSuggestionDTO();
        mVendorSuggestionDTO1.setId(1L);
        MVendorSuggestionDTO mVendorSuggestionDTO2 = new MVendorSuggestionDTO();
        assertThat(mVendorSuggestionDTO1).isNotEqualTo(mVendorSuggestionDTO2);
        mVendorSuggestionDTO2.setId(mVendorSuggestionDTO1.getId());
        assertThat(mVendorSuggestionDTO1).isEqualTo(mVendorSuggestionDTO2);
        mVendorSuggestionDTO2.setId(2L);
        assertThat(mVendorSuggestionDTO1).isNotEqualTo(mVendorSuggestionDTO2);
        mVendorSuggestionDTO1.setId(null);
        assertThat(mVendorSuggestionDTO1).isNotEqualTo(mVendorSuggestionDTO2);
    }
}
