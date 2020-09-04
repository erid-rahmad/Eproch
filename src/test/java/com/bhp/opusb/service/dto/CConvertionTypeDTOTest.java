package com.bhp.opusb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bhp.opusb.web.rest.TestUtil;

public class CConvertionTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CConvertionTypeDTO.class);
        CConvertionTypeDTO cConvertionTypeDTO1 = new CConvertionTypeDTO();
        cConvertionTypeDTO1.setId(1L);
        CConvertionTypeDTO cConvertionTypeDTO2 = new CConvertionTypeDTO();
        assertThat(cConvertionTypeDTO1).isNotEqualTo(cConvertionTypeDTO2);
        cConvertionTypeDTO2.setId(cConvertionTypeDTO1.getId());
        assertThat(cConvertionTypeDTO1).isEqualTo(cConvertionTypeDTO2);
        cConvertionTypeDTO2.setId(2L);
        assertThat(cConvertionTypeDTO1).isNotEqualTo(cConvertionTypeDTO2);
        cConvertionTypeDTO1.setId(null);
        assertThat(cConvertionTypeDTO1).isNotEqualTo(cConvertionTypeDTO2);
    }
}
