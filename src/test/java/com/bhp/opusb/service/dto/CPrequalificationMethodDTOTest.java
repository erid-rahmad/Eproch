package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPrequalificationMethodDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPrequalificationMethodDTO.class);
        CPrequalificationMethodDTO cPrequalificationMethodDTO1 = new CPrequalificationMethodDTO();
        cPrequalificationMethodDTO1.setId(1L);
        CPrequalificationMethodDTO cPrequalificationMethodDTO2 = new CPrequalificationMethodDTO();
        assertThat(cPrequalificationMethodDTO1).isNotEqualTo(cPrequalificationMethodDTO2);
        cPrequalificationMethodDTO2.setId(cPrequalificationMethodDTO1.getId());
        assertThat(cPrequalificationMethodDTO1).isEqualTo(cPrequalificationMethodDTO2);
        cPrequalificationMethodDTO2.setId(2L);
        assertThat(cPrequalificationMethodDTO1).isNotEqualTo(cPrequalificationMethodDTO2);
        cPrequalificationMethodDTO1.setId(null);
        assertThat(cPrequalificationMethodDTO1).isNotEqualTo(cPrequalificationMethodDTO2);
    }
}
