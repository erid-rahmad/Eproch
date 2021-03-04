package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingSubItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingSubItem.class);
        MBiddingSubItem mBiddingSubItem1 = new MBiddingSubItem();
        mBiddingSubItem1.setId(1L);
        MBiddingSubItem mBiddingSubItem2 = new MBiddingSubItem();
        mBiddingSubItem2.setId(mBiddingSubItem1.getId());
        assertThat(mBiddingSubItem1).isEqualTo(mBiddingSubItem2);
        mBiddingSubItem2.setId(2L);
        assertThat(mBiddingSubItem1).isNotEqualTo(mBiddingSubItem2);
        mBiddingSubItem1.setId(null);
        assertThat(mBiddingSubItem1).isNotEqualTo(mBiddingSubItem2);
    }
}
