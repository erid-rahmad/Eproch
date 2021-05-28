package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationContractTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationContract.class);
        MVendorConfirmationContract mVendorConfirmationContract1 = new MVendorConfirmationContract();
        mVendorConfirmationContract1.setId(1L);
        MVendorConfirmationContract mVendorConfirmationContract2 = new MVendorConfirmationContract();
        mVendorConfirmationContract2.setId(mVendorConfirmationContract1.getId());
        assertThat(mVendorConfirmationContract1).isEqualTo(mVendorConfirmationContract2);
        mVendorConfirmationContract2.setId(2L);
        assertThat(mVendorConfirmationContract1).isNotEqualTo(mVendorConfirmationContract2);
        mVendorConfirmationContract1.setId(null);
        assertThat(mVendorConfirmationContract1).isNotEqualTo(mVendorConfirmationContract2);
    }
}
