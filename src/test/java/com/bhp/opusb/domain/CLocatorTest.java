package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CLocatorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLocator.class);
        CLocator cLocator1 = new CLocator();
        cLocator1.setId(1L);
        CLocator cLocator2 = new CLocator();
        cLocator2.setId(cLocator1.getId());
        assertThat(cLocator1).isEqualTo(cLocator2);
        cLocator2.setId(2L);
        assertThat(cLocator1).isNotEqualTo(cLocator2);
        cLocator1.setId(null);
        assertThat(cLocator1).isNotEqualTo(cLocator2);
    }
}
