package by.bsu.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String indexForm(Model model) {
        model.addAttribute("calculator", new CalculatorModel());
        return "index";
    }

    @PostMapping("/index/sum")
    public String sum(@ModelAttribute("calculator") CalculatorModel calculator, Model model) throws Exception {
        model.addAttribute("result", calculator.sum().toString());
        return "sum-result";
    }

    @PostMapping("/index/sub")
    public String sub(@ModelAttribute("calculator") CalculatorModel calculator, Model model) throws Exception {
        model.addAttribute("result", calculator.sub().toString());
        return "sub-result";
    }
}
