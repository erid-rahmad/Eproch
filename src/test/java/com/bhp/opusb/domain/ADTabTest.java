package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADTabTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADTab.class);
        ADTab aDTab1 = new ADTab();
        aDTab1.setId(1L);
        ADTab aDTab2 = new ADTab();
        aDTab2.setId(aDTab1.getId());
        assertThat(aDTab1).isEqualTo(aDTab2);
        aDTab2.setId(2L);
        assertThat(aDTab1).isNotEqualTo(aDTab2);
        aDTab1.setId(null);
        assertThat(aDTab1).isNotEqualTo(aDTab2);
    }
}
