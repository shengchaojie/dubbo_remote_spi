package com.souche.extensionlite.business;

public class BusinessContext {
    private static ThreadLocal<String> bizCodeHolder = new ThreadLocal<>();

    public static void setBizCode(String bizCode) {
        bizCodeHolder.set(bizCode);
    }

    public static String getBizCode() {
        return bizCodeHolder.get();
    }

    public static void clear(){
        bizCodeHolder.remove();
    }

}
