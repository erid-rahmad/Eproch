package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBidding.class);
        MBidding mBidding1 = new MBidding();
        mBidding1.setId(1L);
        MBidding mBidding2 = new MBidding();
        mBidding2.setId(mBidding1.getId());
        assertThat(mBidding1).isEqualTo(mBidding2);
        mBidding2.setId(2L);
        assertThat(mBidding1).isNotEqualTo(mBidding2);
        mBidding1.setId(null);
        assertThat(mBidding1).isNotEqualTo(mBidding2);
    }
}
