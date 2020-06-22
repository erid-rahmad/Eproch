package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBankTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBank.class);
        CBank cBank1 = new CBank();
        cBank1.setId(1L);
        CBank cBank2 = new CBank();
        cBank2.setId(cBank1.getId());
        assertThat(cBank1).isEqualTo(cBank2);
        cBank2.setId(2L);
        assertThat(cBank1).isNotEqualTo(cBank2);
        cBank1.setId(null);
        assertThat(cBank1).isNotEqualTo(cBank2);
    }
}
