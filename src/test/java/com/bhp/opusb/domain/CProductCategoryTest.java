package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductCategory.class);
        CProductCategory cProductCategory1 = new CProductCategory();
        cProductCategory1.setId(1L);
        CProductCategory cProductCategory2 = new CProductCategory();
        cProductCategory2.setId(cProductCategory1.getId());
        assertThat(cProductCategory1).isEqualTo(cProductCategory2);
        cProductCategory2.setId(2L);
        assertThat(cProductCategory1).isNotEqualTo(cProductCategory2);
        cProductCategory1.setId(null);
        assertThat(cProductCategory1).isNotEqualTo(cProductCategory2);
    }
}
