package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalTechnicalFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalTechnicalFile.class);
        MProposalTechnicalFile mProposalTechnicalFile1 = new MProposalTechnicalFile();
        mProposalTechnicalFile1.setId(1L);
        MProposalTechnicalFile mProposalTechnicalFile2 = new MProposalTechnicalFile();
        mProposalTechnicalFile2.setId(mProposalTechnicalFile1.getId());
        assertThat(mProposalTechnicalFile1).isEqualTo(mProposalTechnicalFile2);
        mProposalTechnicalFile2.setId(2L);
        assertThat(mProposalTechnicalFile1).isNotEqualTo(mProposalTechnicalFile2);
        mProposalTechnicalFile1.setId(null);
        assertThat(mProposalTechnicalFile1).isNotEqualTo(mProposalTechnicalFile2);
    }
}
