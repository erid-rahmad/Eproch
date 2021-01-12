package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRequisitionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRequisition.class);
        MRequisition mRequisition1 = new MRequisition();
        mRequisition1.setId(1L);
        MRequisition mRequisition2 = new MRequisition();
        mRequisition2.setId(mRequisition1.getId());
        assertThat(mRequisition1).isEqualTo(mRequisition2);
        mRequisition2.setId(2L);
        assertThat(mRequisition1).isNotEqualTo(mRequisition2);
        mRequisition1.setId(null);
        assertThat(mRequisition1).isNotEqualTo(mRequisition2);
    }
}
