package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalVendorSuggestionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalVendorSuggestion.class);
        MPrequalVendorSuggestion mPrequalVendorSuggestion1 = new MPrequalVendorSuggestion();
        mPrequalVendorSuggestion1.setId(1L);
        MPrequalVendorSuggestion mPrequalVendorSuggestion2 = new MPrequalVendorSuggestion();
        mPrequalVendorSuggestion2.setId(mPrequalVendorSuggestion1.getId());
        assertThat(mPrequalVendorSuggestion1).isEqualTo(mPrequalVendorSuggestion2);
        mPrequalVendorSuggestion2.setId(2L);
        assertThat(mPrequalVendorSuggestion1).isNotEqualTo(mPrequalVendorSuggestion2);
        mPrequalVendorSuggestion1.setId(null);
        assertThat(mPrequalVendorSuggestion1).isNotEqualTo(mPrequalVendorSuggestion2);
    }
}
