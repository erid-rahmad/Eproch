package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqSubmissionLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqSubmissionLine.class);
        MRfqSubmissionLine mRfqSubmissionLine1 = new MRfqSubmissionLine();
        mRfqSubmissionLine1.setId(1L);
        MRfqSubmissionLine mRfqSubmissionLine2 = new MRfqSubmissionLine();
        mRfqSubmissionLine2.setId(mRfqSubmissionLine1.getId());
        assertThat(mRfqSubmissionLine1).isEqualTo(mRfqSubmissionLine2);
        mRfqSubmissionLine2.setId(2L);
        assertThat(mRfqSubmissionLine1).isNotEqualTo(mRfqSubmissionLine2);
        mRfqSubmissionLine1.setId(null);
        assertThat(mRfqSubmissionLine1).isNotEqualTo(mRfqSubmissionLine2);
    }
}
