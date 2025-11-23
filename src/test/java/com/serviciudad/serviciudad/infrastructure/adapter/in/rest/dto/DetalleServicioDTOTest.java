package com.serviciudad.serviciudad.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class DetalleServicioDTOTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        DetalleServicioDTO dto = new DetalleServicioDTO();
        dto.setPeriodo("2023-10");
        dto.setConsumo("100 kWh");
        dto.setValorPagar(new BigDecimal("50000"));

        assertEquals("2023-10", dto.getPeriodo());
        assertEquals("100 kWh", dto.getConsumo());
        assertEquals(new BigDecimal("50000"), dto.getValorPagar());
    }

    @Test
    void testAllArgsConstructor() {
        DetalleServicioDTO dto = new DetalleServicioDTO("2023-10", "100 kWh", new BigDecimal("50000"));

        assertEquals("2023-10", dto.getPeriodo());
        assertEquals("100 kWh", dto.getConsumo());
        assertEquals(new BigDecimal("50000"), dto.getValorPagar());
    }
}
