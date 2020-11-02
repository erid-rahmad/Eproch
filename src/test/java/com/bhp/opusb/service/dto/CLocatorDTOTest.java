package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CLocatorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CLocatorDTO.class);
        CLocatorDTO cLocatorDTO1 = new CLocatorDTO();
        cLocatorDTO1.setId(1L);
        CLocatorDTO cLocatorDTO2 = new CLocatorDTO();
        assertThat(cLocatorDTO1).isNotEqualTo(cLocatorDTO2);
        cLocatorDTO2.setId(cLocatorDTO1.getId());
        assertThat(cLocatorDTO1).isEqualTo(cLocatorDTO2);
        cLocatorDTO2.setId(2L);
        assertThat(cLocatorDTO1).isNotEqualTo(cLocatorDTO2);
        cLocatorDTO1.setId(null);
        assertThat(cLocatorDTO1).isNotEqualTo(cLocatorDTO2);
    }
}
