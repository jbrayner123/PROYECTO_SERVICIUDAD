package com.serviciudad.serviciudad.infrastructure.adapter.in.rest;

import com.serviciudad.serviciudad.application.usecase.ObtenerDeudaUseCase;
import com.serviciudad.serviciudad.infrastructure.adapter.in.rest.dto.DeudaConsolidadaDTO;
import com.serviciudad.serviciudad.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeudaController.class)
class DeudaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObtenerDeudaUseCase obtenerDeudaUseCase;

    @Test
    void testGetDeudaSuccess() throws Exception {
        String clienteId = "123";
        DeudaConsolidadaDTO dto = new DeudaConsolidadaDTO.Builder()
                .clienteId(clienteId)
                .nombreCliente("Juan Perez")
                .totalAPagar(new BigDecimal("50000"))
                .build();

        when(obtenerDeudaUseCase.obtenerDeuda(clienteId)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/clientes/{clienteId}/deuda-consolidada", clienteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(clienteId))
                .andExpect(jsonPath("$.nombreCliente").value("Juan Perez"))
                .andExpect(jsonPath("$.totalAPagar").value(50000));
    }

    @Test
    void testGetDeudaNotFound() throws Exception {
        String clienteId = "999";
        when(obtenerDeudaUseCase.obtenerDeuda(clienteId)).thenThrow(new NotFoundException("Cliente no encontrado"));

        mockMvc.perform(get("/api/v1/clientes/{clienteId}/deuda-consolidada", clienteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
