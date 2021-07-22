package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEvalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEvalDTO.class);
        MPrequalificationEvalDTO mPrequalificationEvalDTO1 = new MPrequalificationEvalDTO();
        mPrequalificationEvalDTO1.setId(1L);
        MPrequalificationEvalDTO mPrequalificationEvalDTO2 = new MPrequalificationEvalDTO();
        assertThat(mPrequalificationEvalDTO1).isNotEqualTo(mPrequalificationEvalDTO2);
        mPrequalificationEvalDTO2.setId(mPrequalificationEvalDTO1.getId());
        assertThat(mPrequalificationEvalDTO1).isEqualTo(mPrequalificationEvalDTO2);
        mPrequalificationEvalDTO2.setId(2L);
        assertThat(mPrequalificationEvalDTO1).isNotEqualTo(mPrequalificationEvalDTO2);
        mPrequalificationEvalDTO1.setId(null);
        assertThat(mPrequalificationEvalDTO1).isNotEqualTo(mPrequalificationEvalDTO2);
    }
}
