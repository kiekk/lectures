package io.security.corespringsecurity.security.aop;

import org.springframework.stereotype.Service;

@Service
public class AopLiveMethodService {

    public void liveMethodSecured(){

        System.out.println("liveMethodSecured");
    }
}