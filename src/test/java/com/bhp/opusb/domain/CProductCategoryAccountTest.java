package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductCategoryAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductCategoryAccount.class);
        CProductCategoryAccount cProductCategoryAccount1 = new CProductCategoryAccount();
        cProductCategoryAccount1.setId(1L);
        CProductCategoryAccount cProductCategoryAccount2 = new CProductCategoryAccount();
        cProductCategoryAccount2.setId(cProductCategoryAccount1.getId());
        assertThat(cProductCategoryAccount1).isEqualTo(cProductCategoryAccount2);
        cProductCategoryAccount2.setId(2L);
        assertThat(cProductCategoryAccount1).isNotEqualTo(cProductCategoryAccount2);
        cProductCategoryAccount1.setId(null);
        assertThat(cProductCategoryAccount1).isNotEqualTo(cProductCategoryAccount2);
    }
}
