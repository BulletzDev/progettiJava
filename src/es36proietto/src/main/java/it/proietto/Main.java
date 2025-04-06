package it.proietto;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        double sum = calc.doOperation(5, 3, new MathOperation() {
            @Override
            public double calculate(int a, int b) {
                return a + b;
            }
        });

        System.out.println("Sum (classe anonima): " + sum);

        double sub = calc.doOperation(5, 3, (a, b) -> a - b);
        System.out.println("subtraction (lambda): " + sub);

        System.out.println("multiplication: " + calc.doOperation(5, 3, (a, b) -> a * b));
        System.out.println("division: " + calc.doOperation(10, 2, (a, b) -> a / b));

        Consumer<String> print = message -> System.out.println("message: " + message);
        print.accept("Benvenuto nella Calcolatrice!");

        Supplier<Double> generate = () -> new Random().nextDouble();
        System.out.println("random number: " + generate.get());
    }

}
