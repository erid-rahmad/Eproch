package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPurchaseOrderLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPurchaseOrderLine.class);
        MPurchaseOrderLine mPurchaseOrderLine1 = new MPurchaseOrderLine();
        mPurchaseOrderLine1.setId(1L);
        MPurchaseOrderLine mPurchaseOrderLine2 = new MPurchaseOrderLine();
        mPurchaseOrderLine2.setId(mPurchaseOrderLine1.getId());
        assertThat(mPurchaseOrderLine1).isEqualTo(mPurchaseOrderLine2);
        mPurchaseOrderLine2.setId(2L);
        assertThat(mPurchaseOrderLine1).isNotEqualTo(mPurchaseOrderLine2);
        mPurchaseOrderLine1.setId(null);
        assertThat(mPurchaseOrderLine1).isNotEqualTo(mPurchaseOrderLine2);
    }
}
