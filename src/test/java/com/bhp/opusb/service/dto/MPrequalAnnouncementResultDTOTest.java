package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalAnnouncementResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalAnnouncementResultDTO.class);
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO1 = new MPrequalAnnouncementResultDTO();
        mPrequalAnnouncementResultDTO1.setId(1L);
        MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO2 = new MPrequalAnnouncementResultDTO();
        assertThat(mPrequalAnnouncementResultDTO1).isNotEqualTo(mPrequalAnnouncementResultDTO2);
        mPrequalAnnouncementResultDTO2.setId(mPrequalAnnouncementResultDTO1.getId());
        assertThat(mPrequalAnnouncementResultDTO1).isEqualTo(mPrequalAnnouncementResultDTO2);
        mPrequalAnnouncementResultDTO2.setId(2L);
        assertThat(mPrequalAnnouncementResultDTO1).isNotEqualTo(mPrequalAnnouncementResultDTO2);
        mPrequalAnnouncementResultDTO1.setId(null);
        assertThat(mPrequalAnnouncementResultDTO1).isNotEqualTo(mPrequalAnnouncementResultDTO2);
    }
}
