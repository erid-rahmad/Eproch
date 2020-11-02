package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductClassificationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductClassificationDTO.class);
        CProductClassificationDTO cProductClassificationDTO1 = new CProductClassificationDTO();
        cProductClassificationDTO1.setId(1L);
        CProductClassificationDTO cProductClassificationDTO2 = new CProductClassificationDTO();
        assertThat(cProductClassificationDTO1).isNotEqualTo(cProductClassificationDTO2);
        cProductClassificationDTO2.setId(cProductClassificationDTO1.getId());
        assertThat(cProductClassificationDTO1).isEqualTo(cProductClassificationDTO2);
        cProductClassificationDTO2.setId(2L);
        assertThat(cProductClassificationDTO1).isNotEqualTo(cProductClassificationDTO2);
        cProductClassificationDTO1.setId(null);
        assertThat(cProductClassificationDTO1).isNotEqualTo(cProductClassificationDTO2);
    }
}
