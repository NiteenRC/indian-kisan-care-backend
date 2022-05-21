package com.nc.med.filter;

import com.nc.med.model.Period;
import com.nc.med.model.Subscription;
import com.nc.med.repo.SubscriptionRepo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@AllArgsConstructor
public class MyFilter extends OncePerRequestFilter {
    private SubscriptionRepo subscriptionRepo;

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        //String expiryDateString = "03-Apr-2023";
        //DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        //Date expiryDate = formatter.parse(expiryDateString);

        Date now = new Date();
        Optional<Subscription> subscription = subscriptionRepo.findById(1L);
        if (subscription.isPresent() && now.before(subscription.get().getEndAt())) {
            filterChain.doFilter(request, response);
        } else {
            if (subscriptionRepo.findAll().isEmpty()) {
                saveSubscription();
            } else {
                //response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
                throw new RuntimeException("Application expired. Please contact Admin");
            }
        }
    }

    private void saveSubscription() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setPeriod(Period.MONTHS_3);
        subscription.setCreatedAt(new Date());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 3);
        Date newDate = c.getTime();
        subscription.setEndAt(newDate);
        subscriptionRepo.save(subscription);
    }
}