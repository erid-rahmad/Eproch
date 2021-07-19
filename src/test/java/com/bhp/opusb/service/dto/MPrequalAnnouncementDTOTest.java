package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalAnnouncementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalAnnouncementDTO.class);
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO1 = new MPrequalAnnouncementDTO();
        mPrequalAnnouncementDTO1.setId(1L);
        MPrequalAnnouncementDTO mPrequalAnnouncementDTO2 = new MPrequalAnnouncementDTO();
        assertThat(mPrequalAnnouncementDTO1).isNotEqualTo(mPrequalAnnouncementDTO2);
        mPrequalAnnouncementDTO2.setId(mPrequalAnnouncementDTO1.getId());
        assertThat(mPrequalAnnouncementDTO1).isEqualTo(mPrequalAnnouncementDTO2);
        mPrequalAnnouncementDTO2.setId(2L);
        assertThat(mPrequalAnnouncementDTO1).isNotEqualTo(mPrequalAnnouncementDTO2);
        mPrequalAnnouncementDTO1.setId(null);
        assertThat(mPrequalAnnouncementDTO1).isNotEqualTo(mPrequalAnnouncementDTO2);
    }
}
