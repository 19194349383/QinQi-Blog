package com.qinqi.utils;

import java.util.UUID;

public class UUid {

    public static Long getUUid() {

        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return Long.valueOf(String.format("%010d", hashCode).substring(0, 10));
    }

}
