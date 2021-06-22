package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractDocumentDTO.class);
        MContractDocumentDTO mContractDocumentDTO1 = new MContractDocumentDTO();
        mContractDocumentDTO1.setId(1L);
        MContractDocumentDTO mContractDocumentDTO2 = new MContractDocumentDTO();
        assertThat(mContractDocumentDTO1).isNotEqualTo(mContractDocumentDTO2);
        mContractDocumentDTO2.setId(mContractDocumentDTO1.getId());
        assertThat(mContractDocumentDTO1).isEqualTo(mContractDocumentDTO2);
        mContractDocumentDTO2.setId(2L);
        assertThat(mContractDocumentDTO1).isNotEqualTo(mContractDocumentDTO2);
        mContractDocumentDTO1.setId(null);
        assertThat(mContractDocumentDTO1).isNotEqualTo(mContractDocumentDTO2);
    }
}
