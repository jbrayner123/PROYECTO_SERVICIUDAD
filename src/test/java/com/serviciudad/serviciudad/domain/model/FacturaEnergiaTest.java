package com.serviciudad.serviciudad.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FacturaEnergiaTest {

    @Test
    void testConstructorAndGetters() {
        BigDecimal valor = new BigDecimal("50000");
        FacturaEnergia factura = new FacturaEnergia("123", "2023-10", 100, valor);

        assertEquals("123", factura.getIdCliente());
        assertEquals("2023-10", factura.getPeriodo());
        assertEquals(100, factura.getConsumoKwh());
        assertEquals(valor, factura.getValorPagar());
    }

    @Test
    void testEqualsAndHashCode() {
        BigDecimal valor = new BigDecimal("50000");
        FacturaEnergia f1 = new FacturaEnergia("123", "2023-10", 100, valor);
        FacturaEnergia f2 = new FacturaEnergia("123", "2023-10", 100, valor);
        FacturaEnergia f3 = new FacturaEnergia("124", "2023-11", 150, valor);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1, f3);
        assertNotEquals(f1, null);
        assertNotEquals(f1, new Object());
    }
}
