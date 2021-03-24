package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingScheduleAttachmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingScheduleAttachmentDTO.class);
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO1 = new MBiddingScheduleAttachmentDTO();
        mBiddingScheduleAttachmentDTO1.setId(1L);
        MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO2 = new MBiddingScheduleAttachmentDTO();
        assertThat(mBiddingScheduleAttachmentDTO1).isNotEqualTo(mBiddingScheduleAttachmentDTO2);
        mBiddingScheduleAttachmentDTO2.setId(mBiddingScheduleAttachmentDTO1.getId());
        assertThat(mBiddingScheduleAttachmentDTO1).isEqualTo(mBiddingScheduleAttachmentDTO2);
        mBiddingScheduleAttachmentDTO2.setId(2L);
        assertThat(mBiddingScheduleAttachmentDTO1).isNotEqualTo(mBiddingScheduleAttachmentDTO2);
        mBiddingScheduleAttachmentDTO1.setId(null);
        assertThat(mBiddingScheduleAttachmentDTO1).isNotEqualTo(mBiddingScheduleAttachmentDTO2);
    }
}
