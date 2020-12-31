package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MShoppingCartItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MShoppingCartItemDTO.class);
        MShoppingCartItemDTO mShoppingCartItemDTO1 = new MShoppingCartItemDTO();
        mShoppingCartItemDTO1.setId(1L);
        MShoppingCartItemDTO mShoppingCartItemDTO2 = new MShoppingCartItemDTO();
        assertThat(mShoppingCartItemDTO1).isNotEqualTo(mShoppingCartItemDTO2);
        mShoppingCartItemDTO2.setId(mShoppingCartItemDTO1.getId());
        assertThat(mShoppingCartItemDTO1).isEqualTo(mShoppingCartItemDTO2);
        mShoppingCartItemDTO2.setId(2L);
        assertThat(mShoppingCartItemDTO1).isNotEqualTo(mShoppingCartItemDTO2);
        mShoppingCartItemDTO1.setId(null);
        assertThat(mShoppingCartItemDTO1).isNotEqualTo(mShoppingCartItemDTO2);
    }
}
