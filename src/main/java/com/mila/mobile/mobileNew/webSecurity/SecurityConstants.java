package com.mila.mobile.mobileNew.webSecurity;

import com.mila.mobile.mobileNew.SpringApplicationContext;
import org.springframework.core.env.Environment;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static String getTokenSecret()
    {
        Environment appProperties = (Environment) SpringApplicationContext.getBean("environment");
       return appProperties.getProperty("tokenSecret");
    }
}
