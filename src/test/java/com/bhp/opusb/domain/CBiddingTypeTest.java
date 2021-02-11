package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingType.class);
        CBiddingType cBiddingType1 = new CBiddingType();
        cBiddingType1.setId(1L);
        CBiddingType cBiddingType2 = new CBiddingType();
        cBiddingType2.setId(cBiddingType1.getId());
        assertThat(cBiddingType1).isEqualTo(cBiddingType2);
        cBiddingType2.setId(2L);
        assertThat(cBiddingType1).isNotEqualTo(cBiddingType2);
        cBiddingType1.setId(null);
        assertThat(cBiddingType1).isNotEqualTo(cBiddingType2);
    }
}
