package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CDocumentTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CDocumentTypeDTO.class);
        CDocumentTypeDTO cDocumentTypeDTO1 = new CDocumentTypeDTO();
        cDocumentTypeDTO1.setId(1L);
        CDocumentTypeDTO cDocumentTypeDTO2 = new CDocumentTypeDTO();
        assertThat(cDocumentTypeDTO1).isNotEqualTo(cDocumentTypeDTO2);
        cDocumentTypeDTO2.setId(cDocumentTypeDTO1.getId());
        assertThat(cDocumentTypeDTO1).isEqualTo(cDocumentTypeDTO2);
        cDocumentTypeDTO2.setId(2L);
        assertThat(cDocumentTypeDTO1).isNotEqualTo(cDocumentTypeDTO2);
        cDocumentTypeDTO1.setId(null);
        assertThat(cDocumentTypeDTO1).isNotEqualTo(cDocumentTypeDTO2);
    }
}
