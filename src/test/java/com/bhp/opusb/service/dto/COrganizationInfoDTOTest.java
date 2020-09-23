package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class COrganizationInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(COrganizationInfoDTO.class);
        COrganizationInfoDTO cOrganizationInfoDTO1 = new COrganizationInfoDTO();
        cOrganizationInfoDTO1.setId(1L);
        COrganizationInfoDTO cOrganizationInfoDTO2 = new COrganizationInfoDTO();
        assertThat(cOrganizationInfoDTO1).isNotEqualTo(cOrganizationInfoDTO2);
        cOrganizationInfoDTO2.setId(cOrganizationInfoDTO1.getId());
        assertThat(cOrganizationInfoDTO1).isEqualTo(cOrganizationInfoDTO2);
        cOrganizationInfoDTO2.setId(2L);
        assertThat(cOrganizationInfoDTO1).isNotEqualTo(cOrganizationInfoDTO2);
        cOrganizationInfoDTO1.setId(null);
        assertThat(cOrganizationInfoDTO1).isNotEqualTo(cOrganizationInfoDTO2);
    }
}
