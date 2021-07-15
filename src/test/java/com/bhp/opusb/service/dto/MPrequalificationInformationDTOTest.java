package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationInformationDTO.class);
        MPrequalificationInformationDTO mPrequalificationInformationDTO1 = new MPrequalificationInformationDTO();
        mPrequalificationInformationDTO1.setId(1L);
        MPrequalificationInformationDTO mPrequalificationInformationDTO2 = new MPrequalificationInformationDTO();
        assertThat(mPrequalificationInformationDTO1).isNotEqualTo(mPrequalificationInformationDTO2);
        mPrequalificationInformationDTO2.setId(mPrequalificationInformationDTO1.getId());
        assertThat(mPrequalificationInformationDTO1).isEqualTo(mPrequalificationInformationDTO2);
        mPrequalificationInformationDTO2.setId(2L);
        assertThat(mPrequalificationInformationDTO1).isNotEqualTo(mPrequalificationInformationDTO2);
        mPrequalificationInformationDTO1.setId(null);
        assertThat(mPrequalificationInformationDTO1).isNotEqualTo(mPrequalificationInformationDTO2);
    }
}
