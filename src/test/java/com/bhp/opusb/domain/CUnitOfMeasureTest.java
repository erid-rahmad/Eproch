package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CUnitOfMeasureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CUnitOfMeasure.class);
        CUnitOfMeasure cUnitOfMeasure1 = new CUnitOfMeasure();
        cUnitOfMeasure1.setId(1L);
        CUnitOfMeasure cUnitOfMeasure2 = new CUnitOfMeasure();
        cUnitOfMeasure2.setId(cUnitOfMeasure1.getId());
        assertThat(cUnitOfMeasure1).isEqualTo(cUnitOfMeasure2);
        cUnitOfMeasure2.setId(2L);
        assertThat(cUnitOfMeasure1).isNotEqualTo(cUnitOfMeasure2);
        cUnitOfMeasure1.setId(null);
        assertThat(cUnitOfMeasure1).isNotEqualTo(cUnitOfMeasure2);
    }
}
