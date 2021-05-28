package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmation.class);
        MVendorConfirmation mVendorConfirmation1 = new MVendorConfirmation();
        mVendorConfirmation1.setId(1L);
        MVendorConfirmation mVendorConfirmation2 = new MVendorConfirmation();
        mVendorConfirmation2.setId(mVendorConfirmation1.getId());
        assertThat(mVendorConfirmation1).isEqualTo(mVendorConfirmation2);
        mVendorConfirmation2.setId(2L);
        assertThat(mVendorConfirmation1).isNotEqualTo(mVendorConfirmation2);
        mVendorConfirmation1.setId(null);
        assertThat(mVendorConfirmation1).isNotEqualTo(mVendorConfirmation2);
    }
}
