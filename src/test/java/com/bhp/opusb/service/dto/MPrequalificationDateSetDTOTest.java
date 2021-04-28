package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class MPrequalificationDateSetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MPrequalificationDateSetDTO.class);
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO1 = new MPrequalificationDateSetDTO();
        mPrequalificationDateSetDTO1.setId(1L);
        MPrequalificationDateSetDTO mPrequalificationDateSetDTO2 = new MPrequalificationDateSetDTO();
        assertThat(mPrequalificationDateSetDTO1).isNotEqualTo(mPrequalificationDateSetDTO2);
        mPrequalificationDateSetDTO2.setId(mPrequalificationDateSetDTO1.getId());
        assertThat(mPrequalificationDateSetDTO1).isEqualTo(mPrequalificationDateSetDTO2);
        mPrequalificationDateSetDTO2.setId(2L);
        assertThat(mPrequalificationDateSetDTO1).isNotEqualTo(mPrequalificationDateSetDTO2);
        mPrequalificationDateSetDTO1.setId(null);
        assertThat(mPrequalificationDateSetDTO1).isNotEqualTo(mPrequalificationDateSetDTO2);
    }
}
