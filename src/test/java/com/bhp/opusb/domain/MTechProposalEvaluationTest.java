package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MTechProposalEvaluationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MTechProposalEvaluation.class);
        MTechProposalEvaluation mTechProposalEvaluation1 = new MTechProposalEvaluation();
        mTechProposalEvaluation1.setId(1L);
        MTechProposalEvaluation mTechProposalEvaluation2 = new MTechProposalEvaluation();
        mTechProposalEvaluation2.setId(mTechProposalEvaluation1.getId());
        assertThat(mTechProposalEvaluation1).isEqualTo(mTechProposalEvaluation2);
        mTechProposalEvaluation2.setId(2L);
        assertThat(mTechProposalEvaluation1).isNotEqualTo(mTechProposalEvaluation2);
        mTechProposalEvaluation1.setId(null);
        assertThat(mTechProposalEvaluation1).isNotEqualTo(mTechProposalEvaluation2);
    }
}
