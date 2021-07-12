package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MBlacklistDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MBlacklistDTO.class);
        MBlacklistDTO mBlacklistDTO1 = new MBlacklistDTO();
        mBlacklistDTO1.setId(1L);
        MBlacklistDTO mBlacklistDTO2 = new MBlacklistDTO();
        assertThat(mBlacklistDTO1).isNotEqualTo(mBlacklistDTO2);
        mBlacklistDTO2.setId(mBlacklistDTO1.getId());
        assertThat(mBlacklistDTO1).isEqualTo(mBlacklistDTO2);
        mBlacklistDTO2.setId(2L);
        assertThat(mBlacklistDTO1).isNotEqualTo(mBlacklistDTO2);
        mBlacklistDTO1.setId(null);
        assertThat(mBlacklistDTO1).isNotEqualTo(mBlacklistDTO2);
    }
}
