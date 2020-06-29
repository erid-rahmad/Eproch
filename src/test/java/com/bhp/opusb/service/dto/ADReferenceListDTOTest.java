package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADReferenceListDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADReferenceListDTO.class);
        ADReferenceListDTO aDReferenceListDTO1 = new ADReferenceListDTO();
        aDReferenceListDTO1.setId(1L);
        ADReferenceListDTO aDReferenceListDTO2 = new ADReferenceListDTO();
        assertThat(aDReferenceListDTO1).isNotEqualTo(aDReferenceListDTO2);
        aDReferenceListDTO2.setId(aDReferenceListDTO1.getId());
        assertThat(aDReferenceListDTO1).isEqualTo(aDReferenceListDTO2);
        aDReferenceListDTO2.setId(2L);
        assertThat(aDReferenceListDTO1).isNotEqualTo(aDReferenceListDTO2);
        aDReferenceListDTO1.setId(null);
        assertThat(aDReferenceListDTO1).isNotEqualTo(aDReferenceListDTO2);
    }
}
