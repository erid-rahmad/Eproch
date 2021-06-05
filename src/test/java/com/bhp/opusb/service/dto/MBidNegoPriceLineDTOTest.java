package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBidNegoPriceLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBidNegoPriceLineDTO.class);
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO1 = new MBidNegoPriceLineDTO();
        mBidNegoPriceLineDTO1.setId(1L);
        MBidNegoPriceLineDTO mBidNegoPriceLineDTO2 = new MBidNegoPriceLineDTO();
        assertThat(mBidNegoPriceLineDTO1).isNotEqualTo(mBidNegoPriceLineDTO2);
        mBidNegoPriceLineDTO2.setId(mBidNegoPriceLineDTO1.getId());
        assertThat(mBidNegoPriceLineDTO1).isEqualTo(mBidNegoPriceLineDTO2);
        mBidNegoPriceLineDTO2.setId(2L);
        assertThat(mBidNegoPriceLineDTO1).isNotEqualTo(mBidNegoPriceLineDTO2);
        mBidNegoPriceLineDTO1.setId(null);
        assertThat(mBidNegoPriceLineDTO1).isNotEqualTo(mBidNegoPriceLineDTO2);
    }
}
