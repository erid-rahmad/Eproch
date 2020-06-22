package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCurrencyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCurrency.class);
        CCurrency cCurrency1 = new CCurrency();
        cCurrency1.setId(1L);
        CCurrency cCurrency2 = new CCurrency();
        cCurrency2.setId(cCurrency1.getId());
        assertThat(cCurrency1).isEqualTo(cCurrency2);
        cCurrency2.setId(2L);
        assertThat(cCurrency1).isNotEqualTo(cCurrency2);
        cCurrency1.setId(null);
        assertThat(cCurrency1).isNotEqualTo(cCurrency2);
    }
}
