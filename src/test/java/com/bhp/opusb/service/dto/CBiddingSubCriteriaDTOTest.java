package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingSubCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingSubCriteriaDTO.class);
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO1 = new CBiddingSubCriteriaDTO();
        cBiddingSubCriteriaDTO1.setId(1L);
        CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO2 = new CBiddingSubCriteriaDTO();
        assertThat(cBiddingSubCriteriaDTO1).isNotEqualTo(cBiddingSubCriteriaDTO2);
        cBiddingSubCriteriaDTO2.setId(cBiddingSubCriteriaDTO1.getId());
        assertThat(cBiddingSubCriteriaDTO1).isEqualTo(cBiddingSubCriteriaDTO2);
        cBiddingSubCriteriaDTO2.setId(2L);
        assertThat(cBiddingSubCriteriaDTO1).isNotEqualTo(cBiddingSubCriteriaDTO2);
        cBiddingSubCriteriaDTO1.setId(null);
        assertThat(cBiddingSubCriteriaDTO1).isNotEqualTo(cBiddingSubCriteriaDTO2);
    }
}
