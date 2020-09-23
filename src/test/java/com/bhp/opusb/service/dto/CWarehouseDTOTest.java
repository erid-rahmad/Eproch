package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CWarehouseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CWarehouseDTO.class);
        CWarehouseDTO cWarehouseDTO1 = new CWarehouseDTO();
        cWarehouseDTO1.setId(1L);
        CWarehouseDTO cWarehouseDTO2 = new CWarehouseDTO();
        assertThat(cWarehouseDTO1).isNotEqualTo(cWarehouseDTO2);
        cWarehouseDTO2.setId(cWarehouseDTO1.getId());
        assertThat(cWarehouseDTO1).isEqualTo(cWarehouseDTO2);
        cWarehouseDTO2.setId(2L);
        assertThat(cWarehouseDTO1).isNotEqualTo(cWarehouseDTO2);
        cWarehouseDTO1.setId(null);
        assertThat(cWarehouseDTO1).isNotEqualTo(cWarehouseDTO2);
    }
}
