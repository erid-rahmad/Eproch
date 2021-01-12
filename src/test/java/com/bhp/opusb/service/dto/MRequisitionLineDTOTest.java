package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRequisitionLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRequisitionLineDTO.class);
        MRequisitionLineDTO mRequisitionLineDTO1 = new MRequisitionLineDTO();
        mRequisitionLineDTO1.setId(1L);
        MRequisitionLineDTO mRequisitionLineDTO2 = new MRequisitionLineDTO();
        assertThat(mRequisitionLineDTO1).isNotEqualTo(mRequisitionLineDTO2);
        mRequisitionLineDTO2.setId(mRequisitionLineDTO1.getId());
        assertThat(mRequisitionLineDTO1).isEqualTo(mRequisitionLineDTO2);
        mRequisitionLineDTO2.setId(2L);
        assertThat(mRequisitionLineDTO1).isNotEqualTo(mRequisitionLineDTO2);
        mRequisitionLineDTO1.setId(null);
        assertThat(mRequisitionLineDTO1).isNotEqualTo(mRequisitionLineDTO2);
    }
}
