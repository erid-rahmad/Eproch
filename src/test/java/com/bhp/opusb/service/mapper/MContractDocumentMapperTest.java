package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractDocumentMapperTest {

    private MContractDocumentMapper mContractDocumentMapper;

    @BeforeEach
    public void setUp() {
        mContractDocumentMapper = new MContractDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractDocumentMapper.fromId(null)).isNull();
    }
}
