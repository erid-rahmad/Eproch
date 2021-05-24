package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MTechProposalEvaluationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MTechProposalEvaluationDTO.class);
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO1 = new MTechProposalEvaluationDTO();
        mTechProposalEvaluationDTO1.setId(1L);
        MTechProposalEvaluationDTO mTechProposalEvaluationDTO2 = new MTechProposalEvaluationDTO();
        assertThat(mTechProposalEvaluationDTO1).isNotEqualTo(mTechProposalEvaluationDTO2);
        mTechProposalEvaluationDTO2.setId(mTechProposalEvaluationDTO1.getId());
        assertThat(mTechProposalEvaluationDTO1).isEqualTo(mTechProposalEvaluationDTO2);
        mTechProposalEvaluationDTO2.setId(2L);
        assertThat(mTechProposalEvaluationDTO1).isNotEqualTo(mTechProposalEvaluationDTO2);
        mTechProposalEvaluationDTO1.setId(null);
        assertThat(mTechProposalEvaluationDTO1).isNotEqualTo(mTechProposalEvaluationDTO2);
    }
}
