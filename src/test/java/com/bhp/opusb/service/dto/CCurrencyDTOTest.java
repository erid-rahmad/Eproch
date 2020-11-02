package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CCurrencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCurrencyDTO.class);
        CCurrencyDTO cCurrencyDTO1 = new CCurrencyDTO();
        cCurrencyDTO1.setId(1L);
        CCurrencyDTO cCurrencyDTO2 = new CCurrencyDTO();
        assertThat(cCurrencyDTO1).isNotEqualTo(cCurrencyDTO2);
        cCurrencyDTO2.setId(cCurrencyDTO1.getId());
        assertThat(cCurrencyDTO1).isEqualTo(cCurrencyDTO2);
        cCurrencyDTO2.setId(2L);
        assertThat(cCurrencyDTO1).isNotEqualTo(cCurrencyDTO2);
        cCurrencyDTO1.setId(null);
        assertThat(cCurrencyDTO1).isNotEqualTo(cCurrencyDTO2);
    }
}
