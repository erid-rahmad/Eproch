package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ScAccessTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScAccessTypeDTO.class);
        ScAccessTypeDTO scAccessTypeDTO1 = new ScAccessTypeDTO();
        scAccessTypeDTO1.setId(1L);
        ScAccessTypeDTO scAccessTypeDTO2 = new ScAccessTypeDTO();
        assertThat(scAccessTypeDTO1).isNotEqualTo(scAccessTypeDTO2);
        scAccessTypeDTO2.setId(scAccessTypeDTO1.getId());
        assertThat(scAccessTypeDTO1).isEqualTo(scAccessTypeDTO2);
        scAccessTypeDTO2.setId(2L);
        assertThat(scAccessTypeDTO1).isNotEqualTo(scAccessTypeDTO2);
        scAccessTypeDTO1.setId(null);
        assertThat(scAccessTypeDTO1).isNotEqualTo(scAccessTypeDTO2);
    }
}
