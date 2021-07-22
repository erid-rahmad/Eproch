package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationSubmissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationSubmission.class);
        MPrequalificationSubmission mPrequalificationSubmission1 = new MPrequalificationSubmission();
        mPrequalificationSubmission1.setId(1L);
        MPrequalificationSubmission mPrequalificationSubmission2 = new MPrequalificationSubmission();
        mPrequalificationSubmission2.setId(mPrequalificationSubmission1.getId());
        assertThat(mPrequalificationSubmission1).isEqualTo(mPrequalificationSubmission2);
        mPrequalificationSubmission2.setId(2L);
        assertThat(mPrequalificationSubmission1).isNotEqualTo(mPrequalificationSubmission2);
        mPrequalificationSubmission1.setId(null);
        assertThat(mPrequalificationSubmission1).isNotEqualTo(mPrequalificationSubmission2);
    }
}
