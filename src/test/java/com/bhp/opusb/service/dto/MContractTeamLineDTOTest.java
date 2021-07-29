package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTeamLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTeamLineDTO.class);
        MContractTeamLineDTO mContractTeamLineDTO1 = new MContractTeamLineDTO();
        mContractTeamLineDTO1.setId(1L);
        MContractTeamLineDTO mContractTeamLineDTO2 = new MContractTeamLineDTO();
        assertThat(mContractTeamLineDTO1).isNotEqualTo(mContractTeamLineDTO2);
        mContractTeamLineDTO2.setId(mContractTeamLineDTO1.getId());
        assertThat(mContractTeamLineDTO1).isEqualTo(mContractTeamLineDTO2);
        mContractTeamLineDTO2.setId(2L);
        assertThat(mContractTeamLineDTO1).isNotEqualTo(mContractTeamLineDTO2);
        mContractTeamLineDTO1.setId(null);
        assertThat(mContractTeamLineDTO1).isNotEqualTo(mContractTeamLineDTO2);
    }
}
