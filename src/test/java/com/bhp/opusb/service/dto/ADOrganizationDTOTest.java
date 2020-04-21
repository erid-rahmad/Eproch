package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADOrganizationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADOrganizationDTO.class);
        ADOrganizationDTO aDOrganizationDTO1 = new ADOrganizationDTO();
        aDOrganizationDTO1.setId(1L);
        ADOrganizationDTO aDOrganizationDTO2 = new ADOrganizationDTO();
        assertThat(aDOrganizationDTO1).isNotEqualTo(aDOrganizationDTO2);
        aDOrganizationDTO2.setId(aDOrganizationDTO1.getId());
        assertThat(aDOrganizationDTO1).isEqualTo(aDOrganizationDTO2);
        aDOrganizationDTO2.setId(2L);
        assertThat(aDOrganizationDTO1).isNotEqualTo(aDOrganizationDTO2);
        aDOrganizationDTO1.setId(null);
        assertThat(aDOrganizationDTO1).isNotEqualTo(aDOrganizationDTO2);
    }
}
