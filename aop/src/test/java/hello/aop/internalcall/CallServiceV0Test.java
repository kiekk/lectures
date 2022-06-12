package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callServiceV0;

    @Test
    void external() {
        callServiceV0.external();
        /*
        external 메소드는 aop 적용이 되지만,
        external 메소드의 내부에서 호출하는
        internal 메소드는 aop 적용 X
         */
    }

    @Test
    void internal() {
        // proxy 를 통해 호출하는 internal 메소드의 경우 aop 적용 O 
        callServiceV0.internal();
    }

}