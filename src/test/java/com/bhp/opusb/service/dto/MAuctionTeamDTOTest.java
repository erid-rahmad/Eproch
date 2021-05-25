package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MAuctionTeamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MAuctionTeamDTO.class);
        MAuctionTeamDTO mAuctionTeamDTO1 = new MAuctionTeamDTO();
        mAuctionTeamDTO1.setId(1L);
        MAuctionTeamDTO mAuctionTeamDTO2 = new MAuctionTeamDTO();
        assertThat(mAuctionTeamDTO1).isNotEqualTo(mAuctionTeamDTO2);
        mAuctionTeamDTO2.setId(mAuctionTeamDTO1.getId());
        assertThat(mAuctionTeamDTO1).isEqualTo(mAuctionTeamDTO2);
        mAuctionTeamDTO2.setId(2L);
        assertThat(mAuctionTeamDTO1).isNotEqualTo(mAuctionTeamDTO2);
        mAuctionTeamDTO1.setId(null);
        assertThat(mAuctionTeamDTO1).isNotEqualTo(mAuctionTeamDTO2);
    }
}
