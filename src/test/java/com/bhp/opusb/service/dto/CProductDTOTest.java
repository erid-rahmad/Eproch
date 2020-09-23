package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductDTO.class);
        CProductDTO cProductDTO1 = new CProductDTO();
        cProductDTO1.setId(1L);
        CProductDTO cProductDTO2 = new CProductDTO();
        assertThat(cProductDTO1).isNotEqualTo(cProductDTO2);
        cProductDTO2.setId(cProductDTO1.getId());
        assertThat(cProductDTO1).isEqualTo(cProductDTO2);
        cProductDTO2.setId(2L);
        assertThat(cProductDTO1).isNotEqualTo(cProductDTO2);
        cProductDTO1.setId(null);
        assertThat(cProductDTO1).isNotEqualTo(cProductDTO2);
    }
}
