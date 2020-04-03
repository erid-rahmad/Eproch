package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class BusinessCategoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessCategory.class);
        BusinessCategory businessCategory1 = new BusinessCategory();
        businessCategory1.setId(1L);
        BusinessCategory businessCategory2 = new BusinessCategory();
        businessCategory2.setId(businessCategory1.getId());
        assertThat(businessCategory1).isEqualTo(businessCategory2);
        businessCategory2.setId(2L);
        assertThat(businessCategory1).isNotEqualTo(businessCategory2);
        businessCategory1.setId(null);
        assertThat(businessCategory1).isNotEqualTo(businessCategory2);
    }
}
