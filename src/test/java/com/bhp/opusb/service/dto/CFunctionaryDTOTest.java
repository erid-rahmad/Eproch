package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CFunctionaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CFunctionaryDTO.class);
        CFunctionaryDTO cFunctionaryDTO1 = new CFunctionaryDTO();
        cFunctionaryDTO1.setId(1L);
        CFunctionaryDTO cFunctionaryDTO2 = new CFunctionaryDTO();
        assertThat(cFunctionaryDTO1).isNotEqualTo(cFunctionaryDTO2);
        cFunctionaryDTO2.setId(cFunctionaryDTO1.getId());
        assertThat(cFunctionaryDTO1).isEqualTo(cFunctionaryDTO2);
        cFunctionaryDTO2.setId(2L);
        assertThat(cFunctionaryDTO1).isNotEqualTo(cFunctionaryDTO2);
        cFunctionaryDTO1.setId(null);
        assertThat(cFunctionaryDTO1).isNotEqualTo(cFunctionaryDTO2);
    }
}
