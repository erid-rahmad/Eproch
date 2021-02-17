package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProjectInformationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProjectInformationDTO.class);
        MProjectInformationDTO mProjectInformationDTO1 = new MProjectInformationDTO();
        mProjectInformationDTO1.setId(1L);
        MProjectInformationDTO mProjectInformationDTO2 = new MProjectInformationDTO();
        assertThat(mProjectInformationDTO1).isNotEqualTo(mProjectInformationDTO2);
        mProjectInformationDTO2.setId(mProjectInformationDTO1.getId());
        assertThat(mProjectInformationDTO1).isEqualTo(mProjectInformationDTO2);
        mProjectInformationDTO2.setId(2L);
        assertThat(mProjectInformationDTO1).isNotEqualTo(mProjectInformationDTO2);
        mProjectInformationDTO1.setId(null);
        assertThat(mProjectInformationDTO1).isNotEqualTo(mProjectInformationDTO2);
    }
}
