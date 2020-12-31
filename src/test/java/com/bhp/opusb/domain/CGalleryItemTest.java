package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CGalleryItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CGalleryItem.class);
        CGalleryItem cGalleryItem1 = new CGalleryItem();
        cGalleryItem1.setId(1L);
        CGalleryItem cGalleryItem2 = new CGalleryItem();
        cGalleryItem2.setId(cGalleryItem1.getId());
        assertThat(cGalleryItem1).isEqualTo(cGalleryItem2);
        cGalleryItem2.setId(2L);
        assertThat(cGalleryItem1).isNotEqualTo(cGalleryItem2);
        cGalleryItem1.setId(null);
        assertThat(cGalleryItem1).isNotEqualTo(cGalleryItem2);
    }
}
