package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MDocumentScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MDocumentSchedule.class);
        MDocumentSchedule mDocumentSchedule1 = new MDocumentSchedule();
        mDocumentSchedule1.setId(1L);
        MDocumentSchedule mDocumentSchedule2 = new MDocumentSchedule();
        mDocumentSchedule2.setId(mDocumentSchedule1.getId());
        assertThat(mDocumentSchedule1).isEqualTo(mDocumentSchedule2);
        mDocumentSchedule2.setId(2L);
        assertThat(mDocumentSchedule1).isNotEqualTo(mDocumentSchedule2);
        mDocumentSchedule1.setId(null);
        assertThat(mDocumentSchedule1).isNotEqualTo(mDocumentSchedule2);
    }
}
