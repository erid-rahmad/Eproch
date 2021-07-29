package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MContractClauseDocumentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MContractClauseDocumentDTO.class);
        MContractClauseDocumentDTO mContractClauseDocumentDTO1 = new MContractClauseDocumentDTO();
        mContractClauseDocumentDTO1.setId(1L);
        MContractClauseDocumentDTO mContractClauseDocumentDTO2 = new MContractClauseDocumentDTO();
        assertThat(mContractClauseDocumentDTO1).isNotEqualTo(mContractClauseDocumentDTO2);
        mContractClauseDocumentDTO2.setId(mContractClauseDocumentDTO1.getId());
        assertThat(mContractClauseDocumentDTO1).isEqualTo(mContractClauseDocumentDTO2);
        mContractClauseDocumentDTO2.setId(2L);
        assertThat(mContractClauseDocumentDTO1).isNotEqualTo(mContractClauseDocumentDTO2);
        mContractClauseDocumentDTO1.setId(null);
        assertThat(mContractClauseDocumentDTO1).isNotEqualTo(mContractClauseDocumentDTO2);
    }
}
