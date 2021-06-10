package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CAuctionPrerequisiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CAuctionPrerequisite.class);
        CAuctionPrerequisite cAuctionPrerequisite1 = new CAuctionPrerequisite();
        cAuctionPrerequisite1.setId(1L);
        CAuctionPrerequisite cAuctionPrerequisite2 = new CAuctionPrerequisite();
        cAuctionPrerequisite2.setId(cAuctionPrerequisite1.getId());
        assertThat(cAuctionPrerequisite1).isEqualTo(cAuctionPrerequisite2);
        cAuctionPrerequisite2.setId(2L);
        assertThat(cAuctionPrerequisite1).isNotEqualTo(cAuctionPrerequisite2);
        cAuctionPrerequisite1.setId(null);
        assertThat(cAuctionPrerequisite1).isNotEqualTo(cAuctionPrerequisite2);
    }
}
