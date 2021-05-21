package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAnnouncementResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAnnouncementResultDTO.class);
        CAnnouncementResultDTO cAnnouncementResultDTO1 = new CAnnouncementResultDTO();
        cAnnouncementResultDTO1.setId(1L);
        CAnnouncementResultDTO cAnnouncementResultDTO2 = new CAnnouncementResultDTO();
        assertThat(cAnnouncementResultDTO1).isNotEqualTo(cAnnouncementResultDTO2);
        cAnnouncementResultDTO2.setId(cAnnouncementResultDTO1.getId());
        assertThat(cAnnouncementResultDTO1).isEqualTo(cAnnouncementResultDTO2);
        cAnnouncementResultDTO2.setId(2L);
        assertThat(cAnnouncementResultDTO1).isNotEqualTo(cAnnouncementResultDTO2);
        cAnnouncementResultDTO1.setId(null);
        assertThat(cAnnouncementResultDTO1).isNotEqualTo(cAnnouncementResultDTO2);
    }
}
