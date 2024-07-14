package com.chacha.energy.api.auth.mapper;


import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.domain.health.entity.Health;
import com.chacha.energy.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface AuthMapper {

    Member toEntity(AuthDto.SignUpRequest authDto);
    Health toHealthEntity(AuthDto.RegisterHealthInfoRequest authDto);
}
