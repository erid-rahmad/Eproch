package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationSubmissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationSubmissionDTO.class);
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO1 = new MPrequalificationSubmissionDTO();
        mPrequalificationSubmissionDTO1.setId(1L);
        MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO2 = new MPrequalificationSubmissionDTO();
        assertThat(mPrequalificationSubmissionDTO1).isNotEqualTo(mPrequalificationSubmissionDTO2);
        mPrequalificationSubmissionDTO2.setId(mPrequalificationSubmissionDTO1.getId());
        assertThat(mPrequalificationSubmissionDTO1).isEqualTo(mPrequalificationSubmissionDTO2);
        mPrequalificationSubmissionDTO2.setId(2L);
        assertThat(mPrequalificationSubmissionDTO1).isNotEqualTo(mPrequalificationSubmissionDTO2);
        mPrequalificationSubmissionDTO1.setId(null);
        assertThat(mPrequalificationSubmissionDTO1).isNotEqualTo(mPrequalificationSubmissionDTO2);
    }
}
