package com.chacha.energy.api.auth.mapper;


import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.domain.admin.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface AuthMapper {
    Admin AuthDtoToAdmin(AuthDto.Post authDto);
}
