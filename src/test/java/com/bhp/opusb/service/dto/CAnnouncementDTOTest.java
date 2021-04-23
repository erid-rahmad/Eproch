package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAnnouncementDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAnnouncementDTO.class);
        CAnnouncementDTO cAnnouncementDTO1 = new CAnnouncementDTO();
        cAnnouncementDTO1.setId(1L);
        CAnnouncementDTO cAnnouncementDTO2 = new CAnnouncementDTO();
        assertThat(cAnnouncementDTO1).isNotEqualTo(cAnnouncementDTO2);
        cAnnouncementDTO2.setId(cAnnouncementDTO1.getId());
        assertThat(cAnnouncementDTO1).isEqualTo(cAnnouncementDTO2);
        cAnnouncementDTO2.setId(2L);
        assertThat(cAnnouncementDTO1).isNotEqualTo(cAnnouncementDTO2);
        cAnnouncementDTO1.setId(null);
        assertThat(cAnnouncementDTO1).isNotEqualTo(cAnnouncementDTO2);
    }
}
