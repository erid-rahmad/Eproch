package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CUnitOfMeasureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CUnitOfMeasureDTO.class);
        CUnitOfMeasureDTO cUnitOfMeasureDTO1 = new CUnitOfMeasureDTO();
        cUnitOfMeasureDTO1.setId(1L);
        CUnitOfMeasureDTO cUnitOfMeasureDTO2 = new CUnitOfMeasureDTO();
        assertThat(cUnitOfMeasureDTO1).isNotEqualTo(cUnitOfMeasureDTO2);
        cUnitOfMeasureDTO2.setId(cUnitOfMeasureDTO1.getId());
        assertThat(cUnitOfMeasureDTO1).isEqualTo(cUnitOfMeasureDTO2);
        cUnitOfMeasureDTO2.setId(2L);
        assertThat(cUnitOfMeasureDTO1).isNotEqualTo(cUnitOfMeasureDTO2);
        cUnitOfMeasureDTO1.setId(null);
        assertThat(cUnitOfMeasureDTO1).isNotEqualTo(cUnitOfMeasureDTO2);
    }
}
