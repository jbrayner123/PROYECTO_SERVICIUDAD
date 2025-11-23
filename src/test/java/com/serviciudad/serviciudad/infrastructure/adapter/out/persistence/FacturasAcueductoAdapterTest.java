package com.serviciudad.serviciudad.infrastructure.adapter.out.persistence;

import com.serviciudad.serviciudad.domain.model.FacturaAcueducto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacturasAcueductoAdapterTest {

    @Mock
    private FacturaAcueductoRepositoryJpa repository;

    @InjectMocks
    private FacturasAcueductoAdapter adapter;

    @Test
    void testFindByCliente() {
        FacturaAcueductoEntity entity = new FacturaAcueductoEntity("123", "2023-10", 20, new BigDecimal("30000"));
        entity.setId(1L);

        when(repository.findByIdCliente("123")).thenReturn(Arrays.asList(entity));

        List<FacturaAcueducto> result = adapter.findByCliente("123");

        assertEquals(1, result.size());
        FacturaAcueducto model = result.get(0);
        assertEquals(1L, model.getId());
        assertEquals("123", model.getIdCliente());
        assertEquals("2023-10", model.getPeriodo());
        assertEquals(20, model.getConsumoM3());
        assertEquals(new BigDecimal("30000"), model.getValorPagar());
    }

    @Test
    void testFindByClienteNullValues() {
        FacturaAcueductoEntity entity = new FacturaAcueductoEntity("123", "2023-10", null, null);
        entity.setId(1L);

        when(repository.findByIdCliente("123")).thenReturn(Arrays.asList(entity));

        List<FacturaAcueducto> result = adapter.findByCliente("123");

        assertEquals(1, result.size());
        FacturaAcueducto model = result.get(0);
        assertEquals(0, model.getConsumoM3());
        assertEquals(BigDecimal.ZERO, model.getValorPagar());
    }
}
