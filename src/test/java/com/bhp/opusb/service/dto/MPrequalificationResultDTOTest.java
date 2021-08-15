package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationResultDTO.class);
        MPrequalificationResultDTO mPrequalificationResultDTO1 = new MPrequalificationResultDTO();
        mPrequalificationResultDTO1.setId(1L);
        MPrequalificationResultDTO mPrequalificationResultDTO2 = new MPrequalificationResultDTO();
        assertThat(mPrequalificationResultDTO1).isNotEqualTo(mPrequalificationResultDTO2);
        mPrequalificationResultDTO2.setId(mPrequalificationResultDTO1.getId());
        assertThat(mPrequalificationResultDTO1).isEqualTo(mPrequalificationResultDTO2);
        mPrequalificationResultDTO2.setId(2L);
        assertThat(mPrequalificationResultDTO1).isNotEqualTo(mPrequalificationResultDTO2);
        mPrequalificationResultDTO1.setId(null);
        assertThat(mPrequalificationResultDTO1).isNotEqualTo(mPrequalificationResultDTO2);
    }
}
