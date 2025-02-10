package ru.alex.burdovitsin.mesh.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.alex.burdovitsin.mesh.model.jpa.User;
import ru.alex.burdovitsin.mesh.model.rest.UserItem;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PhoneDataMapper.class, EmailDataMapper.class})
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    @Mapping(source = "username", target = "userName")
    @Mapping(source = "dateOfBird", target = "dateOfBirth")
    @Mapping(source = "account.balance", target = "balance")
    @Mapping(source = "emailData", target = "emailItems")
    @Mapping(source = "phoneData", target = "phoneItems")
    UserItem userToUserItem(User user);

    List<UserItem> usersToUserItems(List<User> users);
}
