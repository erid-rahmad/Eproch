package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBrandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBrand.class);
        MBrand mBrand1 = new MBrand();
        mBrand1.setId(1L);
        MBrand mBrand2 = new MBrand();
        mBrand2.setId(mBrand1.getId());
        assertThat(mBrand1).isEqualTo(mBrand2);
        mBrand2.setId(2L);
        assertThat(mBrand1).isNotEqualTo(mBrand2);
        mBrand1.setId(null);
        assertThat(mBrand1).isNotEqualTo(mBrand2);
    }
}
