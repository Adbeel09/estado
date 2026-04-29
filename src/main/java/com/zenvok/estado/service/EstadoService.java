package com.zenvok.estado.service;

import com.zenvok.estado.dto.EstadoRequestDTO;
import com.zenvok.estado.dto.EstadoResponseDTO;
import com.zenvok.estado.model.Estado;
import com.zenvok.estado.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    // Listar estados
    public List<EstadoResponseDTO> listar() {
        return estadoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    // Buscar por ID
    public EstadoResponseDTO buscarPorId(Long id) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

        return convertirAResponse(estado);
    }

    // Guardar estado
    public EstadoResponseDTO guardar(EstadoRequestDTO request) {
        Estado estado = new Estado();

        estado.setNombre(request.getNombre());
        estado.setDescripcion(request.getDescripcion());

        Estado guardado = estadoRepository.save(estado);

        return convertirAResponse(guardado);
    }

    // Actualizar estado
    public EstadoResponseDTO actualizar(Long id, EstadoRequestDTO request) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

        estado.setNombre(request.getNombre());
        estado.setDescripcion(request.getDescripcion());

        Estado actualizado = estadoRepository.save(estado);

        return convertirAResponse(actualizado);
    }

    // Eliminar estado
    public void eliminar(Long id) {
        Estado estado = estadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

        estadoRepository.delete(estado);
    }

    // Convertir Entity a ResponseDTO
    private EstadoResponseDTO convertirAResponse(Estado estado) {
        EstadoResponseDTO dto = new EstadoResponseDTO();

        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        dto.setDescripcion(estado.getDescripcion());

        return dto;
    }
}