package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingSubCriteriaLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingSubCriteriaLineDTO.class);
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO1 = new CBiddingSubCriteriaLineDTO();
        cBiddingSubCriteriaLineDTO1.setId(1L);
        CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO2 = new CBiddingSubCriteriaLineDTO();
        assertThat(cBiddingSubCriteriaLineDTO1).isNotEqualTo(cBiddingSubCriteriaLineDTO2);
        cBiddingSubCriteriaLineDTO2.setId(cBiddingSubCriteriaLineDTO1.getId());
        assertThat(cBiddingSubCriteriaLineDTO1).isEqualTo(cBiddingSubCriteriaLineDTO2);
        cBiddingSubCriteriaLineDTO2.setId(2L);
        assertThat(cBiddingSubCriteriaLineDTO1).isNotEqualTo(cBiddingSubCriteriaLineDTO2);
        cBiddingSubCriteriaLineDTO1.setId(null);
        assertThat(cBiddingSubCriteriaLineDTO1).isNotEqualTo(cBiddingSubCriteriaLineDTO2);
    }
}
