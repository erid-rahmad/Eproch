package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MRequisitionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MRequisitionDTO.class);
        MRequisitionDTO mRequisitionDTO1 = new MRequisitionDTO();
        mRequisitionDTO1.setId(1L);
        MRequisitionDTO mRequisitionDTO2 = new MRequisitionDTO();
        assertThat(mRequisitionDTO1).isNotEqualTo(mRequisitionDTO2);
        mRequisitionDTO2.setId(mRequisitionDTO1.getId());
        assertThat(mRequisitionDTO1).isEqualTo(mRequisitionDTO2);
        mRequisitionDTO2.setId(2L);
        assertThat(mRequisitionDTO1).isNotEqualTo(mRequisitionDTO2);
        mRequisitionDTO1.setId(null);
        assertThat(mRequisitionDTO1).isNotEqualTo(mRequisitionDTO2);
    }
}
