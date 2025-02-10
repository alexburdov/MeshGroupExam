package ru.alex.burdovitsin.mesh.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.alex.burdovitsin.mesh.model.jpa.EmailData;
import ru.alex.burdovitsin.mesh.model.rest.EmailItem;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailDataMapper {

    @Mapping(source = "id", target = "emailId")
    EmailItem toEmail(EmailData emailData);

    List<EmailItem> toEmails(List<EmailData> emailDatas);
}
