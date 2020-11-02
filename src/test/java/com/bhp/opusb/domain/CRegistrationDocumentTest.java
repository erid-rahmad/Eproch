package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegistrationDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegistrationDocument.class);
        CRegistrationDocument cRegistrationDocument1 = new CRegistrationDocument();
        cRegistrationDocument1.setId(1L);
        CRegistrationDocument cRegistrationDocument2 = new CRegistrationDocument();
        cRegistrationDocument2.setId(cRegistrationDocument1.getId());
        assertThat(cRegistrationDocument1).isEqualTo(cRegistrationDocument2);
        cRegistrationDocument2.setId(2L);
        assertThat(cRegistrationDocument1).isNotEqualTo(cRegistrationDocument2);
        cRegistrationDocument1.setId(null);
        assertThat(cRegistrationDocument1).isNotEqualTo(cRegistrationDocument2);
    }
}
