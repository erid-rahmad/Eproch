package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTask.class);
        MContractTask mContractTask1 = new MContractTask();
        mContractTask1.setId(1L);
        MContractTask mContractTask2 = new MContractTask();
        mContractTask2.setId(mContractTask1.getId());
        assertThat(mContractTask1).isEqualTo(mContractTask2);
        mContractTask2.setId(2L);
        assertThat(mContractTask1).isNotEqualTo(mContractTask2);
        mContractTask1.setId(null);
        assertThat(mContractTask1).isNotEqualTo(mContractTask2);
    }
}
