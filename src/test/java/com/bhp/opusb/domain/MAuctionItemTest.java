package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionItem.class);
        MAuctionItem mAuctionItem1 = new MAuctionItem();
        mAuctionItem1.setId(1L);
        MAuctionItem mAuctionItem2 = new MAuctionItem();
        mAuctionItem2.setId(mAuctionItem1.getId());
        assertThat(mAuctionItem1).isEqualTo(mAuctionItem2);
        mAuctionItem2.setId(2L);
        assertThat(mAuctionItem1).isNotEqualTo(mAuctionItem2);
        mAuctionItem1.setId(null);
        assertThat(mAuctionItem1).isNotEqualTo(mAuctionItem2);
    }
}
