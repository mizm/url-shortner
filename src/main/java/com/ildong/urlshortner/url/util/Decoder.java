package com.ildong.urlshortner.url.util;

import com.ildong.urlshortner.url.ui.BadRequestException;

public class Decoder {
    private static String Base62String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static int Base62 = 62;

    private String Encode62(String temp) {
        int id;
        try {
            id = Integer.parseInt(temp);

            StringBuilder sb = new StringBuilder();
            while(id > 0) {
                sb.append(Base62String.charAt((int) (id % Base62)));
                id /= Base62;
            }
            for(int i = sb.length(); i < 6; i++){
                sb.append("a");
            }
            return sb.toString();
        } catch(Exception e) {
            throw new BadRequestException("잘못된 요청입니다.");
        }
    }

    public static long decode62(String temp) {
        long re = 0;
        long power = 1;
//        for (int i = temp.length()-1; i >= 0; i--) {
        for(int i = 0; i < temp.length(); i++) {
            re += Base62String.indexOf(temp.charAt(i)) * power;
            power *= Base62;
        }
        return re;
    }
}
