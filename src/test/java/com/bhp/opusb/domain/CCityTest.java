package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCity.class);
        CCity cCity1 = new CCity();
        cCity1.setId(1L);
        CCity cCity2 = new CCity();
        cCity2.setId(cCity1.getId());
        assertThat(cCity1).isEqualTo(cCity2);
        cCity2.setId(2L);
        assertThat(cCity1).isNotEqualTo(cCity2);
        cCity1.setId(null);
        assertThat(cCity1).isNotEqualTo(cCity2);
    }
}
