package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegistrationDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegistrationDocumentDTO.class);
        CRegistrationDocumentDTO cRegistrationDocumentDTO1 = new CRegistrationDocumentDTO();
        cRegistrationDocumentDTO1.setId(1L);
        CRegistrationDocumentDTO cRegistrationDocumentDTO2 = new CRegistrationDocumentDTO();
        assertThat(cRegistrationDocumentDTO1).isNotEqualTo(cRegistrationDocumentDTO2);
        cRegistrationDocumentDTO2.setId(cRegistrationDocumentDTO1.getId());
        assertThat(cRegistrationDocumentDTO1).isEqualTo(cRegistrationDocumentDTO2);
        cRegistrationDocumentDTO2.setId(2L);
        assertThat(cRegistrationDocumentDTO1).isNotEqualTo(cRegistrationDocumentDTO2);
        cRegistrationDocumentDTO1.setId(null);
        assertThat(cRegistrationDocumentDTO1).isNotEqualTo(cRegistrationDocumentDTO2);
    }
}
