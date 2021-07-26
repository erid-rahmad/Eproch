package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTeamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTeam.class);
        MContractTeam mContractTeam1 = new MContractTeam();
        mContractTeam1.setId(1L);
        MContractTeam mContractTeam2 = new MContractTeam();
        mContractTeam2.setId(mContractTeam1.getId());
        assertThat(mContractTeam1).isEqualTo(mContractTeam2);
        mContractTeam2.setId(2L);
        assertThat(mContractTeam1).isNotEqualTo(mContractTeam2);
        mContractTeam1.setId(null);
        assertThat(mContractTeam1).isNotEqualTo(mContractTeam2);
    }
}
