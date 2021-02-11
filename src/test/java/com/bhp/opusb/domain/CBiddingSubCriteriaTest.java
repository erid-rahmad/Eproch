package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingSubCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingSubCriteria.class);
        CBiddingSubCriteria cBiddingSubCriteria1 = new CBiddingSubCriteria();
        cBiddingSubCriteria1.setId(1L);
        CBiddingSubCriteria cBiddingSubCriteria2 = new CBiddingSubCriteria();
        cBiddingSubCriteria2.setId(cBiddingSubCriteria1.getId());
        assertThat(cBiddingSubCriteria1).isEqualTo(cBiddingSubCriteria2);
        cBiddingSubCriteria2.setId(2L);
        assertThat(cBiddingSubCriteria1).isNotEqualTo(cBiddingSubCriteria2);
        cBiddingSubCriteria1.setId(null);
        assertThat(cBiddingSubCriteria1).isNotEqualTo(cBiddingSubCriteria2);
    }
}
