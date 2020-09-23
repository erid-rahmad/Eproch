package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductCategoryAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductCategoryAccountDTO.class);
        CProductCategoryAccountDTO cProductCategoryAccountDTO1 = new CProductCategoryAccountDTO();
        cProductCategoryAccountDTO1.setId(1L);
        CProductCategoryAccountDTO cProductCategoryAccountDTO2 = new CProductCategoryAccountDTO();
        assertThat(cProductCategoryAccountDTO1).isNotEqualTo(cProductCategoryAccountDTO2);
        cProductCategoryAccountDTO2.setId(cProductCategoryAccountDTO1.getId());
        assertThat(cProductCategoryAccountDTO1).isEqualTo(cProductCategoryAccountDTO2);
        cProductCategoryAccountDTO2.setId(2L);
        assertThat(cProductCategoryAccountDTO1).isNotEqualTo(cProductCategoryAccountDTO2);
        cProductCategoryAccountDTO1.setId(null);
        assertThat(cProductCategoryAccountDTO1).isNotEqualTo(cProductCategoryAccountDTO2);
    }
}
