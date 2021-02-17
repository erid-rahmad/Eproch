package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEventType.class);
        CEventType cEventType1 = new CEventType();
        cEventType1.setId(1L);
        CEventType cEventType2 = new CEventType();
        cEventType2.setId(cEventType1.getId());
        assertThat(cEventType1).isEqualTo(cEventType2);
        cEventType2.setId(2L);
        assertThat(cEventType1).isNotEqualTo(cEventType2);
        cEventType1.setId(null);
        assertThat(cEventType1).isNotEqualTo(cEventType2);
    }
}
