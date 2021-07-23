package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationEvalFileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationEvalFileDTO.class);
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO1 = new MPrequalificationEvalFileDTO();
        mPrequalificationEvalFileDTO1.setId(1L);
        MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO2 = new MPrequalificationEvalFileDTO();
        assertThat(mPrequalificationEvalFileDTO1).isNotEqualTo(mPrequalificationEvalFileDTO2);
        mPrequalificationEvalFileDTO2.setId(mPrequalificationEvalFileDTO1.getId());
        assertThat(mPrequalificationEvalFileDTO1).isEqualTo(mPrequalificationEvalFileDTO2);
        mPrequalificationEvalFileDTO2.setId(2L);
        assertThat(mPrequalificationEvalFileDTO1).isNotEqualTo(mPrequalificationEvalFileDTO2);
        mPrequalificationEvalFileDTO1.setId(null);
        assertThat(mPrequalificationEvalFileDTO1).isNotEqualTo(mPrequalificationEvalFileDTO2);
    }
}
