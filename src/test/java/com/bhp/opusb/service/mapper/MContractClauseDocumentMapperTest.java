package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MContractClauseDocumentMapperTest {

    private MContractClauseDocumentMapper mContractClauseDocumentMapper;

    @BeforeEach
    public void setUp() {
        mContractClauseDocumentMapper = new MContractClauseDocumentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mContractClauseDocumentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mContractClauseDocumentMapper.fromId(null)).isNull();
    }
}
