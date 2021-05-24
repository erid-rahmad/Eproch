package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionContent.class);
        MAuctionContent mAuctionContent1 = new MAuctionContent();
        mAuctionContent1.setId(1L);
        MAuctionContent mAuctionContent2 = new MAuctionContent();
        mAuctionContent2.setId(mAuctionContent1.getId());
        assertThat(mAuctionContent1).isEqualTo(mAuctionContent2);
        mAuctionContent2.setId(2L);
        assertThat(mAuctionContent1).isNotEqualTo(mAuctionContent2);
        mAuctionContent1.setId(null);
        assertThat(mAuctionContent1).isNotEqualTo(mAuctionContent2);
    }
}
