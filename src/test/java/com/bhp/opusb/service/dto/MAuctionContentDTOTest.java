package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionContentDTO.class);
        MAuctionContentDTO mAuctionContentDTO1 = new MAuctionContentDTO();
        mAuctionContentDTO1.setId(1L);
        MAuctionContentDTO mAuctionContentDTO2 = new MAuctionContentDTO();
        assertThat(mAuctionContentDTO1).isNotEqualTo(mAuctionContentDTO2);
        mAuctionContentDTO2.setId(mAuctionContentDTO1.getId());
        assertThat(mAuctionContentDTO1).isEqualTo(mAuctionContentDTO2);
        mAuctionContentDTO2.setId(2L);
        assertThat(mAuctionContentDTO1).isNotEqualTo(mAuctionContentDTO2);
        mAuctionContentDTO1.setId(null);
        assertThat(mAuctionContentDTO1).isNotEqualTo(mAuctionContentDTO2);
    }
}
