package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MSubmissionSubItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MSubmissionSubItem.class);
        MSubmissionSubItem mSubmissionSubItem1 = new MSubmissionSubItem();
        mSubmissionSubItem1.setId(1L);
        MSubmissionSubItem mSubmissionSubItem2 = new MSubmissionSubItem();
        mSubmissionSubItem2.setId(mSubmissionSubItem1.getId());
        assertThat(mSubmissionSubItem1).isEqualTo(mSubmissionSubItem2);
        mSubmissionSubItem2.setId(2L);
        assertThat(mSubmissionSubItem1).isNotEqualTo(mSubmissionSubItem2);
        mSubmissionSubItem1.setId(null);
        assertThat(mSubmissionSubItem1).isNotEqualTo(mSubmissionSubItem2);
    }
}
