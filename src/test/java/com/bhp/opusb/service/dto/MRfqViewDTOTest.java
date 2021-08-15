package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRfqViewDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRfqViewDTO.class);
        MRfqViewDTO mRfqViewDTO1 = new MRfqViewDTO();
        mRfqViewDTO1.setId(1L);
        MRfqViewDTO mRfqViewDTO2 = new MRfqViewDTO();
        assertThat(mRfqViewDTO1).isNotEqualTo(mRfqViewDTO2);
        mRfqViewDTO2.setId(mRfqViewDTO1.getId());
        assertThat(mRfqViewDTO1).isEqualTo(mRfqViewDTO2);
        mRfqViewDTO2.setId(2L);
        assertThat(mRfqViewDTO1).isNotEqualTo(mRfqViewDTO2);
        mRfqViewDTO1.setId(null);
        assertThat(mRfqViewDTO1).isNotEqualTo(mRfqViewDTO2);
    }
}
