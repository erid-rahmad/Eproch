package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MVendorConfirmationLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MVendorConfirmationLine.class);
        MVendorConfirmationLine mVendorConfirmationLine1 = new MVendorConfirmationLine();
        mVendorConfirmationLine1.setId(1L);
        MVendorConfirmationLine mVendorConfirmationLine2 = new MVendorConfirmationLine();
        mVendorConfirmationLine2.setId(mVendorConfirmationLine1.getId());
        assertThat(mVendorConfirmationLine1).isEqualTo(mVendorConfirmationLine2);
        mVendorConfirmationLine2.setId(2L);
        assertThat(mVendorConfirmationLine1).isNotEqualTo(mVendorConfirmationLine2);
        mVendorConfirmationLine1.setId(null);
        assertThat(mVendorConfirmationLine1).isNotEqualTo(mVendorConfirmationLine2);
    }
}
