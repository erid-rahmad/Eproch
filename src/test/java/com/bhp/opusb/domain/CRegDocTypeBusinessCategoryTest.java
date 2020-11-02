package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CRegDocTypeBusinessCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CRegDocTypeBusinessCategory.class);
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory1 = new CRegDocTypeBusinessCategory();
        cRegDocTypeBusinessCategory1.setId(1L);
        CRegDocTypeBusinessCategory cRegDocTypeBusinessCategory2 = new CRegDocTypeBusinessCategory();
        cRegDocTypeBusinessCategory2.setId(cRegDocTypeBusinessCategory1.getId());
        assertThat(cRegDocTypeBusinessCategory1).isEqualTo(cRegDocTypeBusinessCategory2);
        cRegDocTypeBusinessCategory2.setId(2L);
        assertThat(cRegDocTypeBusinessCategory1).isNotEqualTo(cRegDocTypeBusinessCategory2);
        cRegDocTypeBusinessCategory1.setId(null);
        assertThat(cRegDocTypeBusinessCategory1).isNotEqualTo(cRegDocTypeBusinessCategory2);
    }
}
