package hello.proxy.app.v5;

import hello.proxy.trace.callback.TraceTemplate;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final TraceTemplate traceTemplate;
    private final OrderRepositoryV5 orderRepositoryV5;

    public OrderServiceV5(LogTrace trace, OrderRepositoryV5 orderRepositoryV5) {
        this.traceTemplate = new TraceTemplate(trace);
        this.orderRepositoryV5 = orderRepositoryV5;
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderService.orderItem()", () -> {
            orderRepositoryV5.save(itemId);
            return null;
        });
    }

}
