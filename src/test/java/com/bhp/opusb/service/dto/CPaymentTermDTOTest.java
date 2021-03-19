package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CPaymentTermDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPaymentTermDTO.class);
        CPaymentTermDTO cPaymentTermDTO1 = new CPaymentTermDTO();
        cPaymentTermDTO1.setId(1L);
        CPaymentTermDTO cPaymentTermDTO2 = new CPaymentTermDTO();
        assertThat(cPaymentTermDTO1).isNotEqualTo(cPaymentTermDTO2);
        cPaymentTermDTO2.setId(cPaymentTermDTO1.getId());
        assertThat(cPaymentTermDTO1).isEqualTo(cPaymentTermDTO2);
        cPaymentTermDTO2.setId(2L);
        assertThat(cPaymentTermDTO1).isNotEqualTo(cPaymentTermDTO2);
        cPaymentTermDTO1.setId(null);
        assertThat(cPaymentTermDTO1).isNotEqualTo(cPaymentTermDTO2);
    }
}
