package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqSubmissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqSubmission.class);
        MRfqSubmission mRfqSubmission1 = new MRfqSubmission();
        mRfqSubmission1.setId(1L);
        MRfqSubmission mRfqSubmission2 = new MRfqSubmission();
        mRfqSubmission2.setId(mRfqSubmission1.getId());
        assertThat(mRfqSubmission1).isEqualTo(mRfqSubmission2);
        mRfqSubmission2.setId(2L);
        assertThat(mRfqSubmission1).isNotEqualTo(mRfqSubmission2);
        mRfqSubmission1.setId(null);
        assertThat(mRfqSubmission1).isNotEqualTo(mRfqSubmission2);
    }
}
