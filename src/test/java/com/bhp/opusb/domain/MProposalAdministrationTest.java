package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalAdministrationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalAdministration.class);
        MProposalAdministration mProposalAdministration1 = new MProposalAdministration();
        mProposalAdministration1.setId(1L);
        MProposalAdministration mProposalAdministration2 = new MProposalAdministration();
        mProposalAdministration2.setId(mProposalAdministration1.getId());
        assertThat(mProposalAdministration1).isEqualTo(mProposalAdministration2);
        mProposalAdministration2.setId(2L);
        assertThat(mProposalAdministration1).isNotEqualTo(mProposalAdministration2);
        mProposalAdministration1.setId(null);
        assertThat(mProposalAdministration1).isNotEqualTo(mProposalAdministration2);
    }
}
