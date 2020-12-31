package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CGalleryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CGallery.class);
        CGallery cGallery1 = new CGallery();
        cGallery1.setId(1L);
        CGallery cGallery2 = new CGallery();
        cGallery2.setId(cGallery1.getId());
        assertThat(cGallery1).isEqualTo(cGallery2);
        cGallery2.setId(2L);
        assertThat(cGallery1).isNotEqualTo(cGallery2);
        cGallery1.setId(null);
        assertThat(cGallery1).isNotEqualTo(cGallery2);
    }
}
