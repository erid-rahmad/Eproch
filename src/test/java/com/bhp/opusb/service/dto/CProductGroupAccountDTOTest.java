package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CProductGroupAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CProductGroupAccountDTO.class);
        CProductGroupAccountDTO cProductGroupAccountDTO1 = new CProductGroupAccountDTO();
        cProductGroupAccountDTO1.setId(1L);
        CProductGroupAccountDTO cProductGroupAccountDTO2 = new CProductGroupAccountDTO();
        assertThat(cProductGroupAccountDTO1).isNotEqualTo(cProductGroupAccountDTO2);
        cProductGroupAccountDTO2.setId(cProductGroupAccountDTO1.getId());
        assertThat(cProductGroupAccountDTO1).isEqualTo(cProductGroupAccountDTO2);
        cProductGroupAccountDTO2.setId(2L);
        assertThat(cProductGroupAccountDTO1).isNotEqualTo(cProductGroupAccountDTO2);
        cProductGroupAccountDTO1.setId(null);
        assertThat(cProductGroupAccountDTO1).isNotEqualTo(cProductGroupAccountDTO2);
    }
}
