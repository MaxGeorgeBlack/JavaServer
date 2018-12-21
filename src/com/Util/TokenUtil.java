package com.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class TokenUtil {
    private static final long EXPIRE_TIME = 365 * 24 * 60 * 60 * 1000L;
    private static final String TOKEN_SECRET = "UpfXcqnahyP0ID4L";

    public static String sign(String id){
        try {
            long currentTime = System.currentTimeMillis() + EXPIRE_TIME;
            Date date = new Date(currentTime);
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");

            return JWT.create()
                    .withHeader(header)
                    .withClaim("Id", id)
                    .withExpiresAt(date)
                    .sign(algorithm) + " " + String.valueOf(currentTime);
        }catch (Exception exception){
            return null;
        }
    }

}
