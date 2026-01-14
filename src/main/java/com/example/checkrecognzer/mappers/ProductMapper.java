package com.example.checkrecognzer.mappers;

import com.example.checkrecognzer.entities.ProductCheckEntity;
import com.example.checkrecognzer.entities.ProductItemEntity;
import com.example.checkrecognzer.models.ProductCheck;
import com.example.checkrecognzer.models.ProductItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductCheck toDto(ProductCheckEntity entity);

    ProductCheckEntity toEntity(ProductCheck entity);

    @AfterMapping
    default void afterMappingToEntity(
            @MappingTarget ProductCheckEntity entity,
            ProductCheck check) {
        entity.setId(null);
        entity.setVersion(null);
        entity.getProductItems().forEach(i->{
            i.setId(null);
            i.setVersion(null);
        });
    }
    private void setNullForVersionAndId(ProductCheck check) {

    }

    List<ProductCheck> toDtoList(List<ProductCheckEntity> entities);

    ProductItem toDto(ProductItemEntity entity);

    List<ProductItem> toItemDtoList(List<ProductItemEntity> entities);
}
