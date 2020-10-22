package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdUserAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdUserAuthority.class);
        AdUserAuthority adUserAuthority1 = new AdUserAuthority();
        adUserAuthority1.setId(1L);
        AdUserAuthority adUserAuthority2 = new AdUserAuthority();
        adUserAuthority2.setId(adUserAuthority1.getId());
        assertThat(adUserAuthority1).isEqualTo(adUserAuthority2);
        adUserAuthority2.setId(2L);
        assertThat(adUserAuthority1).isNotEqualTo(adUserAuthority2);
        adUserAuthority1.setId(null);
        assertThat(adUserAuthority1).isNotEqualTo(adUserAuthority2);
    }
}
