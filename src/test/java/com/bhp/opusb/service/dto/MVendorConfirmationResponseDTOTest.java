package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationResponseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationResponseDTO.class);
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO1 = new MVendorConfirmationResponseDTO();
        mVendorConfirmationResponseDTO1.setId(1L);
        MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO2 = new MVendorConfirmationResponseDTO();
        assertThat(mVendorConfirmationResponseDTO1).isNotEqualTo(mVendorConfirmationResponseDTO2);
        mVendorConfirmationResponseDTO2.setId(mVendorConfirmationResponseDTO1.getId());
        assertThat(mVendorConfirmationResponseDTO1).isEqualTo(mVendorConfirmationResponseDTO2);
        mVendorConfirmationResponseDTO2.setId(2L);
        assertThat(mVendorConfirmationResponseDTO1).isNotEqualTo(mVendorConfirmationResponseDTO2);
        mVendorConfirmationResponseDTO1.setId(null);
        assertThat(mVendorConfirmationResponseDTO1).isNotEqualTo(mVendorConfirmationResponseDTO2);
    }
}
