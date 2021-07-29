package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTeamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTeamDTO.class);
        MContractTeamDTO mContractTeamDTO1 = new MContractTeamDTO();
        mContractTeamDTO1.setId(1L);
        MContractTeamDTO mContractTeamDTO2 = new MContractTeamDTO();
        assertThat(mContractTeamDTO1).isNotEqualTo(mContractTeamDTO2);
        mContractTeamDTO2.setId(mContractTeamDTO1.getId());
        assertThat(mContractTeamDTO1).isEqualTo(mContractTeamDTO2);
        mContractTeamDTO2.setId(2L);
        assertThat(mContractTeamDTO1).isNotEqualTo(mContractTeamDTO2);
        mContractTeamDTO1.setId(null);
        assertThat(mContractTeamDTO1).isNotEqualTo(mContractTeamDTO2);
    }
}
