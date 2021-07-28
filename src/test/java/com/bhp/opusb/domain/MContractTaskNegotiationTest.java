package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskNegotiationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTaskNegotiation.class);
        MContractTaskNegotiation mContractTaskNegotiation1 = new MContractTaskNegotiation();
        mContractTaskNegotiation1.setId(1L);
        MContractTaskNegotiation mContractTaskNegotiation2 = new MContractTaskNegotiation();
        mContractTaskNegotiation2.setId(mContractTaskNegotiation1.getId());
        assertThat(mContractTaskNegotiation1).isEqualTo(mContractTaskNegotiation2);
        mContractTaskNegotiation2.setId(2L);
        assertThat(mContractTaskNegotiation1).isNotEqualTo(mContractTaskNegotiation2);
        mContractTaskNegotiation1.setId(null);
        assertThat(mContractTaskNegotiation1).isNotEqualTo(mContractTaskNegotiation2);
    }
}
