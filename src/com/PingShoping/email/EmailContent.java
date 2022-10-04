package com.PingShoping.email;

public class EmailContent {
    public String code;
    public String getContent(){

        RandomUtil rd = new RandomUtil();
        code =  rd.getRandom();
        String  cont = "验证码：" +code;
        return cont;
    }

}
