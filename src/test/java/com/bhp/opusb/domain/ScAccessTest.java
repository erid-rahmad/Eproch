package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAccessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAccess.class);
        ScAccess scAccess1 = new ScAccess();
        scAccess1.setId(1L);
        ScAccess scAccess2 = new ScAccess();
        scAccess2.setId(scAccess1.getId());
        assertThat(scAccess1).isEqualTo(scAccess2);
        scAccess2.setId(2L);
        assertThat(scAccess1).isNotEqualTo(scAccess2);
        scAccess1.setId(null);
        assertThat(scAccess1).isNotEqualTo(scAccess2);
    }
}
