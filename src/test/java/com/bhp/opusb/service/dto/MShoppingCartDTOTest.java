package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MShoppingCartDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MShoppingCartDTO.class);
        MShoppingCartDTO mShoppingCartDTO1 = new MShoppingCartDTO();
        mShoppingCartDTO1.setId(1L);
        MShoppingCartDTO mShoppingCartDTO2 = new MShoppingCartDTO();
        assertThat(mShoppingCartDTO1).isNotEqualTo(mShoppingCartDTO2);
        mShoppingCartDTO2.setId(mShoppingCartDTO1.getId());
        assertThat(mShoppingCartDTO1).isEqualTo(mShoppingCartDTO2);
        mShoppingCartDTO2.setId(2L);
        assertThat(mShoppingCartDTO1).isNotEqualTo(mShoppingCartDTO2);
        mShoppingCartDTO1.setId(null);
        assertThat(mShoppingCartDTO1).isNotEqualTo(mShoppingCartDTO2);
    }
}
