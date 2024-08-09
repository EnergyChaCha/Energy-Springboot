package com.chacha.energy.cj;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CjMapper {
//    CjEntity toEntity(CjDto.CjDtoResponse cjDtoResponse);
}
