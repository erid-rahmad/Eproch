package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPurchaseOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPurchaseOrder.class);
        MPurchaseOrder mPurchaseOrder1 = new MPurchaseOrder();
        mPurchaseOrder1.setId(1L);
        MPurchaseOrder mPurchaseOrder2 = new MPurchaseOrder();
        mPurchaseOrder2.setId(mPurchaseOrder1.getId());
        assertThat(mPurchaseOrder1).isEqualTo(mPurchaseOrder2);
        mPurchaseOrder2.setId(2L);
        assertThat(mPurchaseOrder1).isNotEqualTo(mPurchaseOrder2);
        mPurchaseOrder1.setId(null);
        assertThat(mPurchaseOrder1).isNotEqualTo(mPurchaseOrder2);
    }
}
