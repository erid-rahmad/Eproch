package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MWarningLetterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MWarningLetterDTO.class);
        MWarningLetterDTO mWarningLetterDTO1 = new MWarningLetterDTO();
        mWarningLetterDTO1.setId(1L);
        MWarningLetterDTO mWarningLetterDTO2 = new MWarningLetterDTO();
        assertThat(mWarningLetterDTO1).isNotEqualTo(mWarningLetterDTO2);
        mWarningLetterDTO2.setId(mWarningLetterDTO1.getId());
        assertThat(mWarningLetterDTO1).isEqualTo(mWarningLetterDTO2);
        mWarningLetterDTO2.setId(2L);
        assertThat(mWarningLetterDTO1).isNotEqualTo(mWarningLetterDTO2);
        mWarningLetterDTO1.setId(null);
        assertThat(mWarningLetterDTO1).isNotEqualTo(mWarningLetterDTO2);
    }
}
