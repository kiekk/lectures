package io.security.corespringsecurity.security.aop;

import io.security.corespringsecurity.domain.dto.AccountDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AopSecurityController {

    private final AopMethodService aopMethodService;

    private final AopPointcutService aopPointcutService;

    private final AopLiveMethodService aopLiveMethodService;

    public AopSecurityController(AopMethodService aopMethodService, AopPointcutService aopPointcutService, AopLiveMethodService aopLiveMethodService) {
        this.aopMethodService = aopMethodService;
        this.aopPointcutService = aopPointcutService;
        this.aopLiveMethodService = aopLiveMethodService;
    }

    @GetMapping("/pre-authorize")
    @PreAuthorize("hasRole('ROLE_USER') and #account.username == principal.username")
    public String preAuthorize(AccountDto account, Model model, Principal principal) {

        model.addAttribute("method", "Success @PreAuthorize");

        return "aop/method";
    }

    @GetMapping("/method-secured")
    public String methodSecured(Model model) {
        aopMethodService.methodSecured();

        model.addAttribute("method", "Success MethodSecured");

        return "aop/method";
    }

    @GetMapping("/pointcut-secured")
    public String pointcutSecured(Model model) {

        aopPointcutService.notSecured();
        aopPointcutService.pointcutSecured();
        model.addAttribute("method", "Success PointcutSecured");

        return "aop/method";
    }

    @GetMapping("/live-method-secured")
    public String liveMethodSecured(Model model) {

        aopLiveMethodService.liveMethodSecured();
        model.addAttribute("method", "Success LiveMethodSecured");
        return "aop/method";
    }
}
