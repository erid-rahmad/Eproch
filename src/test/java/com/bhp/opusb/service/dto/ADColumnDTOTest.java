package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADColumnDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADColumnDTO.class);
        ADColumnDTO aDColumnDTO1 = new ADColumnDTO();
        aDColumnDTO1.setId(1L);
        ADColumnDTO aDColumnDTO2 = new ADColumnDTO();
        assertThat(aDColumnDTO1).isNotEqualTo(aDColumnDTO2);
        aDColumnDTO2.setId(aDColumnDTO1.getId());
        assertThat(aDColumnDTO1).isEqualTo(aDColumnDTO2);
        aDColumnDTO2.setId(2L);
        assertThat(aDColumnDTO1).isNotEqualTo(aDColumnDTO2);
        aDColumnDTO1.setId(null);
        assertThat(aDColumnDTO1).isNotEqualTo(aDColumnDTO2);
    }
}
