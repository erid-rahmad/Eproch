package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MShoppingCartTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MShoppingCart.class);
        MShoppingCart mShoppingCart1 = new MShoppingCart();
        mShoppingCart1.setId(1L);
        MShoppingCart mShoppingCart2 = new MShoppingCart();
        mShoppingCart2.setId(mShoppingCart1.getId());
        assertThat(mShoppingCart1).isEqualTo(mShoppingCart2);
        mShoppingCart2.setId(2L);
        assertThat(mShoppingCart1).isNotEqualTo(mShoppingCart2);
        mShoppingCart1.setId(null);
        assertThat(mShoppingCart1).isNotEqualTo(mShoppingCart2);
    }
}
