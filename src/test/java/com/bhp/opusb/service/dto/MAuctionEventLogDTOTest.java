package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionEventLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionEventLogDTO.class);
        MAuctionEventLogDTO mAuctionEventLogDTO1 = new MAuctionEventLogDTO();
        mAuctionEventLogDTO1.setId(1L);
        MAuctionEventLogDTO mAuctionEventLogDTO2 = new MAuctionEventLogDTO();
        assertThat(mAuctionEventLogDTO1).isNotEqualTo(mAuctionEventLogDTO2);
        mAuctionEventLogDTO2.setId(mAuctionEventLogDTO1.getId());
        assertThat(mAuctionEventLogDTO1).isEqualTo(mAuctionEventLogDTO2);
        mAuctionEventLogDTO2.setId(2L);
        assertThat(mAuctionEventLogDTO1).isNotEqualTo(mAuctionEventLogDTO2);
        mAuctionEventLogDTO1.setId(null);
        assertThat(mAuctionEventLogDTO1).isNotEqualTo(mAuctionEventLogDTO2);
    }
}
