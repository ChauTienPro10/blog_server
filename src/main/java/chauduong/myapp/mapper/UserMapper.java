package chauduong.myapp.mapper;

import chauduong.myapp.dto.response.UserCreateResponse;
import chauduong.myapp.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "account.roles")
    UserCreateResponse toUserCreateResponse(User  user);
}
