package com.nc.med.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class HostConfiguration {

    @PostConstruct
    public void hosting() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (!"Niteens-Air".equals(localHost.getHostName()) && !"192.168.43.211".equals(localHost.getHostAddress())) {
            //System.exit(0);
        }
    }
}
