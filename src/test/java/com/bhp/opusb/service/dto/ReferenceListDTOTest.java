package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ReferenceListDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReferenceListDTO.class);
        ReferenceListDTO referenceListDTO1 = new ReferenceListDTO();
        referenceListDTO1.setId(1L);
        ReferenceListDTO referenceListDTO2 = new ReferenceListDTO();
        assertThat(referenceListDTO1).isNotEqualTo(referenceListDTO2);
        referenceListDTO2.setId(referenceListDTO1.getId());
        assertThat(referenceListDTO1).isEqualTo(referenceListDTO2);
        referenceListDTO2.setId(2L);
        assertThat(referenceListDTO1).isNotEqualTo(referenceListDTO2);
        referenceListDTO1.setId(null);
        assertThat(referenceListDTO1).isNotEqualTo(referenceListDTO2);
    }
}
