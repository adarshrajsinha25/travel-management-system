package com.easetravel.trip.service.impl;

import com.easetravel.trip.dto.request.DestinationRequest;
import com.easetravel.trip.dto.response.DestinationResponse;
import com.easetravel.trip.entity.Destination;
import com.easetravel.trip.exception.ResourceNotFoundException;
import com.easetravel.trip.mapper.DestinationMapper;
import com.easetravel.trip.repository.DestinationRepository;
import com.easetravel.trip.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationMapper destinationMapper;

    @Override
    public DestinationResponse createDestination(DestinationRequest request) {
        return destinationMapper.toResponse(destinationRepository.save(destinationMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public DestinationResponse getDestinationById(Long id) {
        return destinationMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(destinationMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DestinationResponse> findByCountry(String country) {
        return destinationRepository.findByCountryIgnoreCase(country).stream()
                .map(destinationMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public DestinationResponse updateDestination(Long id, DestinationRequest request) {
        Destination dest = findOrThrow(id);
        dest.setName(request.getName());
        dest.setCountry(request.getCountry());
        dest.setDescription(request.getDescription());
        dest.setImageUrl(request.getImageUrl());
        return destinationMapper.toResponse(destinationRepository.save(dest));
    }

    @Override
    public void deleteDestination(Long id) {
        if (!destinationRepository.existsById(id))
            throw new ResourceNotFoundException("Destination not found with id: " + id);
        destinationRepository.deleteById(id);
    }

    private Destination findOrThrow(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found with id: " + id));
    }
}

