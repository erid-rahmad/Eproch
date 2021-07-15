package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEventDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEventDTO.class);
        MPrequalificationEventDTO mPrequalificationEventDTO1 = new MPrequalificationEventDTO();
        mPrequalificationEventDTO1.setId(1L);
        MPrequalificationEventDTO mPrequalificationEventDTO2 = new MPrequalificationEventDTO();
        assertThat(mPrequalificationEventDTO1).isNotEqualTo(mPrequalificationEventDTO2);
        mPrequalificationEventDTO2.setId(mPrequalificationEventDTO1.getId());
        assertThat(mPrequalificationEventDTO1).isEqualTo(mPrequalificationEventDTO2);
        mPrequalificationEventDTO2.setId(2L);
        assertThat(mPrequalificationEventDTO1).isNotEqualTo(mPrequalificationEventDTO2);
        mPrequalificationEventDTO1.setId(null);
        assertThat(mPrequalificationEventDTO1).isNotEqualTo(mPrequalificationEventDTO2);
    }
}
