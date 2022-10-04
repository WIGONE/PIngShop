package com.PingShoping.email;

import javax.mail.MessagingException;

public class Test {
    public static void main(String[] args) throws MessagingException {
        Email  ml = new Email();
        ml.send("564917287@qq.com");
    }
}
