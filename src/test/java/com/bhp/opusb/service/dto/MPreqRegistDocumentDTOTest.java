package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPreqRegistDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPreqRegistDocumentDTO.class);
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO1 = new MPreqRegistDocumentDTO();
        mPreqRegistDocumentDTO1.setId(1L);
        MPreqRegistDocumentDTO mPreqRegistDocumentDTO2 = new MPreqRegistDocumentDTO();
        assertThat(mPreqRegistDocumentDTO1).isNotEqualTo(mPreqRegistDocumentDTO2);
        mPreqRegistDocumentDTO2.setId(mPreqRegistDocumentDTO1.getId());
        assertThat(mPreqRegistDocumentDTO1).isEqualTo(mPreqRegistDocumentDTO2);
        mPreqRegistDocumentDTO2.setId(2L);
        assertThat(mPreqRegistDocumentDTO1).isNotEqualTo(mPreqRegistDocumentDTO2);
        mPreqRegistDocumentDTO1.setId(null);
        assertThat(mPreqRegistDocumentDTO1).isNotEqualTo(mPreqRegistDocumentDTO2);
    }
}
