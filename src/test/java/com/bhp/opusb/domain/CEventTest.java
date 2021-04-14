package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEvent.class);
        CEvent cEvent1 = new CEvent();
        cEvent1.setId(1L);
        CEvent cEvent2 = new CEvent();
        cEvent2.setId(cEvent1.getId());
        assertThat(cEvent1).isEqualTo(cEvent2);
        cEvent2.setId(2L);
        assertThat(cEvent1).isNotEqualTo(cEvent2);
        cEvent1.setId(null);
        assertThat(cEvent1).isNotEqualTo(cEvent2);
    }
}
