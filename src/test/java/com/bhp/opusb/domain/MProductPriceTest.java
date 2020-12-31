package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProductPriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProductPrice.class);
        MProductPrice mProductPrice1 = new MProductPrice();
        mProductPrice1.setId(1L);
        MProductPrice mProductPrice2 = new MProductPrice();
        mProductPrice2.setId(mProductPrice1.getId());
        assertThat(mProductPrice1).isEqualTo(mProductPrice2);
        mProductPrice2.setId(2L);
        assertThat(mProductPrice1).isNotEqualTo(mProductPrice2);
        mProductPrice1.setId(null);
        assertThat(mProductPrice1).isNotEqualTo(mProductPrice2);
    }
}
