package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBidNegoPriceLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBidNegoPriceLine.class);
        MBidNegoPriceLine mBidNegoPriceLine1 = new MBidNegoPriceLine();
        mBidNegoPriceLine1.setId(1L);
        MBidNegoPriceLine mBidNegoPriceLine2 = new MBidNegoPriceLine();
        mBidNegoPriceLine2.setId(mBidNegoPriceLine1.getId());
        assertThat(mBidNegoPriceLine1).isEqualTo(mBidNegoPriceLine2);
        mBidNegoPriceLine2.setId(2L);
        assertThat(mBidNegoPriceLine1).isNotEqualTo(mBidNegoPriceLine2);
        mBidNegoPriceLine1.setId(null);
        assertThat(mBidNegoPriceLine1).isNotEqualTo(mBidNegoPriceLine2);
    }
}
