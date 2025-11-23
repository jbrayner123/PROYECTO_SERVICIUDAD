package com.serviciudad.serviciudad.application.usecase;

import com.serviciudad.serviciudad.domain.model.FacturaAcueducto;
import com.serviciudad.serviciudad.domain.model.FacturaEnergia;
import com.serviciudad.serviciudad.domain.port.out.FacturasAcueductoPort;
import com.serviciudad.serviciudad.domain.port.out.FacturasEnergiaPort;
import com.serviciudad.serviciudad.infrastructure.adapter.in.rest.dto.DeudaConsolidadaDTO;
import com.serviciudad.serviciudad.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsolidarDeudaServiceTest {

    @Mock
    private FacturasEnergiaPort energiaPort;

    @Mock
    private FacturasAcueductoPort acueductoPort;

    private ConsolidarDeudaService service;

    @BeforeEach
    void setUp() {
        service = new ConsolidarDeudaService(energiaPort, acueductoPort);
    }

    @Test
    void testObtenerDeudaFoundBoth() {
        String clienteId = "123";
        FacturaEnergia energia = new FacturaEnergia(clienteId, "2023-10", 100, new BigDecimal("50000"));
        FacturaAcueducto acueducto = new FacturaAcueducto(1L, clienteId, "2023-10", 20, new BigDecimal("30000"));

        when(energiaPort.obtenerFacturas()).thenReturn(Collections.singletonList(energia));
        when(acueductoPort.findByCliente(clienteId)).thenReturn(Collections.singletonList(acueducto));

        DeudaConsolidadaDTO result = service.obtenerDeuda(clienteId);

        assertNotNull(result);
        assertEquals(clienteId, result.getClienteId());
        assertEquals(new BigDecimal("80000"), result.getTotalAPagar());
        assertNotNull(result.getEnergia());
        assertNotNull(result.getAcueducto());
    }

    @Test
    void testObtenerDeudaFoundOnlyEnergia() {
        String clienteId = "123";
        FacturaEnergia energia = new FacturaEnergia(clienteId, "2023-10", 100, new BigDecimal("50000"));

        when(energiaPort.obtenerFacturas()).thenReturn(Collections.singletonList(energia));
        when(acueductoPort.findByCliente(clienteId)).thenReturn(Collections.emptyList());

        DeudaConsolidadaDTO result = service.obtenerDeuda(clienteId);

        assertNotNull(result);
        assertEquals(new BigDecimal("50000"), result.getTotalAPagar());
        assertNotNull(result.getEnergia());
        assertNull(result.getAcueducto());
    }

    @Test
    void testObtenerDeudaFoundOnlyAcueducto() {
        String clienteId = "123";
        FacturaAcueducto acueducto = new FacturaAcueducto(1L, clienteId, "2023-10", 20, new BigDecimal("30000"));

        when(energiaPort.obtenerFacturas()).thenReturn(Collections.emptyList());
        when(acueductoPort.findByCliente(clienteId)).thenReturn(Collections.singletonList(acueducto));

        DeudaConsolidadaDTO result = service.obtenerDeuda(clienteId);

        assertNotNull(result);
        assertEquals(new BigDecimal("30000"), result.getTotalAPagar());
        assertNull(result.getEnergia());
        assertNotNull(result.getAcueducto());
    }

    @Test
    void testObtenerDeudaNotFound() {
        String clienteId = "123";
        when(energiaPort.obtenerFacturas()).thenReturn(Collections.emptyList());
        when(acueductoPort.findByCliente(clienteId)).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> service.obtenerDeuda(clienteId));
    }

    @Test
    void testObtenerDeudaMultipleAcueducto() {
        String clienteId = "123";
        FacturaAcueducto f1 = new FacturaAcueducto(1L, clienteId, "2023-09", 20, new BigDecimal("30000"));
        FacturaAcueducto f2 = new FacturaAcueducto(2L, clienteId, "2023-10", 25, new BigDecimal("35000"));

        when(energiaPort.obtenerFacturas()).thenReturn(Collections.emptyList());
        when(acueductoPort.findByCliente(clienteId)).thenReturn(Arrays.asList(f1, f2));

        DeudaConsolidadaDTO result = service.obtenerDeuda(clienteId);

        assertNotNull(result);
        assertEquals(new BigDecimal("35000"), result.getTotalAPagar());
        assertEquals("2023-10", result.getAcueducto().getPeriodo());
    }
}
