package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CNonBusinessDayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CNonBusinessDay.class);
        CNonBusinessDay cNonBusinessDay1 = new CNonBusinessDay();
        cNonBusinessDay1.setId(1L);
        CNonBusinessDay cNonBusinessDay2 = new CNonBusinessDay();
        cNonBusinessDay2.setId(cNonBusinessDay1.getId());
        assertThat(cNonBusinessDay1).isEqualTo(cNonBusinessDay2);
        cNonBusinessDay2.setId(2L);
        assertThat(cNonBusinessDay1).isNotEqualTo(cNonBusinessDay2);
        cNonBusinessDay1.setId(null);
        assertThat(cNonBusinessDay1).isNotEqualTo(cNonBusinessDay2);
    }
}
