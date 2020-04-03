package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class SupportingDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupportingDocument.class);
        SupportingDocument supportingDocument1 = new SupportingDocument();
        supportingDocument1.setId(1L);
        SupportingDocument supportingDocument2 = new SupportingDocument();
        supportingDocument2.setId(supportingDocument1.getId());
        assertThat(supportingDocument1).isEqualTo(supportingDocument2);
        supportingDocument2.setId(2L);
        assertThat(supportingDocument1).isNotEqualTo(supportingDocument2);
        supportingDocument1.setId(null);
        assertThat(supportingDocument1).isNotEqualTo(supportingDocument2);
    }
}
