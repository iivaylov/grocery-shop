package com.groceryshop.groceryshop.utils;

import com.groceryshop.groceryshop.exceptions.GroceryAuthorizationException;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthenticationHelper {
    public static final String USER_INFORMATION_IS_MISSING = "User information is missing in the authorization header";
    public static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    public Credentials tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new GroceryAuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);

        if (userInfo == null) {
            throw new GroceryAuthorizationException(USER_INFORMATION_IS_MISSING);
        }

        String username = getUsername(userInfo);
        String password = getPassword(userInfo);
        return new Credentials(username, password);
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new GroceryAuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new GroceryAuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

    public record Credentials(String username, String password) {
    }
}
