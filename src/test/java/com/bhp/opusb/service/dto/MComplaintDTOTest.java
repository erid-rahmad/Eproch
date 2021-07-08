package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MComplaintDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MComplaintDTO.class);
        MComplaintDTO mComplaintDTO1 = new MComplaintDTO();
        mComplaintDTO1.setId(1L);
        MComplaintDTO mComplaintDTO2 = new MComplaintDTO();
        assertThat(mComplaintDTO1).isNotEqualTo(mComplaintDTO2);
        mComplaintDTO2.setId(mComplaintDTO1.getId());
        assertThat(mComplaintDTO1).isEqualTo(mComplaintDTO2);
        mComplaintDTO2.setId(2L);
        assertThat(mComplaintDTO1).isNotEqualTo(mComplaintDTO2);
        mComplaintDTO1.setId(null);
        assertThat(mComplaintDTO1).isNotEqualTo(mComplaintDTO2);
    }
}
