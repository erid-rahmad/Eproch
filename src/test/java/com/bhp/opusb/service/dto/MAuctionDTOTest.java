package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionDTO.class);
        MAuctionDTO mAuctionDTO1 = new MAuctionDTO();
        mAuctionDTO1.setId(1L);
        MAuctionDTO mAuctionDTO2 = new MAuctionDTO();
        assertThat(mAuctionDTO1).isNotEqualTo(mAuctionDTO2);
        mAuctionDTO2.setId(mAuctionDTO1.getId());
        assertThat(mAuctionDTO1).isEqualTo(mAuctionDTO2);
        mAuctionDTO2.setId(2L);
        assertThat(mAuctionDTO1).isNotEqualTo(mAuctionDTO2);
        mAuctionDTO1.setId(null);
        assertThat(mAuctionDTO1).isNotEqualTo(mAuctionDTO2);
    }
}
