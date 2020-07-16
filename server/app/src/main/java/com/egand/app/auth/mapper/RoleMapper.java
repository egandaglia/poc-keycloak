package com.egand.app.auth.mapper;

import com.egand.app.security.RoleSec;
import com.egand.orm.db.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleSec toRoleSec(Role role);
}
