package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADTableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADTableDTO.class);
        ADTableDTO aDTableDTO1 = new ADTableDTO();
        aDTableDTO1.setId(1L);
        ADTableDTO aDTableDTO2 = new ADTableDTO();
        assertThat(aDTableDTO1).isNotEqualTo(aDTableDTO2);
        aDTableDTO2.setId(aDTableDTO1.getId());
        assertThat(aDTableDTO1).isEqualTo(aDTableDTO2);
        aDTableDTO2.setId(2L);
        assertThat(aDTableDTO1).isNotEqualTo(aDTableDTO2);
        aDTableDTO1.setId(null);
        assertThat(aDTableDTO1).isNotEqualTo(aDTableDTO2);
    }
}
