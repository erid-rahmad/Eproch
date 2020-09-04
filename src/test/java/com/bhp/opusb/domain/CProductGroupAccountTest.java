package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductGroupAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductGroupAccount.class);
        CProductGroupAccount cProductGroupAccount1 = new CProductGroupAccount();
        cProductGroupAccount1.setId(1L);
        CProductGroupAccount cProductGroupAccount2 = new CProductGroupAccount();
        cProductGroupAccount2.setId(cProductGroupAccount1.getId());
        assertThat(cProductGroupAccount1).isEqualTo(cProductGroupAccount2);
        cProductGroupAccount2.setId(2L);
        assertThat(cProductGroupAccount1).isNotEqualTo(cProductGroupAccount2);
        cProductGroupAccount1.setId(null);
        assertThat(cProductGroupAccount1).isNotEqualTo(cProductGroupAccount2);
    }
}
