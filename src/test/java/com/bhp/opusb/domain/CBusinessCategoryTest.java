package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBusinessCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBusinessCategory.class);
        CBusinessCategory cBusinessCategory1 = new CBusinessCategory();
        cBusinessCategory1.setId(1L);
        CBusinessCategory cBusinessCategory2 = new CBusinessCategory();
        cBusinessCategory2.setId(cBusinessCategory1.getId());
        assertThat(cBusinessCategory1).isEqualTo(cBusinessCategory2);
        cBusinessCategory2.setId(2L);
        assertThat(cBusinessCategory1).isNotEqualTo(cBusinessCategory2);
        cBusinessCategory1.setId(null);
        assertThat(cBusinessCategory1).isNotEqualTo(cBusinessCategory2);
    }
}
