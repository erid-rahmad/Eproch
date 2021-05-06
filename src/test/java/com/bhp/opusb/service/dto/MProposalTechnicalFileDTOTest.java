package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalTechnicalFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalTechnicalFileDTO.class);
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO1 = new MProposalTechnicalFileDTO();
        mProposalTechnicalFileDTO1.setId(1L);
        MProposalTechnicalFileDTO mProposalTechnicalFileDTO2 = new MProposalTechnicalFileDTO();
        assertThat(mProposalTechnicalFileDTO1).isNotEqualTo(mProposalTechnicalFileDTO2);
        mProposalTechnicalFileDTO2.setId(mProposalTechnicalFileDTO1.getId());
        assertThat(mProposalTechnicalFileDTO1).isEqualTo(mProposalTechnicalFileDTO2);
        mProposalTechnicalFileDTO2.setId(2L);
        assertThat(mProposalTechnicalFileDTO1).isNotEqualTo(mProposalTechnicalFileDTO2);
        mProposalTechnicalFileDTO1.setId(null);
        assertThat(mProposalTechnicalFileDTO1).isNotEqualTo(mProposalTechnicalFileDTO2);
    }
}
