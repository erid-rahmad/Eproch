package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingParticipantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeetingParticipantDTO.class);
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO1 = new MPreBidMeetingParticipantDTO();
        mPreBidMeetingParticipantDTO1.setId(1L);
        MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO2 = new MPreBidMeetingParticipantDTO();
        assertThat(mPreBidMeetingParticipantDTO1).isNotEqualTo(mPreBidMeetingParticipantDTO2);
        mPreBidMeetingParticipantDTO2.setId(mPreBidMeetingParticipantDTO1.getId());
        assertThat(mPreBidMeetingParticipantDTO1).isEqualTo(mPreBidMeetingParticipantDTO2);
        mPreBidMeetingParticipantDTO2.setId(2L);
        assertThat(mPreBidMeetingParticipantDTO1).isNotEqualTo(mPreBidMeetingParticipantDTO2);
        mPreBidMeetingParticipantDTO1.setId(null);
        assertThat(mPreBidMeetingParticipantDTO1).isNotEqualTo(mPreBidMeetingParticipantDTO2);
    }
}
