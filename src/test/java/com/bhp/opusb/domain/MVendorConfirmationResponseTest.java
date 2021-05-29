package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationResponseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationResponse.class);
        MVendorConfirmationResponse mVendorConfirmationResponse1 = new MVendorConfirmationResponse();
        mVendorConfirmationResponse1.setId(1L);
        MVendorConfirmationResponse mVendorConfirmationResponse2 = new MVendorConfirmationResponse();
        mVendorConfirmationResponse2.setId(mVendorConfirmationResponse1.getId());
        assertThat(mVendorConfirmationResponse1).isEqualTo(mVendorConfirmationResponse2);
        mVendorConfirmationResponse2.setId(2L);
        assertThat(mVendorConfirmationResponse1).isNotEqualTo(mVendorConfirmationResponse2);
        mVendorConfirmationResponse1.setId(null);
        assertThat(mVendorConfirmationResponse1).isNotEqualTo(mVendorConfirmationResponse2);
    }
}
