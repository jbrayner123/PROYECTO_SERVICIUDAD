package com.serviciudad.serviciudad.infrastructure.adapter.in.rest.dto;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class DeudaConsolidadaDTOTest {

    @Test
    void testBuilder() {
        Instant now = Instant.now();
        DetalleServicioDTO energia = new DetalleServicioDTO("2023-10", "100 kWh", new BigDecimal("50000"));
        DetalleServicioDTO acueducto = new DetalleServicioDTO("2023-10", "20 m3", new BigDecimal("30000"));

        DeudaConsolidadaDTO dto = new DeudaConsolidadaDTO.Builder()
                .clienteId("123")
                .nombreCliente("Juan Perez")
                .fechaConsulta(now)
                .energia(energia)
                .acueducto(acueducto)
                .totalAPagar(new BigDecimal("80000"))
                .build();

        assertEquals("123", dto.getClienteId());
        assertEquals("Juan Perez", dto.getNombreCliente());
        assertEquals(now, dto.getFechaConsulta());
        assertEquals(energia, dto.getEnergia());
        assertEquals(acueducto, dto.getAcueducto());
        assertEquals(new BigDecimal("80000"), dto.getTotalAPagar());
    }
}
