package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CTaxCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CTaxCategoryDTO.class);
        CTaxCategoryDTO cTaxCategoryDTO1 = new CTaxCategoryDTO();
        cTaxCategoryDTO1.setId(1L);
        CTaxCategoryDTO cTaxCategoryDTO2 = new CTaxCategoryDTO();
        assertThat(cTaxCategoryDTO1).isNotEqualTo(cTaxCategoryDTO2);
        cTaxCategoryDTO2.setId(cTaxCategoryDTO1.getId());
        assertThat(cTaxCategoryDTO1).isEqualTo(cTaxCategoryDTO2);
        cTaxCategoryDTO2.setId(2L);
        assertThat(cTaxCategoryDTO1).isNotEqualTo(cTaxCategoryDTO2);
        cTaxCategoryDTO1.setId(null);
        assertThat(cTaxCategoryDTO1).isNotEqualTo(cTaxCategoryDTO2);
    }
}
