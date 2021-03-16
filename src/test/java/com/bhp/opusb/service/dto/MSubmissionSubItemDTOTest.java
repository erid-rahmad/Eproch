package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MSubmissionSubItemDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MSubmissionSubItemDTO.class);
        MSubmissionSubItemDTO mSubmissionSubItemDTO1 = new MSubmissionSubItemDTO();
        mSubmissionSubItemDTO1.setId(1L);
        MSubmissionSubItemDTO mSubmissionSubItemDTO2 = new MSubmissionSubItemDTO();
        assertThat(mSubmissionSubItemDTO1).isNotEqualTo(mSubmissionSubItemDTO2);
        mSubmissionSubItemDTO2.setId(mSubmissionSubItemDTO1.getId());
        assertThat(mSubmissionSubItemDTO1).isEqualTo(mSubmissionSubItemDTO2);
        mSubmissionSubItemDTO2.setId(2L);
        assertThat(mSubmissionSubItemDTO1).isNotEqualTo(mSubmissionSubItemDTO2);
        mSubmissionSubItemDTO1.setId(null);
        assertThat(mSubmissionSubItemDTO1).isNotEqualTo(mSubmissionSubItemDTO2);
    }
}
