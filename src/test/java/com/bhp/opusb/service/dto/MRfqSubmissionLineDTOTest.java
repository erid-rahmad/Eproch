package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqSubmissionLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqSubmissionLineDTO.class);
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO1 = new MRfqSubmissionLineDTO();
        mRfqSubmissionLineDTO1.setId(1L);
        MRfqSubmissionLineDTO mRfqSubmissionLineDTO2 = new MRfqSubmissionLineDTO();
        assertThat(mRfqSubmissionLineDTO1).isNotEqualTo(mRfqSubmissionLineDTO2);
        mRfqSubmissionLineDTO2.setId(mRfqSubmissionLineDTO1.getId());
        assertThat(mRfqSubmissionLineDTO1).isEqualTo(mRfqSubmissionLineDTO2);
        mRfqSubmissionLineDTO2.setId(2L);
        assertThat(mRfqSubmissionLineDTO1).isNotEqualTo(mRfqSubmissionLineDTO2);
        mRfqSubmissionLineDTO1.setId(null);
        assertThat(mRfqSubmissionLineDTO1).isNotEqualTo(mRfqSubmissionLineDTO2);
    }
}
