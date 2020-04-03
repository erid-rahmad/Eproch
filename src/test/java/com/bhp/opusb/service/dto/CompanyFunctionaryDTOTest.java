package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CompanyFunctionaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyFunctionaryDTO.class);
        CompanyFunctionaryDTO companyFunctionaryDTO1 = new CompanyFunctionaryDTO();
        companyFunctionaryDTO1.setId(1L);
        CompanyFunctionaryDTO companyFunctionaryDTO2 = new CompanyFunctionaryDTO();
        assertThat(companyFunctionaryDTO1).isNotEqualTo(companyFunctionaryDTO2);
        companyFunctionaryDTO2.setId(companyFunctionaryDTO1.getId());
        assertThat(companyFunctionaryDTO1).isEqualTo(companyFunctionaryDTO2);
        companyFunctionaryDTO2.setId(2L);
        assertThat(companyFunctionaryDTO1).isNotEqualTo(companyFunctionaryDTO2);
        companyFunctionaryDTO1.setId(null);
        assertThat(companyFunctionaryDTO1).isNotEqualTo(companyFunctionaryDTO2);
    }
}
