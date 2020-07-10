package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAttachmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAttachmentDTO.class);
        CAttachmentDTO cAttachmentDTO1 = new CAttachmentDTO();
        cAttachmentDTO1.setId(1L);
        CAttachmentDTO cAttachmentDTO2 = new CAttachmentDTO();
        assertThat(cAttachmentDTO1).isNotEqualTo(cAttachmentDTO2);
        cAttachmentDTO2.setId(cAttachmentDTO1.getId());
        assertThat(cAttachmentDTO1).isEqualTo(cAttachmentDTO2);
        cAttachmentDTO2.setId(2L);
        assertThat(cAttachmentDTO1).isNotEqualTo(cAttachmentDTO2);
        cAttachmentDTO1.setId(null);
        assertThat(cAttachmentDTO1).isNotEqualTo(cAttachmentDTO2);
    }
}
