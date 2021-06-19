package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContract.class);
        MContract mContract1 = new MContract();
        mContract1.setId(1L);
        MContract mContract2 = new MContract();
        mContract2.setId(mContract1.getId());
        assertThat(mContract1).isEqualTo(mContract2);
        mContract2.setId(2L);
        assertThat(mContract1).isNotEqualTo(mContract2);
        mContract1.setId(null);
        assertThat(mContract1).isNotEqualTo(mContract2);
    }
}
