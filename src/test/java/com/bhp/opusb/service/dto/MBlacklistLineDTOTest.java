package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBlacklistLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBlacklistLineDTO.class);
        MBlacklistLineDTO mBlacklistLineDTO1 = new MBlacklistLineDTO();
        mBlacklistLineDTO1.setId(1L);
        MBlacklistLineDTO mBlacklistLineDTO2 = new MBlacklistLineDTO();
        assertThat(mBlacklistLineDTO1).isNotEqualTo(mBlacklistLineDTO2);
        mBlacklistLineDTO2.setId(mBlacklistLineDTO1.getId());
        assertThat(mBlacklistLineDTO1).isEqualTo(mBlacklistLineDTO2);
        mBlacklistLineDTO2.setId(2L);
        assertThat(mBlacklistLineDTO1).isNotEqualTo(mBlacklistLineDTO2);
        mBlacklistLineDTO1.setId(null);
        assertThat(mBlacklistLineDTO1).isNotEqualTo(mBlacklistLineDTO2);
    }
}
