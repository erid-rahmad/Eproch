package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationLineDTO.class);
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO1 = new MVendorConfirmationLineDTO();
        mVendorConfirmationLineDTO1.setId(1L);
        MVendorConfirmationLineDTO mVendorConfirmationLineDTO2 = new MVendorConfirmationLineDTO();
        assertThat(mVendorConfirmationLineDTO1).isNotEqualTo(mVendorConfirmationLineDTO2);
        mVendorConfirmationLineDTO2.setId(mVendorConfirmationLineDTO1.getId());
        assertThat(mVendorConfirmationLineDTO1).isEqualTo(mVendorConfirmationLineDTO2);
        mVendorConfirmationLineDTO2.setId(2L);
        assertThat(mVendorConfirmationLineDTO1).isNotEqualTo(mVendorConfirmationLineDTO2);
        mVendorConfirmationLineDTO1.setId(null);
        assertThat(mVendorConfirmationLineDTO1).isNotEqualTo(mVendorConfirmationLineDTO2);
    }
}
