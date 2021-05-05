package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalAdministrationFileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalAdministrationFile.class);
        MProposalAdministrationFile mProposalAdministrationFile1 = new MProposalAdministrationFile();
        mProposalAdministrationFile1.setId(1L);
        MProposalAdministrationFile mProposalAdministrationFile2 = new MProposalAdministrationFile();
        mProposalAdministrationFile2.setId(mProposalAdministrationFile1.getId());
        assertThat(mProposalAdministrationFile1).isEqualTo(mProposalAdministrationFile2);
        mProposalAdministrationFile2.setId(2L);
        assertThat(mProposalAdministrationFile1).isNotEqualTo(mProposalAdministrationFile2);
        mProposalAdministrationFile1.setId(null);
        assertThat(mProposalAdministrationFile1).isNotEqualTo(mProposalAdministrationFile2);
    }
}
