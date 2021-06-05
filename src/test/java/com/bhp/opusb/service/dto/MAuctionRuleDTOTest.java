package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionRuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionRuleDTO.class);
        MAuctionRuleDTO mAuctionRuleDTO1 = new MAuctionRuleDTO();
        mAuctionRuleDTO1.setId(1L);
        MAuctionRuleDTO mAuctionRuleDTO2 = new MAuctionRuleDTO();
        assertThat(mAuctionRuleDTO1).isNotEqualTo(mAuctionRuleDTO2);
        mAuctionRuleDTO2.setId(mAuctionRuleDTO1.getId());
        assertThat(mAuctionRuleDTO1).isEqualTo(mAuctionRuleDTO2);
        mAuctionRuleDTO2.setId(2L);
        assertThat(mAuctionRuleDTO1).isNotEqualTo(mAuctionRuleDTO2);
        mAuctionRuleDTO1.setId(null);
        assertThat(mAuctionRuleDTO1).isNotEqualTo(mAuctionRuleDTO2);
    }
}
