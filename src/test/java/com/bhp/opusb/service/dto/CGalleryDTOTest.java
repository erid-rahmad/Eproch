package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CGalleryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CGalleryDTO.class);
        CGalleryDTO cGalleryDTO1 = new CGalleryDTO();
        cGalleryDTO1.setId(1L);
        CGalleryDTO cGalleryDTO2 = new CGalleryDTO();
        assertThat(cGalleryDTO1).isNotEqualTo(cGalleryDTO2);
        cGalleryDTO2.setId(cGalleryDTO1.getId());
        assertThat(cGalleryDTO1).isEqualTo(cGalleryDTO2);
        cGalleryDTO2.setId(2L);
        assertThat(cGalleryDTO1).isNotEqualTo(cGalleryDTO2);
        cGalleryDTO1.setId(null);
        assertThat(cGalleryDTO1).isNotEqualTo(cGalleryDTO2);
    }
}
