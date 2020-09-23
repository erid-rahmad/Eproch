package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class COrganizationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(COrganizationDTO.class);
        COrganizationDTO cOrganizationDTO1 = new COrganizationDTO();
        cOrganizationDTO1.setId(1L);
        COrganizationDTO cOrganizationDTO2 = new COrganizationDTO();
        assertThat(cOrganizationDTO1).isNotEqualTo(cOrganizationDTO2);
        cOrganizationDTO2.setId(cOrganizationDTO1.getId());
        assertThat(cOrganizationDTO1).isEqualTo(cOrganizationDTO2);
        cOrganizationDTO2.setId(2L);
        assertThat(cOrganizationDTO1).isNotEqualTo(cOrganizationDTO2);
        cOrganizationDTO1.setId(null);
        assertThat(cOrganizationDTO1).isNotEqualTo(cOrganizationDTO2);
    }
}
