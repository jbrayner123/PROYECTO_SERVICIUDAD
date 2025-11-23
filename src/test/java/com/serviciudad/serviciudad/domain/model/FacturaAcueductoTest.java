package com.serviciudad.serviciudad.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FacturaAcueductoTest {

    @Test
    void testConstructorAndGetters() {
        BigDecimal valor = new BigDecimal("30000");
        FacturaAcueducto factura = new FacturaAcueducto(1L, "123", "2023-10", 20, valor);

        assertEquals(1L, factura.getId());
        assertEquals("123", factura.getIdCliente());
        assertEquals("2023-10", factura.getPeriodo());
        assertEquals(20, factura.getConsumoM3());
        assertEquals(valor, factura.getValorPagar());
    }

    @Test
    void testEqualsAndHashCode() {
        BigDecimal valor = new BigDecimal("30000");
        FacturaAcueducto f1 = new FacturaAcueducto(1L, "123", "2023-10", 20, valor);
        FacturaAcueducto f2 = new FacturaAcueducto(1L, "123", "2023-10", 20, valor);
        FacturaAcueducto f3 = new FacturaAcueducto(2L, "124", "2023-11", 25, valor);

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1, f3);
        assertNotEquals(f1, null);
        assertNotEquals(f1, new Object());
    }
}
