package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CFunctionaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CFunctionary.class);
        CFunctionary cFunctionary1 = new CFunctionary();
        cFunctionary1.setId(1L);
        CFunctionary cFunctionary2 = new CFunctionary();
        cFunctionary2.setId(cFunctionary1.getId());
        assertThat(cFunctionary1).isEqualTo(cFunctionary2);
        cFunctionary2.setId(2L);
        assertThat(cFunctionary1).isNotEqualTo(cFunctionary2);
        cFunctionary1.setId(null);
        assertThat(cFunctionary1).isNotEqualTo(cFunctionary2);
    }
}
