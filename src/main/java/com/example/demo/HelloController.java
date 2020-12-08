package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello from Spring!";
    }

    @GetMapping("/tasks")
    public String getTasks() {
        return "These are tasks";
    }

    @PostMapping("/tasks")
    public String createTask() {
        return "You just POSTed to /tasks";
    }

    @GetMapping("/math/pi")
    public String getPi() {
        return "3.141592653589793";
    }

    @GetMapping("/math/calculate")
    public String calc(@RequestParam(required = false, defaultValue = "add") String operation, @RequestParam String x, @RequestParam String y) {
        int xNum = Integer.parseInt(x);
        int yNum = Integer.parseInt(y);
        if(operation.equals("add")) return x + " + " + y + " = " + Integer.toString(xNum+yNum);
        else if(operation.equals("subtract")) return x + " - " + y + " = " + Integer.toString(xNum-yNum);
        else if(operation.equals("multiply")) return x + " * " + y + " = " + Integer.toString(xNum*yNum);
        else return x + " / " + y + " = " + Integer.toString(xNum/yNum);
    }

    @GetMapping("/math/calculate_class")
    public String calcRefactor(MathService mathService) {
        return mathService.calculate();
    }

    @PostMapping("/math/sum")
    public String sum(@RequestParam String[] n) {
        int sum = 0;
        for(String num : n) {
            sum += Integer.parseInt(num);
        }

        return Integer.toString(sum);
    }

    @PostMapping("/math/sum_class")
    public String sumRefactor(MathService mathService) {
        return mathService.sum();
    }

}