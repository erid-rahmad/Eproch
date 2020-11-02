package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCalendarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCalendar.class);
        CCalendar cCalendar1 = new CCalendar();
        cCalendar1.setId(1L);
        CCalendar cCalendar2 = new CCalendar();
        cCalendar2.setId(cCalendar1.getId());
        assertThat(cCalendar1).isEqualTo(cCalendar2);
        cCalendar2.setId(2L);
        assertThat(cCalendar1).isNotEqualTo(cCalendar2);
        cCalendar1.setId(null);
        assertThat(cCalendar1).isNotEqualTo(cCalendar2);
    }
}
