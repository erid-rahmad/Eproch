package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MShoppingCartItemMapperTest {

    private MShoppingCartItemMapper mShoppingCartItemMapper;

    @BeforeEach
    public void setUp() {
        mShoppingCartItemMapper = new MShoppingCartItemMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mShoppingCartItemMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mShoppingCartItemMapper.fromId(null)).isNull();
    }
}
