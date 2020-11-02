package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAttachmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAttachment.class);
        CAttachment cAttachment1 = new CAttachment();
        cAttachment1.setId(1L);
        CAttachment cAttachment2 = new CAttachment();
        cAttachment2.setId(cAttachment1.getId());
        assertThat(cAttachment1).isEqualTo(cAttachment2);
        cAttachment2.setId(2L);
        assertThat(cAttachment1).isNotEqualTo(cAttachment2);
        cAttachment1.setId(null);
        assertThat(cAttachment1).isNotEqualTo(cAttachment2);
    }
}
