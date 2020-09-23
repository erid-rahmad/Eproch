package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CWarehouseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CWarehouse.class);
        CWarehouse cWarehouse1 = new CWarehouse();
        cWarehouse1.setId(1L);
        CWarehouse cWarehouse2 = new CWarehouse();
        cWarehouse2.setId(cWarehouse1.getId());
        assertThat(cWarehouse1).isEqualTo(cWarehouse2);
        cWarehouse2.setId(2L);
        assertThat(cWarehouse1).isNotEqualTo(cWarehouse2);
        cWarehouse1.setId(null);
        assertThat(cWarehouse1).isNotEqualTo(cWarehouse2);
    }
}
