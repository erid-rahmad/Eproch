package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class PaDashboardItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaDashboardItem.class);
        PaDashboardItem paDashboardItem1 = new PaDashboardItem();
        paDashboardItem1.setId(1L);
        PaDashboardItem paDashboardItem2 = new PaDashboardItem();
        paDashboardItem2.setId(paDashboardItem1.getId());
        assertThat(paDashboardItem1).isEqualTo(paDashboardItem2);
        paDashboardItem2.setId(2L);
        assertThat(paDashboardItem1).isNotEqualTo(paDashboardItem2);
        paDashboardItem1.setId(null);
        assertThat(paDashboardItem1).isNotEqualTo(paDashboardItem2);
    }
}
