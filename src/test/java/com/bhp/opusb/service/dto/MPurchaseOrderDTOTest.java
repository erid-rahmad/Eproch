package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPurchaseOrderDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPurchaseOrderDTO.class);
        MPurchaseOrderDTO mPurchaseOrderDTO1 = new MPurchaseOrderDTO();
        mPurchaseOrderDTO1.setId(1L);
        MPurchaseOrderDTO mPurchaseOrderDTO2 = new MPurchaseOrderDTO();
        assertThat(mPurchaseOrderDTO1).isNotEqualTo(mPurchaseOrderDTO2);
        mPurchaseOrderDTO2.setId(mPurchaseOrderDTO1.getId());
        assertThat(mPurchaseOrderDTO1).isEqualTo(mPurchaseOrderDTO2);
        mPurchaseOrderDTO2.setId(2L);
        assertThat(mPurchaseOrderDTO1).isNotEqualTo(mPurchaseOrderDTO2);
        mPurchaseOrderDTO1.setId(null);
        assertThat(mPurchaseOrderDTO1).isNotEqualTo(mPurchaseOrderDTO2);
    }
}
