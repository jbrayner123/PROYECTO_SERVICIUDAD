package com.serviciudad.serviciudad.infrastructure.adapter.out.persistence;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FacturaAcueductoEntityTest {

    @Test
    void testEntity() {
        FacturaAcueductoEntity entity = new FacturaAcueductoEntity("123", "2023-10", 20, new BigDecimal("30000"));
        entity.setId(1L);

        assertEquals(1L, entity.getId());
        assertEquals("123", entity.getIdCliente());
        assertEquals("2023-10", entity.getPeriodo());
        assertEquals(20, entity.getConsumoM3());
        assertEquals(new BigDecimal("30000"), entity.getValorPagar());
    }

    @Test
    void testSetters() {
        FacturaAcueductoEntity entity = new FacturaAcueductoEntity();
        entity.setId(1L);
        entity.setIdCliente("123");
        entity.setPeriodo("2023-10");
        entity.setConsumoM3(20);
        entity.setValorPagar(new BigDecimal("30000"));

        assertEquals(1L, entity.getId());
        assertEquals("123", entity.getIdCliente());
        assertEquals("2023-10", entity.getPeriodo());
        assertEquals(20, entity.getConsumoM3());
        assertEquals(new BigDecimal("30000"), entity.getValorPagar());
    }
}
