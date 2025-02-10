package ru.alex.burdovitsin.mesh.common;

import io.jsonwebtoken.lang.Strings;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.alex.burdovitsin.mesh.model.rest.UserSeekRequest;

import java.util.Objects;

@UtilityClass
public class CommonUtils {

    private static final String FOR_SEARCH_ALL_IN_LIKE = "%";

    public UserSeekRequest normalizeSeekRequest(final UserSeekRequest request) {
        if (Objects.isNull(request.getNameStartFrom())) {
            request.setNameStartFrom(Strings.EMPTY);
        }
        request.setNameStartFrom(request.getNameStartFrom() + FOR_SEARCH_ALL_IN_LIKE);
        if (Objects.isNull(request.getEmailFull())) {
            request.setEmailFull(FOR_SEARCH_ALL_IN_LIKE);
        }
        if (Objects.isNull(request.getPhoneNumberFull())) {
            request.setPhoneNumberFull(FOR_SEARCH_ALL_IN_LIKE);
        }
        return request;
    }

    public Pageable transformToPageable(final UserSeekRequest request) {
        Integer pageOffset = request.getPageOffset();
        Integer pageSize = request.getPageSize();
        if (Objects.isNull(pageOffset) || Objects.isNull(pageSize)) {
            return Pageable.unpaged();
        }
        return PageRequest.of(pageOffset, pageSize);
    }
}