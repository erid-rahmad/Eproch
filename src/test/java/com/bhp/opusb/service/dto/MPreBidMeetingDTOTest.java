package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreBidMeetingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreBidMeetingDTO.class);
        MPreBidMeetingDTO mPreBidMeetingDTO1 = new MPreBidMeetingDTO();
        mPreBidMeetingDTO1.setId(1L);
        MPreBidMeetingDTO mPreBidMeetingDTO2 = new MPreBidMeetingDTO();
        assertThat(mPreBidMeetingDTO1).isNotEqualTo(mPreBidMeetingDTO2);
        mPreBidMeetingDTO2.setId(mPreBidMeetingDTO1.getId());
        assertThat(mPreBidMeetingDTO1).isEqualTo(mPreBidMeetingDTO2);
        mPreBidMeetingDTO2.setId(2L);
        assertThat(mPreBidMeetingDTO1).isNotEqualTo(mPreBidMeetingDTO2);
        mPreBidMeetingDTO1.setId(null);
        assertThat(mPreBidMeetingDTO1).isNotEqualTo(mPreBidMeetingDTO2);
    }
}
