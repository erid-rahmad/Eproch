package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorSuggestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorSuggestion.class);
        MVendorSuggestion mVendorSuggestion1 = new MVendorSuggestion();
        mVendorSuggestion1.setId(1L);
        MVendorSuggestion mVendorSuggestion2 = new MVendorSuggestion();
        mVendorSuggestion2.setId(mVendorSuggestion1.getId());
        assertThat(mVendorSuggestion1).isEqualTo(mVendorSuggestion2);
        mVendorSuggestion2.setId(2L);
        assertThat(mVendorSuggestion1).isNotEqualTo(mVendorSuggestion2);
        mVendorSuggestion1.setId(null);
        assertThat(mVendorSuggestion1).isNotEqualTo(mVendorSuggestion2);
    }
}
