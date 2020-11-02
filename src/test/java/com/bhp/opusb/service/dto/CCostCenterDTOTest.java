package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCostCenterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCostCenterDTO.class);
        CCostCenterDTO cCostCenterDTO1 = new CCostCenterDTO();
        cCostCenterDTO1.setId(1L);
        CCostCenterDTO cCostCenterDTO2 = new CCostCenterDTO();
        assertThat(cCostCenterDTO1).isNotEqualTo(cCostCenterDTO2);
        cCostCenterDTO2.setId(cCostCenterDTO1.getId());
        assertThat(cCostCenterDTO1).isEqualTo(cCostCenterDTO2);
        cCostCenterDTO2.setId(2L);
        assertThat(cCostCenterDTO1).isNotEqualTo(cCostCenterDTO2);
        cCostCenterDTO1.setId(null);
        assertThat(cCostCenterDTO1).isNotEqualTo(cCostCenterDTO2);
    }
}
