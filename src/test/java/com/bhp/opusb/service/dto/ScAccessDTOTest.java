package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAccessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAccessDTO.class);
        ScAccessDTO scAccessDTO1 = new ScAccessDTO();
        scAccessDTO1.setId(1L);
        ScAccessDTO scAccessDTO2 = new ScAccessDTO();
        assertThat(scAccessDTO1).isNotEqualTo(scAccessDTO2);
        scAccessDTO2.setId(scAccessDTO1.getId());
        assertThat(scAccessDTO1).isEqualTo(scAccessDTO2);
        scAccessDTO2.setId(2L);
        assertThat(scAccessDTO1).isNotEqualTo(scAccessDTO2);
        scAccessDTO1.setId(null);
        assertThat(scAccessDTO1).isNotEqualTo(scAccessDTO2);
    }
}
