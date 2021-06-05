package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBidNegoPriceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBidNegoPriceDTO.class);
        MBidNegoPriceDTO mBidNegoPriceDTO1 = new MBidNegoPriceDTO();
        mBidNegoPriceDTO1.setId(1L);
        MBidNegoPriceDTO mBidNegoPriceDTO2 = new MBidNegoPriceDTO();
        assertThat(mBidNegoPriceDTO1).isNotEqualTo(mBidNegoPriceDTO2);
        mBidNegoPriceDTO2.setId(mBidNegoPriceDTO1.getId());
        assertThat(mBidNegoPriceDTO1).isEqualTo(mBidNegoPriceDTO2);
        mBidNegoPriceDTO2.setId(2L);
        assertThat(mBidNegoPriceDTO1).isNotEqualTo(mBidNegoPriceDTO2);
        mBidNegoPriceDTO1.setId(null);
        assertThat(mBidNegoPriceDTO1).isNotEqualTo(mBidNegoPriceDTO2);
    }
}
