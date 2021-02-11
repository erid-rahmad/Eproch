package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingTypeDTO.class);
        CBiddingTypeDTO cBiddingTypeDTO1 = new CBiddingTypeDTO();
        cBiddingTypeDTO1.setId(1L);
        CBiddingTypeDTO cBiddingTypeDTO2 = new CBiddingTypeDTO();
        assertThat(cBiddingTypeDTO1).isNotEqualTo(cBiddingTypeDTO2);
        cBiddingTypeDTO2.setId(cBiddingTypeDTO1.getId());
        assertThat(cBiddingTypeDTO1).isEqualTo(cBiddingTypeDTO2);
        cBiddingTypeDTO2.setId(2L);
        assertThat(cBiddingTypeDTO1).isNotEqualTo(cBiddingTypeDTO2);
        cBiddingTypeDTO1.setId(null);
        assertThat(cBiddingTypeDTO1).isNotEqualTo(cBiddingTypeDTO2);
    }
}
