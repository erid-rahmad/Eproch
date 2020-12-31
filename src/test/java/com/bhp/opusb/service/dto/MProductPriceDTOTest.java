package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProductPriceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProductPriceDTO.class);
        MProductPriceDTO mProductPriceDTO1 = new MProductPriceDTO();
        mProductPriceDTO1.setId(1L);
        MProductPriceDTO mProductPriceDTO2 = new MProductPriceDTO();
        assertThat(mProductPriceDTO1).isNotEqualTo(mProductPriceDTO2);
        mProductPriceDTO2.setId(mProductPriceDTO1.getId());
        assertThat(mProductPriceDTO1).isEqualTo(mProductPriceDTO2);
        mProductPriceDTO2.setId(2L);
        assertThat(mProductPriceDTO1).isNotEqualTo(mProductPriceDTO2);
        mProductPriceDTO1.setId(null);
        assertThat(mProductPriceDTO1).isNotEqualTo(mProductPriceDTO2);
    }
}
