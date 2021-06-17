package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqDTO.class);
        MRfqDTO mRfqDTO1 = new MRfqDTO();
        mRfqDTO1.setId(1L);
        MRfqDTO mRfqDTO2 = new MRfqDTO();
        assertThat(mRfqDTO1).isNotEqualTo(mRfqDTO2);
        mRfqDTO2.setId(mRfqDTO1.getId());
        assertThat(mRfqDTO1).isEqualTo(mRfqDTO2);
        mRfqDTO2.setId(2L);
        assertThat(mRfqDTO1).isNotEqualTo(mRfqDTO2);
        mRfqDTO1.setId(null);
        assertThat(mRfqDTO1).isNotEqualTo(mRfqDTO2);
    }
}
