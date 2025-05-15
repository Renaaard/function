import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("Выберите тип функции:");
        System.out.println("1 - Линейная функция");
        System.out.println("2 - Квадратичная функция");
        int type = Integer.parseInt(scanner.nextLine().trim());

        if (type == 1) {
            LinearFunction lf = readLinearFunction(scanner);

            while (true) {
                System.out.println("\nВыберите действие:");
                System.out.println("1 - Вывести функцию");
                System.out.println("2 - Вычислить значение в точке");
                System.out.println("3 - Получить градиент");
                System.out.println("4 - Сложить с другой функцией");
                System.out.println("5 - Вычесть другую функцию");
                System.out.println("6 - Умножить на число");
                System.out.println("7 - Завершить");
                int action = Integer.parseInt(scanner.nextLine().trim());
                if (action == 1) {
                    System.out.println("Функция: " + lf);
                } else if (action == 2) {
                    double[] x = readVector(scanner, lf.b.length, "Введите точку (через пробел): ");
                    double value = lf.valueAt(x);
                    System.out.println("Значение функции в точке: " + value);
                } else if (action == 3) {
                    System.out.println("Градиент: " + Arrays.toString(lf.gradient()));
                } else if (action == 4) {
                    System.out.println("Введите вторую линейную функцию:");
                    LinearFunction lf2 = readLinearFunction(scanner);
                    lf = lf.add(lf2);
                    System.out.println("Результат: " + lf);
                } else if (action == 5) {
                    System.out.println("Введите вторую линейную функцию:");
                    LinearFunction lf2 = readLinearFunction(scanner);
                    lf = lf.subtract(lf2);
                    System.out.println("Результат: " + lf);
                } else if (action == 6) {
                    System.out.print("Введите число: ");
                    double scalar = Double.parseDouble(scanner.nextLine().trim());
                    lf = lf.multiply(scalar);
                    System.out.println("Результат: " + lf);
                } else if (action == 7) {
                    System.out.println("Работа завершена.");
                    break;
                } else {
                    System.out.println("Некорректный выбор!");
                }
            }
        } else if (type == 2) {
            QuadraticFunction qf = readQuadraticFunction(scanner);

            while (true) {
                System.out.println("\nВыберите действие:");
                System.out.println("1 - Вывести функцию");
                System.out.println("2 - Вычислить значение в точке");
                System.out.println("3 - Получить градиент в точке");
                System.out.println("4 - Сложить с другой функцией");
                System.out.println("5 - Вычесть другую функцию");
                System.out.println("6 - Умножить на число");
                System.out.println("7 - Завершить");
                int action = Integer.parseInt(scanner.nextLine().trim());
                if (action == 1) {
                    System.out.println("Функция: " + qf);
                } else if (action == 2) {
                    double[] x = readVector(scanner, qf.b.length, "Введите точку (через пробел): ");
                    double value = qf.valueAt(x);
                    System.out.println("Значение функции в точке: " + value);
                } else if (action == 3) {
                    double[] x = readVector(scanner, qf.b.length, "Введите точку (через пробел): ");
                    System.out.println("Градиент в точке: " + Arrays.toString(qf.gradientAt(x)));
                } else if (action == 4) {
                    System.out.println("Введите вторую квадратичную функцию:");
                    QuadraticFunction qf2 = readQuadraticFunction(scanner);
                    qf = qf.add(qf2);
                    System.out.println("Результат: " + qf);
                } else if (action == 5) {
                    System.out.println("Введите вторую квадратичную функцию:");
                    QuadraticFunction qf2 = readQuadraticFunction(scanner);
                    qf = qf.subtract(qf2);
                    System.out.println("Результат: " + qf);
                } else if (action == 6) {
                    System.out.print("Введите число: ");
                    double scalar = Double.parseDouble(scanner.nextLine().trim());
                    qf = qf.multiply(scalar);
                    System.out.println("Результат: " + qf);
                } else if (action == 7) {
                    System.out.println("Работа завершена.");
                    break;
                } else {
                    System.out.println("Некорректный выбор!");
                }
            }
        } else {
            System.out.println("Некорректный тип функции!");
        }
    }

    private static LinearFunction readLinearFunction(Scanner scanner) {
        System.out.print("Введите размерность n: ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        double[] b = readVector(scanner, n, "Введите коэффициенты b (через пробел): ");
        System.out.print("Введите свободный член c: ");
        double c = Double.parseDouble(scanner.nextLine().trim());
        return new LinearFunction(b, c);
    }

    private static QuadraticFunction readQuadraticFunction(Scanner scanner) {
        System.out.print("Введите размерность n: ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        double[][] A = new double[n][n];
        System.out.println("Введите матрицу A (по строкам, элементы через пробел):");
        for (int i = 0; i < n; i++) {
            A[i] = readVector(scanner, n, "Строка " + (i + 1) + ": ");
        }
        double[] b = readVector(scanner, n, "Введите коэффициенты b (через пробел): ");
        System.out.print("Введите свободный член c: ");
        double c = Double.parseDouble(scanner.nextLine().trim());
        return new QuadraticFunction(A, b, c);
    }

    private static double[] readVector(Scanner scanner, int n, String prompt) {
        while (true) {
            System.out.print(prompt);
            String[] tokens = scanner.nextLine().trim().split("\\s+");
            if (tokens.length != n) {
                System.out.println("Ошибка: требуется " + n + " чисел!");
                continue;
            }
            double[] vec = new double[n];
            try {
                for (int i = 0; i < n; i++) {
                    vec[i] = Double.parseDouble(tokens[i]);
                }
                return vec;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: вводите только числа!");
            }
        }
    }
}
