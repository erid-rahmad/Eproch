package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationContractDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationContractDTO.class);
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO1 = new MVendorConfirmationContractDTO();
        mVendorConfirmationContractDTO1.setId(1L);
        MVendorConfirmationContractDTO mVendorConfirmationContractDTO2 = new MVendorConfirmationContractDTO();
        assertThat(mVendorConfirmationContractDTO1).isNotEqualTo(mVendorConfirmationContractDTO2);
        mVendorConfirmationContractDTO2.setId(mVendorConfirmationContractDTO1.getId());
        assertThat(mVendorConfirmationContractDTO1).isEqualTo(mVendorConfirmationContractDTO2);
        mVendorConfirmationContractDTO2.setId(2L);
        assertThat(mVendorConfirmationContractDTO1).isNotEqualTo(mVendorConfirmationContractDTO2);
        mVendorConfirmationContractDTO1.setId(null);
        assertThat(mVendorConfirmationContractDTO1).isNotEqualTo(mVendorConfirmationContractDTO2);
    }
}
