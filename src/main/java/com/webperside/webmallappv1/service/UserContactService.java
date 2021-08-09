package com.webperside.webmallappv1.service;

import com.webperside.webmallappv1.dto.user.UserContactDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserContactService {

    List<UserContactDto> getUserContact(HttpServletRequest req);
}
