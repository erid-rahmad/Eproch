package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class AdUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdUser.class);
        AdUser adUser1 = new AdUser();
        adUser1.setId(1L);
        AdUser adUser2 = new AdUser();
        adUser2.setId(adUser1.getId());
        assertThat(adUser1).isEqualTo(adUser2);
        adUser2.setId(2L);
        assertThat(adUser1).isNotEqualTo(adUser2);
        adUser1.setId(null);
        assertThat(adUser1).isNotEqualTo(adUser2);
    }
}
