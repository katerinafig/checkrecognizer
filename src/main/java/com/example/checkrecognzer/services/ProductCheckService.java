package com.example.checkrecognzer.services;

import com.example.checkrecognzer.entities.ProductCheckEntity;
import com.example.checkrecognzer.mappers.ProductMapper;
import com.example.checkrecognzer.models.ProductCheck;
import com.example.checkrecognzer.repositoties.ProductCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCheckService {
    private final ProductCheckRepository checkRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductCheck> getAllChecksWithItems() {
        return checkRepository.findAllWithItems().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductCheck findById(Long id) {
        return checkRepository.findById(id)
                .map(productMapper::toDto).orElse(null);
    }

    @Transactional
    public ProductCheckEntity createCheck(ProductCheck check) {
        return checkRepository.save(productMapper.toEntity(check));
    }


}
