package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalAdministrationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalAdministrationDTO.class);
        MProposalAdministrationDTO mProposalAdministrationDTO1 = new MProposalAdministrationDTO();
        mProposalAdministrationDTO1.setId(1L);
        MProposalAdministrationDTO mProposalAdministrationDTO2 = new MProposalAdministrationDTO();
        assertThat(mProposalAdministrationDTO1).isNotEqualTo(mProposalAdministrationDTO2);
        mProposalAdministrationDTO2.setId(mProposalAdministrationDTO1.getId());
        assertThat(mProposalAdministrationDTO1).isEqualTo(mProposalAdministrationDTO2);
        mProposalAdministrationDTO2.setId(2L);
        assertThat(mProposalAdministrationDTO1).isNotEqualTo(mProposalAdministrationDTO2);
        mProposalAdministrationDTO1.setId(null);
        assertThat(mProposalAdministrationDTO1).isNotEqualTo(mProposalAdministrationDTO2);
    }
}
