package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPaymentScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPaymentSchedule.class);
        CPaymentSchedule cPaymentSchedule1 = new CPaymentSchedule();
        cPaymentSchedule1.setId(1L);
        CPaymentSchedule cPaymentSchedule2 = new CPaymentSchedule();
        cPaymentSchedule2.setId(cPaymentSchedule1.getId());
        assertThat(cPaymentSchedule1).isEqualTo(cPaymentSchedule2);
        cPaymentSchedule2.setId(2L);
        assertThat(cPaymentSchedule1).isNotEqualTo(cPaymentSchedule2);
        cPaymentSchedule1.setId(null);
        assertThat(cPaymentSchedule1).isNotEqualTo(cPaymentSchedule2);
    }
}
