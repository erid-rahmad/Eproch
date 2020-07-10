package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegistrationDocTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegistrationDocType.class);
        CRegistrationDocType cRegistrationDocType1 = new CRegistrationDocType();
        cRegistrationDocType1.setId(1L);
        CRegistrationDocType cRegistrationDocType2 = new CRegistrationDocType();
        cRegistrationDocType2.setId(cRegistrationDocType1.getId());
        assertThat(cRegistrationDocType1).isEqualTo(cRegistrationDocType2);
        cRegistrationDocType2.setId(2L);
        assertThat(cRegistrationDocType1).isNotEqualTo(cRegistrationDocType2);
        cRegistrationDocType1.setId(null);
        assertThat(cRegistrationDocType1).isNotEqualTo(cRegistrationDocType2);
    }
}
