package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuction.class);
        MAuction mAuction1 = new MAuction();
        mAuction1.setId(1L);
        MAuction mAuction2 = new MAuction();
        mAuction2.setId(mAuction1.getId());
        assertThat(mAuction1).isEqualTo(mAuction2);
        mAuction2.setId(2L);
        assertThat(mAuction1).isNotEqualTo(mAuction2);
        mAuction1.setId(null);
        assertThat(mAuction1).isNotEqualTo(mAuction2);
    }
}
