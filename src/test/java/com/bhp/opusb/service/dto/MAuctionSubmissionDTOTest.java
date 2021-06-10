package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmissionDTO.class);
        MAuctionSubmissionDTO mAuctionSubmissionDTO1 = new MAuctionSubmissionDTO();
        mAuctionSubmissionDTO1.setId(1L);
        MAuctionSubmissionDTO mAuctionSubmissionDTO2 = new MAuctionSubmissionDTO();
        assertThat(mAuctionSubmissionDTO1).isNotEqualTo(mAuctionSubmissionDTO2);
        mAuctionSubmissionDTO2.setId(mAuctionSubmissionDTO1.getId());
        assertThat(mAuctionSubmissionDTO1).isEqualTo(mAuctionSubmissionDTO2);
        mAuctionSubmissionDTO2.setId(2L);
        assertThat(mAuctionSubmissionDTO1).isNotEqualTo(mAuctionSubmissionDTO2);
        mAuctionSubmissionDTO1.setId(null);
        assertThat(mAuctionSubmissionDTO1).isNotEqualTo(mAuctionSubmissionDTO2);
    }
}
