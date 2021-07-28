package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskReviewersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTaskReviewers.class);
        MContractTaskReviewers mContractTaskReviewers1 = new MContractTaskReviewers();
        mContractTaskReviewers1.setId(1L);
        MContractTaskReviewers mContractTaskReviewers2 = new MContractTaskReviewers();
        mContractTaskReviewers2.setId(mContractTaskReviewers1.getId());
        assertThat(mContractTaskReviewers1).isEqualTo(mContractTaskReviewers2);
        mContractTaskReviewers2.setId(2L);
        assertThat(mContractTaskReviewers1).isNotEqualTo(mContractTaskReviewers2);
        mContractTaskReviewers1.setId(null);
        assertThat(mContractTaskReviewers1).isNotEqualTo(mContractTaskReviewers2);
    }
}
