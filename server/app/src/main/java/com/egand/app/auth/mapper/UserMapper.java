package com.egand.app.auth.mapper;

import com.egand.app.auth.dto.user.LoginCredentials;
import com.egand.app.auth.dto.user.RegisterCredentials;
import com.egand.app.auth.dto.user.UserAuthenticated;
import com.egand.app.security.UserSec;
import com.egand.orm.db.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(LoginCredentials loginCredentials);

    LoginCredentials toLoginCredentials(User user);

    User toUser(RegisterCredentials registerCredentials);

    RegisterCredentials toRegisterCredentials(User user);

    UserAuthenticated toUserAuthenticated(User user);

    UserSec toUserSec(User user);

}
