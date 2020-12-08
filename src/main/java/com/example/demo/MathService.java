package com.example.demo;

public class MathService {
    private String operation = "add";
    private String x;
    private String y;

    private String[] n;

    public String getOperation() { return this.operation; }

    public void setOperation(String operationIn) { this.operation = operationIn; }

    public String getX() { return this.x; }

    public void setX(String xIn) { this.x = xIn; }

    public String getY() { return this.y; }

    public void setY(String xIn) { this.y = xIn; }

    public String[] getN() { return this.n; }

    public void setN(String[] nIn) { this.n = nIn; }

    public String calculate() {
        int xNum = Integer.parseInt(x);
        int yNum = Integer.parseInt(y);
        if(operation.equals("add")) return x + " + " + y + " = " + Integer.toString(xNum+yNum);
        else if(operation.equals("subtract")) return x + " - " + y + " = " + Integer.toString(xNum-yNum);
        else if(operation.equals("multiply")) return x + " * " + y + " = " + Integer.toString(xNum*yNum);
        else return x + " / " + y + " = " + Integer.toString(xNum/yNum);
    }

    public String sum() {
        int sum = 0;
        for(String num : n) {
            sum += Integer.parseInt(num);
        }

        return Integer.toString(sum);
    }
}
