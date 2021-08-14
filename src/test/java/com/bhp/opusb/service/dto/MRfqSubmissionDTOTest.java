package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqSubmissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqSubmissionDTO.class);
        MRfqSubmissionDTO mRfqSubmissionDTO1 = new MRfqSubmissionDTO();
        mRfqSubmissionDTO1.setId(1L);
        MRfqSubmissionDTO mRfqSubmissionDTO2 = new MRfqSubmissionDTO();
        assertThat(mRfqSubmissionDTO1).isNotEqualTo(mRfqSubmissionDTO2);
        mRfqSubmissionDTO2.setId(mRfqSubmissionDTO1.getId());
        assertThat(mRfqSubmissionDTO1).isEqualTo(mRfqSubmissionDTO2);
        mRfqSubmissionDTO2.setId(2L);
        assertThat(mRfqSubmissionDTO1).isNotEqualTo(mRfqSubmissionDTO2);
        mRfqSubmissionDTO1.setId(null);
        assertThat(mRfqSubmissionDTO1).isNotEqualTo(mRfqSubmissionDTO2);
    }
}
