package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingAttachmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeetingAttachmentDTO.class);
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO1 = new MPreBidMeetingAttachmentDTO();
        mPreBidMeetingAttachmentDTO1.setId(1L);
        MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO2 = new MPreBidMeetingAttachmentDTO();
        assertThat(mPreBidMeetingAttachmentDTO1).isNotEqualTo(mPreBidMeetingAttachmentDTO2);
        mPreBidMeetingAttachmentDTO2.setId(mPreBidMeetingAttachmentDTO1.getId());
        assertThat(mPreBidMeetingAttachmentDTO1).isEqualTo(mPreBidMeetingAttachmentDTO2);
        mPreBidMeetingAttachmentDTO2.setId(2L);
        assertThat(mPreBidMeetingAttachmentDTO1).isNotEqualTo(mPreBidMeetingAttachmentDTO2);
        mPreBidMeetingAttachmentDTO1.setId(null);
        assertThat(mPreBidMeetingAttachmentDTO1).isNotEqualTo(mPreBidMeetingAttachmentDTO2);
    }
}
