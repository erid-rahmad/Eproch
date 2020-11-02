package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MMatchPOTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMatchPO.class);
        MMatchPO mMatchPO1 = new MMatchPO();
        mMatchPO1.setId(1L);
        MMatchPO mMatchPO2 = new MMatchPO();
        mMatchPO2.setId(mMatchPO1.getId());
        assertThat(mMatchPO1).isEqualTo(mMatchPO2);
        mMatchPO2.setId(2L);
        assertThat(mMatchPO1).isNotEqualTo(mMatchPO2);
        mMatchPO1.setId(null);
        assertThat(mMatchPO1).isNotEqualTo(mMatchPO2);
    }
}
