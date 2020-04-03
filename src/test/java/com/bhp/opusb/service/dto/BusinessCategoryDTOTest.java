package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class BusinessCategoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessCategoryDTO.class);
        BusinessCategoryDTO businessCategoryDTO1 = new BusinessCategoryDTO();
        businessCategoryDTO1.setId(1L);
        BusinessCategoryDTO businessCategoryDTO2 = new BusinessCategoryDTO();
        assertThat(businessCategoryDTO1).isNotEqualTo(businessCategoryDTO2);
        businessCategoryDTO2.setId(businessCategoryDTO1.getId());
        assertThat(businessCategoryDTO1).isEqualTo(businessCategoryDTO2);
        businessCategoryDTO2.setId(2L);
        assertThat(businessCategoryDTO1).isNotEqualTo(businessCategoryDTO2);
        businessCategoryDTO1.setId(null);
        assertThat(businessCategoryDTO1).isNotEqualTo(businessCategoryDTO2);
    }
}
