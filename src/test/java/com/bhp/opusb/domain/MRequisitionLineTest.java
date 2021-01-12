package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRequisitionLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRequisitionLine.class);
        MRequisitionLine mRequisitionLine1 = new MRequisitionLine();
        mRequisitionLine1.setId(1L);
        MRequisitionLine mRequisitionLine2 = new MRequisitionLine();
        mRequisitionLine2.setId(mRequisitionLine1.getId());
        assertThat(mRequisitionLine1).isEqualTo(mRequisitionLine2);
        mRequisitionLine2.setId(2L);
        assertThat(mRequisitionLine1).isNotEqualTo(mRequisitionLine2);
        mRequisitionLine1.setId(null);
        assertThat(mRequisitionLine1).isNotEqualTo(mRequisitionLine2);
    }
}
