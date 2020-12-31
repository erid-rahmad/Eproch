package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CGalleryItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CGalleryItemDTO.class);
        CGalleryItemDTO cGalleryItemDTO1 = new CGalleryItemDTO();
        cGalleryItemDTO1.setId(1L);
        CGalleryItemDTO cGalleryItemDTO2 = new CGalleryItemDTO();
        assertThat(cGalleryItemDTO1).isNotEqualTo(cGalleryItemDTO2);
        cGalleryItemDTO2.setId(cGalleryItemDTO1.getId());
        assertThat(cGalleryItemDTO1).isEqualTo(cGalleryItemDTO2);
        cGalleryItemDTO2.setId(2L);
        assertThat(cGalleryItemDTO1).isNotEqualTo(cGalleryItemDTO2);
        cGalleryItemDTO1.setId(null);
        assertThat(cGalleryItemDTO1).isNotEqualTo(cGalleryItemDTO2);
    }
}
