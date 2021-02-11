package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CEventTypelineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CEventTypeline.class);
        CEventTypeline cEventTypeline1 = new CEventTypeline();
        cEventTypeline1.setId(1L);
        CEventTypeline cEventTypeline2 = new CEventTypeline();
        cEventTypeline2.setId(cEventTypeline1.getId());
        assertThat(cEventTypeline1).isEqualTo(cEventTypeline2);
        cEventTypeline2.setId(2L);
        assertThat(cEventTypeline1).isNotEqualTo(cEventTypeline2);
        cEventTypeline1.setId(null);
        assertThat(cEventTypeline1).isNotEqualTo(cEventTypeline2);
    }
}
