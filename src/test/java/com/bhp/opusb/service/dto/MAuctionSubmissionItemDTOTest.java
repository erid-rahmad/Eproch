package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmissionItemDTO.class);
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO1 = new MAuctionSubmissionItemDTO();
        mAuctionSubmissionItemDTO1.setId(1L);
        MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO2 = new MAuctionSubmissionItemDTO();
        assertThat(mAuctionSubmissionItemDTO1).isNotEqualTo(mAuctionSubmissionItemDTO2);
        mAuctionSubmissionItemDTO2.setId(mAuctionSubmissionItemDTO1.getId());
        assertThat(mAuctionSubmissionItemDTO1).isEqualTo(mAuctionSubmissionItemDTO2);
        mAuctionSubmissionItemDTO2.setId(2L);
        assertThat(mAuctionSubmissionItemDTO1).isNotEqualTo(mAuctionSubmissionItemDTO2);
        mAuctionSubmissionItemDTO1.setId(null);
        assertThat(mAuctionSubmissionItemDTO1).isNotEqualTo(mAuctionSubmissionItemDTO2);
    }
}
