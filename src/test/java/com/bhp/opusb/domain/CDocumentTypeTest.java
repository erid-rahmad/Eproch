package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CDocumentTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CDocumentType.class);
        CDocumentType cDocumentType1 = new CDocumentType();
        cDocumentType1.setId(1L);
        CDocumentType cDocumentType2 = new CDocumentType();
        cDocumentType2.setId(cDocumentType1.getId());
        assertThat(cDocumentType1).isEqualTo(cDocumentType2);
        cDocumentType2.setId(2L);
        assertThat(cDocumentType1).isNotEqualTo(cDocumentType2);
        cDocumentType1.setId(null);
        assertThat(cDocumentType1).isNotEqualTo(cDocumentType2);
    }
}
