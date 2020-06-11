package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADFieldDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADFieldDTO.class);
        ADFieldDTO aDFieldDTO1 = new ADFieldDTO();
        aDFieldDTO1.setId(1L);
        ADFieldDTO aDFieldDTO2 = new ADFieldDTO();
        assertThat(aDFieldDTO1).isNotEqualTo(aDFieldDTO2);
        aDFieldDTO2.setId(aDFieldDTO1.getId());
        assertThat(aDFieldDTO1).isEqualTo(aDFieldDTO2);
        aDFieldDTO2.setId(2L);
        assertThat(aDFieldDTO1).isNotEqualTo(aDFieldDTO2);
        aDFieldDTO1.setId(null);
        assertThat(aDFieldDTO1).isNotEqualTo(aDFieldDTO2);
    }
}
