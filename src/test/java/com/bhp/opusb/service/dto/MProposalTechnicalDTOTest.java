package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalTechnicalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalTechnicalDTO.class);
        MProposalTechnicalDTO mProposalTechnicalDTO1 = new MProposalTechnicalDTO();
        mProposalTechnicalDTO1.setId(1L);
        MProposalTechnicalDTO mProposalTechnicalDTO2 = new MProposalTechnicalDTO();
        assertThat(mProposalTechnicalDTO1).isNotEqualTo(mProposalTechnicalDTO2);
        mProposalTechnicalDTO2.setId(mProposalTechnicalDTO1.getId());
        assertThat(mProposalTechnicalDTO1).isEqualTo(mProposalTechnicalDTO2);
        mProposalTechnicalDTO2.setId(2L);
        assertThat(mProposalTechnicalDTO1).isNotEqualTo(mProposalTechnicalDTO2);
        mProposalTechnicalDTO1.setId(null);
        assertThat(mProposalTechnicalDTO1).isNotEqualTo(mProposalTechnicalDTO2);
    }
}
