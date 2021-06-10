package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAuctionPrerequisiteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAuctionPrerequisiteDTO.class);
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO1 = new CAuctionPrerequisiteDTO();
        cAuctionPrerequisiteDTO1.setId(1L);
        CAuctionPrerequisiteDTO cAuctionPrerequisiteDTO2 = new CAuctionPrerequisiteDTO();
        assertThat(cAuctionPrerequisiteDTO1).isNotEqualTo(cAuctionPrerequisiteDTO2);
        cAuctionPrerequisiteDTO2.setId(cAuctionPrerequisiteDTO1.getId());
        assertThat(cAuctionPrerequisiteDTO1).isEqualTo(cAuctionPrerequisiteDTO2);
        cAuctionPrerequisiteDTO2.setId(2L);
        assertThat(cAuctionPrerequisiteDTO1).isNotEqualTo(cAuctionPrerequisiteDTO2);
        cAuctionPrerequisiteDTO1.setId(null);
        assertThat(cAuctionPrerequisiteDTO1).isNotEqualTo(cAuctionPrerequisiteDTO2);
    }
}
