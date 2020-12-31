package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MShoppingCartItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MShoppingCartItem.class);
        MShoppingCartItem mShoppingCartItem1 = new MShoppingCartItem();
        mShoppingCartItem1.setId(1L);
        MShoppingCartItem mShoppingCartItem2 = new MShoppingCartItem();
        mShoppingCartItem2.setId(mShoppingCartItem1.getId());
        assertThat(mShoppingCartItem1).isEqualTo(mShoppingCartItem2);
        mShoppingCartItem2.setId(2L);
        assertThat(mShoppingCartItem1).isNotEqualTo(mShoppingCartItem2);
        mShoppingCartItem1.setId(null);
        assertThat(mShoppingCartItem1).isNotEqualTo(mShoppingCartItem2);
    }
}
