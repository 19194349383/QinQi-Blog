//package com.qinqi.utils;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTCreator;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.stereotype.Component;
//
//import java.util.Calendar;
//import java.util.Map;
//
//@Component
//public class JwtUtils {
//
//    public static String createJwt(Map<String,String> map){
//
//        //设置Jwt的超时时间
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE,60);
//        //创建JWT，使用JWT.creat()方法进行创建
//        //此时builder对象中默认设置了header--表头，默认为JWT
//        JWTCreator.Builder builder = JWT.create();
//        //将信息写入有效载荷中payload
//        for (String key:map.keySet()){
//            //通过withClaim方法传入传输到payload中
//            builder.withClaim(key,map.get(key));
//        }
//        //利用builder和签名创健token，同时利用Calender类设置过期时间
//        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256("qinqi"));
//
//    }
//
//    public static String verify(String token,String key){
//
//        //创建解码对象，利用JWT签名去完成解码对象的创建
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("qinqi")).build();
//        //包含了JWT解码信息
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        //利用键得到存放在有效负载payload的数据
//        return decodedJWT.getClaim(key).asString();
//
//    }
//
//
//}
