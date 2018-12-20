package com.Util;

import java.util.UUID;

public class CodeGenerateUtil {
    public static String generateUniqueCode(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
