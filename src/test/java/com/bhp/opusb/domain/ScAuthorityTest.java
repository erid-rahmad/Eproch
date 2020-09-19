package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAuthority.class);
        ScAuthority scAuthority1 = new ScAuthority();
        scAuthority1.setId(1L);
        ScAuthority scAuthority2 = new ScAuthority();
        scAuthority2.setId(scAuthority1.getId());
        assertThat(scAuthority1).isEqualTo(scAuthority2);
        scAuthority2.setId(2L);
        assertThat(scAuthority1).isNotEqualTo(scAuthority2);
        scAuthority1.setId(null);
        assertThat(scAuthority1).isNotEqualTo(scAuthority2);
    }
}
