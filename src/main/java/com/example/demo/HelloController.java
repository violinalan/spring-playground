package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @RequestMapping("/math/volume/{length}/{width}/{height}")
    public String volume(@PathVariable String length, @PathVariable String width, @PathVariable String height) {
        int lengthNum = Integer.parseInt(length);
        int widthNum = Integer.parseInt(width);
        int heightNum = Integer.parseInt(height);

        return "The volume of a " + length + "x" + width + "x" + height + " rectangle is " + Integer.toString(lengthNum*widthNum*heightNum);
    }

    @PostMapping("/math/area")
    public String area(@RequestParam String type, @RequestParam Map<String, String> body) {
        if(type.equals("circle")) {
            if(!body.containsKey("radius")) {
                return "Invalid";
            } else {
                int radius = Integer.parseInt(body.get("radius"));
                return "Area of a circle with a radius of " + radius + " is " + Double.toString(3.14159 * radius * radius);
            }
        }
        else if(type.equals("rectangle")) {
            if(!body.containsKey("width") || !body.containsKey("height")) {
                return "Invalid";
            } else {
                int width = Integer.parseInt(body.get("width"));
                int height = Integer.parseInt(body.get("height"));
                return "Area of a " + width + "x" + height + " rectangle is " + Integer.toString(width * height);
            }
        }
        else return "Invalid";
    }

}