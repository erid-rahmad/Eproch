package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class ADWindowDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ADWindowDTO.class);
        ADWindowDTO aDWindowDTO1 = new ADWindowDTO();
        aDWindowDTO1.setId(1L);
        ADWindowDTO aDWindowDTO2 = new ADWindowDTO();
        assertThat(aDWindowDTO1).isNotEqualTo(aDWindowDTO2);
        aDWindowDTO2.setId(aDWindowDTO1.getId());
        assertThat(aDWindowDTO1).isEqualTo(aDWindowDTO2);
        aDWindowDTO2.setId(2L);
        assertThat(aDWindowDTO1).isNotEqualTo(aDWindowDTO2);
        aDWindowDTO1.setId(null);
        assertThat(aDWindowDTO1).isNotEqualTo(aDWindowDTO2);
    }
}
