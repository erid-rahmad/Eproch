package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEventTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEvent.class);
        MPrequalificationEvent mPrequalificationEvent1 = new MPrequalificationEvent();
        mPrequalificationEvent1.setId(1L);
        MPrequalificationEvent mPrequalificationEvent2 = new MPrequalificationEvent();
        mPrequalificationEvent2.setId(mPrequalificationEvent1.getId());
        assertThat(mPrequalificationEvent1).isEqualTo(mPrequalificationEvent2);
        mPrequalificationEvent2.setId(2L);
        assertThat(mPrequalificationEvent1).isNotEqualTo(mPrequalificationEvent2);
        mPrequalificationEvent1.setId(null);
        assertThat(mPrequalificationEvent1).isNotEqualTo(mPrequalificationEvent2);
    }
}
