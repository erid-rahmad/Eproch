package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAccessTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAccessType.class);
        ScAccessType scAccessType1 = new ScAccessType();
        scAccessType1.setId(1L);
        ScAccessType scAccessType2 = new ScAccessType();
        scAccessType2.setId(scAccessType1.getId());
        assertThat(scAccessType1).isEqualTo(scAccessType2);
        scAccessType2.setId(2L);
        assertThat(scAccessType1).isNotEqualTo(scAccessType2);
        scAccessType1.setId(null);
        assertThat(scAccessType1).isNotEqualTo(scAccessType2);
    }
}
