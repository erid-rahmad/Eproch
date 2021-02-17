package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingCriteriaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingCriteria.class);
        CBiddingCriteria cBiddingCriteria1 = new CBiddingCriteria();
        cBiddingCriteria1.setId(1L);
        CBiddingCriteria cBiddingCriteria2 = new CBiddingCriteria();
        cBiddingCriteria2.setId(cBiddingCriteria1.getId());
        assertThat(cBiddingCriteria1).isEqualTo(cBiddingCriteria2);
        cBiddingCriteria2.setId(2L);
        assertThat(cBiddingCriteria1).isNotEqualTo(cBiddingCriteria2);
        cBiddingCriteria1.setId(null);
        assertThat(cBiddingCriteria1).isNotEqualTo(cBiddingCriteria2);
    }
}
