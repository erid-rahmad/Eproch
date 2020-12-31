package com.bhp.opusb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MProductCatalogMapperTest {

    private MProductCatalogMapper mProductCatalogMapper;

    @BeforeEach
    public void setUp() {
        mProductCatalogMapper = new MProductCatalogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(mProductCatalogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(mProductCatalogMapper.fromId(null)).isNull();
    }
}
