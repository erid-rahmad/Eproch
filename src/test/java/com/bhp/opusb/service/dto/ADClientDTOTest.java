package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADClientDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADClientDTO.class);
        ADClientDTO aDClientDTO1 = new ADClientDTO();
        aDClientDTO1.setId(1L);
        ADClientDTO aDClientDTO2 = new ADClientDTO();
        assertThat(aDClientDTO1).isNotEqualTo(aDClientDTO2);
        aDClientDTO2.setId(aDClientDTO1.getId());
        assertThat(aDClientDTO1).isEqualTo(aDClientDTO2);
        aDClientDTO2.setId(2L);
        assertThat(aDClientDTO1).isNotEqualTo(aDClientDTO2);
        aDClientDTO1.setId(null);
        assertThat(aDClientDTO1).isNotEqualTo(aDClientDTO2);
    }
}
