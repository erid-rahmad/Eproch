package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingSubCriteriaLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingSubCriteriaLine.class);
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine1 = new CBiddingSubCriteriaLine();
        cBiddingSubCriteriaLine1.setId(1L);
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine2 = new CBiddingSubCriteriaLine();
        cBiddingSubCriteriaLine2.setId(cBiddingSubCriteriaLine1.getId());
        assertThat(cBiddingSubCriteriaLine1).isEqualTo(cBiddingSubCriteriaLine2);
        cBiddingSubCriteriaLine2.setId(2L);
        assertThat(cBiddingSubCriteriaLine1).isNotEqualTo(cBiddingSubCriteriaLine2);
        cBiddingSubCriteriaLine1.setId(null);
        assertThat(cBiddingSubCriteriaLine1).isNotEqualTo(cBiddingSubCriteriaLine2);
    }
}
