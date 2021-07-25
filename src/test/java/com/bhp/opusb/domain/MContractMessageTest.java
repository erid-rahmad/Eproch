package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractMessageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractMessage.class);
        MContractMessage mContractMessage1 = new MContractMessage();
        mContractMessage1.setId(1L);
        MContractMessage mContractMessage2 = new MContractMessage();
        mContractMessage2.setId(mContractMessage1.getId());
        assertThat(mContractMessage1).isEqualTo(mContractMessage2);
        mContractMessage2.setId(2L);
        assertThat(mContractMessage1).isNotEqualTo(mContractMessage2);
        mContractMessage1.setId(null);
        assertThat(mContractMessage1).isNotEqualTo(mContractMessage2);
    }
}
