package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCostCenterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCostCenter.class);
        CCostCenter cCostCenter1 = new CCostCenter();
        cCostCenter1.setId(1L);
        CCostCenter cCostCenter2 = new CCostCenter();
        cCostCenter2.setId(cCostCenter1.getId());
        assertThat(cCostCenter1).isEqualTo(cCostCenter2);
        cCostCenter2.setId(2L);
        assertThat(cCostCenter1).isNotEqualTo(cCostCenter2);
        cCostCenter1.setId(null);
        assertThat(cCostCenter1).isNotEqualTo(cCostCenter2);
    }
}
