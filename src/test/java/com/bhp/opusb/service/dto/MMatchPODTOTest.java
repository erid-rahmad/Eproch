package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MMatchPODTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MMatchPODTO.class);
        MMatchPODTO mMatchPODTO1 = new MMatchPODTO();
        mMatchPODTO1.setId(1L);
        MMatchPODTO mMatchPODTO2 = new MMatchPODTO();
        assertThat(mMatchPODTO1).isNotEqualTo(mMatchPODTO2);
        mMatchPODTO2.setId(mMatchPODTO1.getId());
        assertThat(mMatchPODTO1).isEqualTo(mMatchPODTO2);
        mMatchPODTO2.setId(2L);
        assertThat(mMatchPODTO1).isNotEqualTo(mMatchPODTO2);
        mMatchPODTO1.setId(null);
        assertThat(mMatchPODTO1).isNotEqualTo(mMatchPODTO2);
    }
}
