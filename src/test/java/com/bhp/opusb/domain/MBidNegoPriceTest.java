package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBidNegoPriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBidNegoPrice.class);
        MBidNegoPrice mBidNegoPrice1 = new MBidNegoPrice();
        mBidNegoPrice1.setId(1L);
        MBidNegoPrice mBidNegoPrice2 = new MBidNegoPrice();
        mBidNegoPrice2.setId(mBidNegoPrice1.getId());
        assertThat(mBidNegoPrice1).isEqualTo(mBidNegoPrice2);
        mBidNegoPrice2.setId(2L);
        assertThat(mBidNegoPrice1).isNotEqualTo(mBidNegoPrice2);
        mBidNegoPrice1.setId(null);
        assertThat(mBidNegoPrice1).isNotEqualTo(mBidNegoPrice2);
    }
}
