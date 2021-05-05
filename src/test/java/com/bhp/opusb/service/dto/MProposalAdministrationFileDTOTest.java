package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalAdministrationFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalAdministrationFileDTO.class);
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO1 = new MProposalAdministrationFileDTO();
        mProposalAdministrationFileDTO1.setId(1L);
        MProposalAdministrationFileDTO mProposalAdministrationFileDTO2 = new MProposalAdministrationFileDTO();
        assertThat(mProposalAdministrationFileDTO1).isNotEqualTo(mProposalAdministrationFileDTO2);
        mProposalAdministrationFileDTO2.setId(mProposalAdministrationFileDTO1.getId());
        assertThat(mProposalAdministrationFileDTO1).isEqualTo(mProposalAdministrationFileDTO2);
        mProposalAdministrationFileDTO2.setId(2L);
        assertThat(mProposalAdministrationFileDTO1).isNotEqualTo(mProposalAdministrationFileDTO2);
        mProposalAdministrationFileDTO1.setId(null);
        assertThat(mProposalAdministrationFileDTO1).isNotEqualTo(mProposalAdministrationFileDTO2);
    }
}
