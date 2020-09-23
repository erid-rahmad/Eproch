package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductCategoryDTO.class);
        CProductCategoryDTO cProductCategoryDTO1 = new CProductCategoryDTO();
        cProductCategoryDTO1.setId(1L);
        CProductCategoryDTO cProductCategoryDTO2 = new CProductCategoryDTO();
        assertThat(cProductCategoryDTO1).isNotEqualTo(cProductCategoryDTO2);
        cProductCategoryDTO2.setId(cProductCategoryDTO1.getId());
        assertThat(cProductCategoryDTO1).isEqualTo(cProductCategoryDTO2);
        cProductCategoryDTO2.setId(2L);
        assertThat(cProductCategoryDTO1).isNotEqualTo(cProductCategoryDTO2);
        cProductCategoryDTO1.setId(null);
        assertThat(cProductCategoryDTO1).isNotEqualTo(cProductCategoryDTO2);
    }
}
