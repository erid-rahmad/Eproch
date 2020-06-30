package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADReferenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADReferenceDTO.class);
        ADReferenceDTO aDReferenceDTO1 = new ADReferenceDTO();
        aDReferenceDTO1.setId(1L);
        ADReferenceDTO aDReferenceDTO2 = new ADReferenceDTO();
        assertThat(aDReferenceDTO1).isNotEqualTo(aDReferenceDTO2);
        aDReferenceDTO2.setId(aDReferenceDTO1.getId());
        assertThat(aDReferenceDTO1).isEqualTo(aDReferenceDTO2);
        aDReferenceDTO2.setId(2L);
        assertThat(aDReferenceDTO1).isNotEqualTo(aDReferenceDTO2);
        aDReferenceDTO1.setId(null);
        assertThat(aDReferenceDTO1).isNotEqualTo(aDReferenceDTO2);
    }
}
