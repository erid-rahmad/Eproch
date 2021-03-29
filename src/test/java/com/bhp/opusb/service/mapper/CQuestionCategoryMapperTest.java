package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CQuestionCategoryMapperTest {

    private CQuestionCategoryMapper cQuestionCategoryMapper;

    @BeforeEach
    public void setUp() {
        cQuestionCategoryMapper = new CQuestionCategoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cQuestionCategoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cQuestionCategoryMapper.fromId(null)).isNull();
    }
}
