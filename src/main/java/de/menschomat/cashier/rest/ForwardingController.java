package de.menschomat.cashier.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {
    @SuppressWarnings("MVCPathVariableInspection")
    @RequestMapping("/**/{path:[^.]+}")
    public String forward() {
        return "forward:/";
    }
}