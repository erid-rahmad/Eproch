package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADTabDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADTabDTO.class);
        ADTabDTO aDTabDTO1 = new ADTabDTO();
        aDTabDTO1.setId(1L);
        ADTabDTO aDTabDTO2 = new ADTabDTO();
        assertThat(aDTabDTO1).isNotEqualTo(aDTabDTO2);
        aDTabDTO2.setId(aDTabDTO1.getId());
        assertThat(aDTabDTO1).isEqualTo(aDTabDTO2);
        aDTabDTO2.setId(2L);
        assertThat(aDTabDTO1).isNotEqualTo(aDTabDTO2);
        aDTabDTO1.setId(null);
        assertThat(aDTabDTO1).isNotEqualTo(aDTabDTO2);
    }
}
