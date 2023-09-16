package com.maxim.hibernate;

import java.lang.reflect.Proxy;

import org.junit.jupiter.api.Test;

import com.maxim.hibernate.entity.Company;

public class ProxyTest {
    
    @Test
    void testDynamic() {
        Company company = new Company();
        Proxy.newProxyInstance(company.getClass().getClassLoader(),
        company.getClass().getInterfaces(), (proxy, method, args) -> method.invoke(company, args));
    }
}
