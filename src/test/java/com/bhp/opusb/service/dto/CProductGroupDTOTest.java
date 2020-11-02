package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductGroupDTO.class);
        CProductGroupDTO cProductGroupDTO1 = new CProductGroupDTO();
        cProductGroupDTO1.setId(1L);
        CProductGroupDTO cProductGroupDTO2 = new CProductGroupDTO();
        assertThat(cProductGroupDTO1).isNotEqualTo(cProductGroupDTO2);
        cProductGroupDTO2.setId(cProductGroupDTO1.getId());
        assertThat(cProductGroupDTO1).isEqualTo(cProductGroupDTO2);
        cProductGroupDTO2.setId(2L);
        assertThat(cProductGroupDTO1).isNotEqualTo(cProductGroupDTO2);
        cProductGroupDTO1.setId(null);
        assertThat(cProductGroupDTO1).isNotEqualTo(cProductGroupDTO2);
    }
}
