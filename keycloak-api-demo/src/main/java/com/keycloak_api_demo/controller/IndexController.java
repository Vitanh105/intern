package com.keycloak_api_demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(path = "/")
    public HashMap index() {
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return new HashMap() {{
            put("Hello", user.getAttribute("name"));
            put("Your email is", user.getAttribute("email"));
        }};
    }

    @PostMapping(path = "/sso-logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap logout(@RequestParam("logout_token") String logoutToken) throws Exception {

        String[] splitString = logoutToken.split("\\.");
        String body = new String(java.util.Base64.getDecoder().decode(splitString[1]));

        ObjectMapper mapper = new ObjectMapper();
        HashMap bodyMap = mapper.readValue(body, HashMap.class);

        logger.debug("logging out {}", bodyMap.get("sid"));

        jdbcTemplate.update("DELETE FROM `spring_session` " +
                        "WHERE `PRIMARY_ID` = (SELECT `PRIMARY_ID` FROM `spring_session_attributes` WHERE `ATTRIBUTE_NAME` = ?)",
                new Object[] { bodyMap.get("sid") });

        return new HashMap(){{
            put("status", true);
        }};
    }
}