package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractLine.class);
        MContractLine mContractLine1 = new MContractLine();
        mContractLine1.setId(1L);
        MContractLine mContractLine2 = new MContractLine();
        mContractLine2.setId(mContractLine1.getId());
        assertThat(mContractLine1).isEqualTo(mContractLine2);
        mContractLine2.setId(2L);
        assertThat(mContractLine1).isNotEqualTo(mContractLine2);
        mContractLine1.setId(null);
        assertThat(mContractLine1).isNotEqualTo(mContractLine2);
    }
}
