package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionSubmissionLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionSubmissionLogDTO.class);
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO1 = new MAuctionSubmissionLogDTO();
        mAuctionSubmissionLogDTO1.setId(1L);
        MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO2 = new MAuctionSubmissionLogDTO();
        assertThat(mAuctionSubmissionLogDTO1).isNotEqualTo(mAuctionSubmissionLogDTO2);
        mAuctionSubmissionLogDTO2.setId(mAuctionSubmissionLogDTO1.getId());
        assertThat(mAuctionSubmissionLogDTO1).isEqualTo(mAuctionSubmissionLogDTO2);
        mAuctionSubmissionLogDTO2.setId(2L);
        assertThat(mAuctionSubmissionLogDTO1).isNotEqualTo(mAuctionSubmissionLogDTO2);
        mAuctionSubmissionLogDTO1.setId(null);
        assertThat(mAuctionSubmissionLogDTO1).isNotEqualTo(mAuctionSubmissionLogDTO2);
    }
}
