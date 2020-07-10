package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegistrationDocTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegistrationDocTypeDTO.class);
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO1 = new CRegistrationDocTypeDTO();
        cRegistrationDocTypeDTO1.setId(1L);
        CRegistrationDocTypeDTO cRegistrationDocTypeDTO2 = new CRegistrationDocTypeDTO();
        assertThat(cRegistrationDocTypeDTO1).isNotEqualTo(cRegistrationDocTypeDTO2);
        cRegistrationDocTypeDTO2.setId(cRegistrationDocTypeDTO1.getId());
        assertThat(cRegistrationDocTypeDTO1).isEqualTo(cRegistrationDocTypeDTO2);
        cRegistrationDocTypeDTO2.setId(2L);
        assertThat(cRegistrationDocTypeDTO1).isNotEqualTo(cRegistrationDocTypeDTO2);
        cRegistrationDocTypeDTO1.setId(null);
        assertThat(cRegistrationDocTypeDTO1).isNotEqualTo(cRegistrationDocTypeDTO2);
    }
}
