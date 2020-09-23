package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProduct.class);
        CProduct cProduct1 = new CProduct();
        cProduct1.setId(1L);
        CProduct cProduct2 = new CProduct();
        cProduct2.setId(cProduct1.getId());
        assertThat(cProduct1).isEqualTo(cProduct2);
        cProduct2.setId(2L);
        assertThat(cProduct1).isNotEqualTo(cProduct2);
        cProduct1.setId(null);
        assertThat(cProduct1).isNotEqualTo(cProduct2);
    }
}
