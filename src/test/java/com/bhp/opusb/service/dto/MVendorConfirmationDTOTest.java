package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationDTO.class);
        MVendorConfirmationDTO mVendorConfirmationDTO1 = new MVendorConfirmationDTO();
        mVendorConfirmationDTO1.setId(1L);
        MVendorConfirmationDTO mVendorConfirmationDTO2 = new MVendorConfirmationDTO();
        assertThat(mVendorConfirmationDTO1).isNotEqualTo(mVendorConfirmationDTO2);
        mVendorConfirmationDTO2.setId(mVendorConfirmationDTO1.getId());
        assertThat(mVendorConfirmationDTO1).isEqualTo(mVendorConfirmationDTO2);
        mVendorConfirmationDTO2.setId(2L);
        assertThat(mVendorConfirmationDTO1).isNotEqualTo(mVendorConfirmationDTO2);
        mVendorConfirmationDTO1.setId(null);
        assertThat(mVendorConfirmationDTO1).isNotEqualTo(mVendorConfirmationDTO2);
    }
}
