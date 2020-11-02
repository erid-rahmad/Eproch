package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADOrganizationInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADOrganizationInfoDTO.class);
        ADOrganizationInfoDTO aDOrganizationInfoDTO1 = new ADOrganizationInfoDTO();
        aDOrganizationInfoDTO1.setId(1L);
        ADOrganizationInfoDTO aDOrganizationInfoDTO2 = new ADOrganizationInfoDTO();
        assertThat(aDOrganizationInfoDTO1).isNotEqualTo(aDOrganizationInfoDTO2);
        aDOrganizationInfoDTO2.setId(aDOrganizationInfoDTO1.getId());
        assertThat(aDOrganizationInfoDTO1).isEqualTo(aDOrganizationInfoDTO2);
        aDOrganizationInfoDTO2.setId(2L);
        assertThat(aDOrganizationInfoDTO1).isNotEqualTo(aDOrganizationInfoDTO2);
        aDOrganizationInfoDTO1.setId(null);
        assertThat(aDOrganizationInfoDTO1).isNotEqualTo(aDOrganizationInfoDTO2);
    }
}
