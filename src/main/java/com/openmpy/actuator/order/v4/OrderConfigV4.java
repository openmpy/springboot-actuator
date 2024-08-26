package com.openmpy.actuator.order.v4;

import com.openmpy.actuator.order.OrderService;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV4 {

    @Bean
    OrderService orderService() {
        return new OrderServiceV4();
    }

    @Bean
    public TimedAspect countedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
