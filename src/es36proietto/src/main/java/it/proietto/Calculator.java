package it.proietto;

class Calculator {
    public double doOperation(int a, int b, MathOperation op) {
        return op.calculate(a, b);
    }
}