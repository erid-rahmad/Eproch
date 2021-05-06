package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MProposalPriceSubItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MProposalPriceSubItemDTO.class);
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO1 = new MProposalPriceSubItemDTO();
        mProposalPriceSubItemDTO1.setId(1L);
        MProposalPriceSubItemDTO mProposalPriceSubItemDTO2 = new MProposalPriceSubItemDTO();
        assertThat(mProposalPriceSubItemDTO1).isNotEqualTo(mProposalPriceSubItemDTO2);
        mProposalPriceSubItemDTO2.setId(mProposalPriceSubItemDTO1.getId());
        assertThat(mProposalPriceSubItemDTO1).isEqualTo(mProposalPriceSubItemDTO2);
        mProposalPriceSubItemDTO2.setId(2L);
        assertThat(mProposalPriceSubItemDTO1).isNotEqualTo(mProposalPriceSubItemDTO2);
        mProposalPriceSubItemDTO1.setId(null);
        assertThat(mProposalPriceSubItemDTO1).isNotEqualTo(mProposalPriceSubItemDTO2);
    }
}
