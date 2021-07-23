package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTeamLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTeamLine.class);
        MContractTeamLine mContractTeamLine1 = new MContractTeamLine();
        mContractTeamLine1.setId(1L);
        MContractTeamLine mContractTeamLine2 = new MContractTeamLine();
        mContractTeamLine2.setId(mContractTeamLine1.getId());
        assertThat(mContractTeamLine1).isEqualTo(mContractTeamLine2);
        mContractTeamLine2.setId(2L);
        assertThat(mContractTeamLine1).isNotEqualTo(mContractTeamLine2);
        mContractTeamLine1.setId(null);
        assertThat(mContractTeamLine1).isNotEqualTo(mContractTeamLine2);
    }
}
