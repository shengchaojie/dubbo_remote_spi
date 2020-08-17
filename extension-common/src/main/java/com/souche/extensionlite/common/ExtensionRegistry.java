package com.souche.extensionlite.common;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;


/**
 * @author shengchaojie
 * @date 2020/8/17
 **/
public class ExtensionRegistry {

    @Getter
    public static final ExtensionRegistry INSTANCE = new ExtensionRegistry();

    private Table<String,String,Object> table = HashBasedTable.create();

    public void registry(String interfaceName, String bizCode, Object bean){
        table.put(interfaceName,bizCode,bean);
    }

    public void registry(Class<?> interfaceClass, String bizCode, Object bean){
        table.put(interfaceClass.getName(),bizCode,bean);
    }

    public Object get(String interfaceName, String bizCode){
        return table.get(interfaceName,bizCode);
    }

    public <T> T get(Class<T> interfaceClass, String bizCode){
        return (T)table.get(interfaceClass.getName(),bizCode);
    }

}
