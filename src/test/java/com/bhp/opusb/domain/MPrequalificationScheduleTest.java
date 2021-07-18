package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationSchedule.class);
        MPrequalificationSchedule mPrequalificationSchedule1 = new MPrequalificationSchedule();
        mPrequalificationSchedule1.setId(1L);
        MPrequalificationSchedule mPrequalificationSchedule2 = new MPrequalificationSchedule();
        mPrequalificationSchedule2.setId(mPrequalificationSchedule1.getId());
        assertThat(mPrequalificationSchedule1).isEqualTo(mPrequalificationSchedule2);
        mPrequalificationSchedule2.setId(2L);
        assertThat(mPrequalificationSchedule1).isNotEqualTo(mPrequalificationSchedule2);
        mPrequalificationSchedule1.setId(null);
        assertThat(mPrequalificationSchedule1).isNotEqualTo(mPrequalificationSchedule2);
    }
}
