package com.serviciudad.serviciudad.infrastructure.adapter.out.file;

import com.serviciudad.serviciudad.domain.model.FacturaEnergia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdaptadorArchivoEnergiaTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    private AdaptadorArchivoEnergia adaptador;

    @BeforeEach
    void setUp() {
        // Initialize with a classpath path to trigger the classpath logic
        adaptador = new AdaptadorArchivoEnergia("classpath:test.txt", resourceLoader);
    }

    @Test
    void testObtenerFacturasSuccess() throws IOException {
        // Line format: id(10) periodo(6) consumo(8) valor(rest)
        // "12345678902023100000010050000"
        // id: 1234567890
        // periodo: 202310
        // consumo: 00000100 -> 100
        // valor: 50000
        String content = "12345678902023100000010050000";
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(content.getBytes()));

        List<FacturaEnergia> facturas = adaptador.obtenerFacturas();

        assertNotNull(facturas);
        assertEquals(1, facturas.size());
        FacturaEnergia f = facturas.get(0);
        assertEquals("1234567890", f.getIdCliente());
        assertEquals("202310", f.getPeriodo());
        assertEquals(100, f.getConsumoKwh());
        assertEquals(new BigDecimal("50000"), f.getValorPagar());
    }

    @Test
    void testObtenerFacturasMultipleLines() throws IOException {
        String content = "123       2023100000010050000\n" +
                "456       2023110000020075000.50";
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(content.getBytes()));

        List<FacturaEnergia> facturas = adaptador.obtenerFacturas();

        assertEquals(2, facturas.size());
        assertEquals("123", facturas.get(0).getIdCliente());
        assertEquals(new BigDecimal("50000"), facturas.get(0).getValorPagar());
        assertEquals("456", facturas.get(1).getIdCliente());
        assertEquals(new BigDecimal("75000.50"), facturas.get(1).getValorPagar());
    }

    @Test
    void testObtenerFacturasInvalidNumber() throws IOException {
        // Invalid consumption and value
        String content = "123       202310XXXXXXXXYYYYY";
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.exists()).thenReturn(true);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(content.getBytes()));

        List<FacturaEnergia> facturas = adaptador.obtenerFacturas();

        assertEquals(1, facturas.size());
        assertEquals(0, facturas.get(0).getConsumoKwh());
        assertEquals(BigDecimal.ZERO, facturas.get(0).getValorPagar());
    }

    @Test
    void testObtenerFacturasFileNotFound() {
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.exists()).thenReturn(false);

        assertThrows(RuntimeException.class, () -> adaptador.obtenerFacturas());
    }
}
