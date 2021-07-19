package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MPreqRegistDocumentMapperTest {

    private MPreqRegistDocumentMapper mPreqRegistDocumentMapper;

    @BeforeEach
    public void setUp() {
        mPreqRegistDocumentMapper = new MPreqRegistDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mPreqRegistDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mPreqRegistDocumentMapper.fromId(null)).isNull();
    }
}
