package com.serviciudad.serviciudad.domain.port.in;

import com.serviciudad.serviciudad.infrastructure.adapter.in.rest.dto.DeudaConsolidadaDTO;

public interface ObtenerDeudaUseCase {
    DeudaConsolidadaDTO obtenerDeuda(String clienteId);
}
