package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CBankDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CBankDTO.class);
        CBankDTO cBankDTO1 = new CBankDTO();
        cBankDTO1.setId(1L);
        CBankDTO cBankDTO2 = new CBankDTO();
        assertThat(cBankDTO1).isNotEqualTo(cBankDTO2);
        cBankDTO2.setId(cBankDTO1.getId());
        assertThat(cBankDTO1).isEqualTo(cBankDTO2);
        cBankDTO2.setId(2L);
        assertThat(cBankDTO1).isNotEqualTo(cBankDTO2);
        cBankDTO1.setId(null);
        assertThat(cBankDTO1).isNotEqualTo(cBankDTO2);
    }
}
