package com.bhp.opusb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CompanyFunctionaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyFunctionary.class);
        CompanyFunctionary companyFunctionary1 = new CompanyFunctionary();
        companyFunctionary1.setId(1L);
        CompanyFunctionary companyFunctionary2 = new CompanyFunctionary();
        companyFunctionary2.setId(companyFunctionary1.getId());
        assertThat(companyFunctionary1).isEqualTo(companyFunctionary2);
        companyFunctionary2.setId(2L);
        assertThat(companyFunctionary1).isNotEqualTo(companyFunctionary2);
        companyFunctionary1.setId(null);
        assertThat(companyFunctionary1).isNotEqualTo(companyFunctionary2);
    }
}
