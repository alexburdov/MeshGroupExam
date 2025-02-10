package ru.alex.burdovitsin.mesh.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.alex.burdovitsin.mesh.model.jpa.PhoneData;
import ru.alex.burdovitsin.mesh.model.rest.PhoneItem;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PhoneDataMapper {

    @Mapping(source = "id", target = "phoneId")
    @Mapping(source = "phone", target = "phoneNumber")
    PhoneItem toPhoneItem(PhoneData phoneData);

    List<PhoneItem> toPhoneItems(List<PhoneData> phones);
}
