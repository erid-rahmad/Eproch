package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBiddingCriteriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBiddingCriteriaDTO.class);
        CBiddingCriteriaDTO cBiddingCriteriaDTO1 = new CBiddingCriteriaDTO();
        cBiddingCriteriaDTO1.setId(1L);
        CBiddingCriteriaDTO cBiddingCriteriaDTO2 = new CBiddingCriteriaDTO();
        assertThat(cBiddingCriteriaDTO1).isNotEqualTo(cBiddingCriteriaDTO2);
        cBiddingCriteriaDTO2.setId(cBiddingCriteriaDTO1.getId());
        assertThat(cBiddingCriteriaDTO1).isEqualTo(cBiddingCriteriaDTO2);
        cBiddingCriteriaDTO2.setId(2L);
        assertThat(cBiddingCriteriaDTO1).isNotEqualTo(cBiddingCriteriaDTO2);
        cBiddingCriteriaDTO1.setId(null);
        assertThat(cBiddingCriteriaDTO1).isNotEqualTo(cBiddingCriteriaDTO2);
    }
}
