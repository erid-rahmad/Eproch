package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalTechnicalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalTechnical.class);
        MProposalTechnical mProposalTechnical1 = new MProposalTechnical();
        mProposalTechnical1.setId(1L);
        MProposalTechnical mProposalTechnical2 = new MProposalTechnical();
        mProposalTechnical2.setId(mProposalTechnical1.getId());
        assertThat(mProposalTechnical1).isEqualTo(mProposalTechnical2);
        mProposalTechnical2.setId(2L);
        assertThat(mProposalTechnical1).isNotEqualTo(mProposalTechnical2);
        mProposalTechnical1.setId(null);
        assertThat(mProposalTechnical1).isNotEqualTo(mProposalTechnical2);
    }
}
