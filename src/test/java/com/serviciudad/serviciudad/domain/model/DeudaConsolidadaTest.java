package com.serviciudad.serviciudad.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

class DeudaConsolidadaTest {

    @Test
    void testCalcularTotalWithBothInvoices() {
        FacturaEnergia energia = new FacturaEnergia("123", "2023-10", 100, new BigDecimal("50000"));
        FacturaAcueducto acueducto = new FacturaAcueducto(1L, "123", "2023-10", 20, new BigDecimal("30000"));
        DeudaConsolidada deuda = new DeudaConsolidada("123", "Juan Perez", energia, acueducto, Instant.now());

        assertEquals(new BigDecimal("80000"), deuda.calcularTotal());
        assertEquals("123", deuda.getClienteId());
        assertEquals("Juan Perez", deuda.getNombreCliente());
        assertNotNull(deuda.getFechaConsulta());
        assertEquals(energia, deuda.getFacturaEnergia());
        assertEquals(acueducto, deuda.getFacturaAcueducto());
    }

    @Test
    void testCalcularTotalWithOnlyEnergia() {
        FacturaEnergia energia = new FacturaEnergia("123", "2023-10", 100, new BigDecimal("50000"));
        DeudaConsolidada deuda = new DeudaConsolidada("123", "Juan Perez", energia, null, Instant.now());

        assertEquals(new BigDecimal("50000"), deuda.calcularTotal());
    }

    @Test
    void testCalcularTotalWithOnlyAcueducto() {
        FacturaAcueducto acueducto = new FacturaAcueducto(1L, "123", "2023-10", 20, new BigDecimal("30000"));
        DeudaConsolidada deuda = new DeudaConsolidada("123", "Juan Perez", null, acueducto, Instant.now());

        assertEquals(new BigDecimal("30000"), deuda.calcularTotal());
    }

    @Test
    void testCalcularTotalWithNoInvoices() {
        DeudaConsolidada deuda = new DeudaConsolidada("123", "Juan Perez", null, null, Instant.now());

        assertEquals(BigDecimal.ZERO, deuda.calcularTotal());
    }

    @Test
    void testCalcularTotalWithNullValues() {
        FacturaEnergia energia = new FacturaEnergia("123", "2023-10", 100, null);
        FacturaAcueducto acueducto = new FacturaAcueducto(1L, "123", "2023-10", 20, null);
        DeudaConsolidada deuda = new DeudaConsolidada("123", "Juan Perez", energia, acueducto, Instant.now());

        assertEquals(BigDecimal.ZERO, deuda.calcularTotal());
    }
}
