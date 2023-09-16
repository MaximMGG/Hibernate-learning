package com.maxim.hibernate;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.ProxyConfiguration;
import org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor;

import com.maxim.hibernate.entity.Company;

public class CompanyProxy extends Company implements HibernateProxy, ProxyConfiguration{


    private ByteBuddyInterceptor buddyInterceptor;

    @Override
    public void $$_hibernate_set_interceptor(Interceptor interceptor) {
    }

    @Override
    public Object writeReplace() {
        return buddyInterceptor;
    }

    @Override
    public LazyInitializer getHibernateLazyInitializer() {
        return null;
    }
    
}
