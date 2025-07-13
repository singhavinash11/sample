package org.singhav.sample.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig {

    /*@Bean
    public TaskExecutor asyncExecutor() {
        //return new ConcurrentTaskExecutor(Executors.newVirtualThreadPerTaskExecutor());
        ThreadFactory threadFactory = Thread.ofVirtual()
                .name("AsyncThread-", 0)
                .factory();
        return new ConcurrentTaskExecutor(Executors.newThreadPerTaskExecutor(threadFactory));
    }*/

}
