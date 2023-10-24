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
        model.addAttribute("result", calculator.sum());
        return "sum-result";
    }

    @PostMapping("/index/sub")
    public String sub(@ModelAttribute("calculator") CalculatorModel calculator, Model model) throws Exception {
        model.addAttribute("result", calculator.sub());
        return "sub-result";
    }

    @PostMapping("/index/mul")
    public String mul(@ModelAttribute("calculator") CalculatorModel calculator, Model model) throws Exception {
        model.addAttribute("result", calculator.mul());
        return "mul-result";
    }

    @PostMapping("/index/divide")
    public String divide(@ModelAttribute("calculator") CalculatorModel calculator, Model model) throws Exception {
        model.addAttribute("result", calculator.divide());
        return "div-result";
    }
}
