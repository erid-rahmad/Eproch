package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractTaskReviewersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractTaskReviewersDTO.class);
        MContractTaskReviewersDTO mContractTaskReviewersDTO1 = new MContractTaskReviewersDTO();
        mContractTaskReviewersDTO1.setId(1L);
        MContractTaskReviewersDTO mContractTaskReviewersDTO2 = new MContractTaskReviewersDTO();
        assertThat(mContractTaskReviewersDTO1).isNotEqualTo(mContractTaskReviewersDTO2);
        mContractTaskReviewersDTO2.setId(mContractTaskReviewersDTO1.getId());
        assertThat(mContractTaskReviewersDTO1).isEqualTo(mContractTaskReviewersDTO2);
        mContractTaskReviewersDTO2.setId(2L);
        assertThat(mContractTaskReviewersDTO1).isNotEqualTo(mContractTaskReviewersDTO2);
        mContractTaskReviewersDTO1.setId(null);
        assertThat(mContractTaskReviewersDTO1).isNotEqualTo(mContractTaskReviewersDTO2);
    }
}
