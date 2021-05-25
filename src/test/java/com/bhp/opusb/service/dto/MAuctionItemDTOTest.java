package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionItemDTO.class);
        MAuctionItemDTO mAuctionItemDTO1 = new MAuctionItemDTO();
        mAuctionItemDTO1.setId(1L);
        MAuctionItemDTO mAuctionItemDTO2 = new MAuctionItemDTO();
        assertThat(mAuctionItemDTO1).isNotEqualTo(mAuctionItemDTO2);
        mAuctionItemDTO2.setId(mAuctionItemDTO1.getId());
        assertThat(mAuctionItemDTO1).isEqualTo(mAuctionItemDTO2);
        mAuctionItemDTO2.setId(2L);
        assertThat(mAuctionItemDTO1).isNotEqualTo(mAuctionItemDTO2);
        mAuctionItemDTO1.setId(null);
        assertThat(mAuctionItemDTO1).isNotEqualTo(mAuctionItemDTO2);
    }
}
