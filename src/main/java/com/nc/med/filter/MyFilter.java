package com.nc.med.filter;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String expiryDateString = "03-Apr-2023";
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date expiryDate = formatter.parse(expiryDateString);

        Date now = new Date();
        if (now.before(expiryDate)) {
            filterChain.doFilter(request, response);
        } else {
            throw new RuntimeException("Application expired. Please contact Admin");
        }
    }
}