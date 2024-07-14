package com.chacha.energy.api.auth.mapper;


import com.chacha.energy.api.auth.dto.AuthDto;
<<<<<<< Updated upstream
import com.chacha.energy.domain.health.entity.Health;
=======
import com.chacha.energy.domain.admin.entity.Admin;
>>>>>>> Stashed changes
import com.chacha.energy.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface AuthMapper {

<<<<<<< Updated upstream
    Member toEntity(AuthDto.SignUpRequest authDto);
    Health toHealthEntity(AuthDto.RegisterHealthInfoRequest authDto);
=======
>>>>>>> Stashed changes
}
