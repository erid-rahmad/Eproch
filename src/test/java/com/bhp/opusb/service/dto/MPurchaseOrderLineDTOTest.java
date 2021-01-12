package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPurchaseOrderLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPurchaseOrderLineDTO.class);
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO1 = new MPurchaseOrderLineDTO();
        mPurchaseOrderLineDTO1.setId(1L);
        MPurchaseOrderLineDTO mPurchaseOrderLineDTO2 = new MPurchaseOrderLineDTO();
        assertThat(mPurchaseOrderLineDTO1).isNotEqualTo(mPurchaseOrderLineDTO2);
        mPurchaseOrderLineDTO2.setId(mPurchaseOrderLineDTO1.getId());
        assertThat(mPurchaseOrderLineDTO1).isEqualTo(mPurchaseOrderLineDTO2);
        mPurchaseOrderLineDTO2.setId(2L);
        assertThat(mPurchaseOrderLineDTO1).isNotEqualTo(mPurchaseOrderLineDTO2);
        mPurchaseOrderLineDTO1.setId(null);
        assertThat(mPurchaseOrderLineDTO1).isNotEqualTo(mPurchaseOrderLineDTO2);
    }
}
