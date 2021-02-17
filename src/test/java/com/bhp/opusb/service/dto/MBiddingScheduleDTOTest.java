package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBiddingScheduleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBiddingScheduleDTO.class);
        MBiddingScheduleDTO mBiddingScheduleDTO1 = new MBiddingScheduleDTO();
        mBiddingScheduleDTO1.setId(1L);
        MBiddingScheduleDTO mBiddingScheduleDTO2 = new MBiddingScheduleDTO();
        assertThat(mBiddingScheduleDTO1).isNotEqualTo(mBiddingScheduleDTO2);
        mBiddingScheduleDTO2.setId(mBiddingScheduleDTO1.getId());
        assertThat(mBiddingScheduleDTO1).isEqualTo(mBiddingScheduleDTO2);
        mBiddingScheduleDTO2.setId(2L);
        assertThat(mBiddingScheduleDTO1).isNotEqualTo(mBiddingScheduleDTO2);
        mBiddingScheduleDTO1.setId(null);
        assertThat(mBiddingScheduleDTO1).isNotEqualTo(mBiddingScheduleDTO2);
    }
}
