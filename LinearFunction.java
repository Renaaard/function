import java.util.Arrays;

public class LinearFunction {
    protected double[] b; // коэффициенты при x
    protected double c;   // свободный член

    public LinearFunction(double[] b, double c) {
        this.b = Arrays.copyOf(b, b.length);
        this.c = c;
    }

    // Сложение функций
    public LinearFunction add(LinearFunction other) {
        checkDimension(other);
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            resultB[i] = this.b[i] + other.b[i];
        }
        return new LinearFunction(resultB, this.c + other.c);
    }

    // Вычитание функций
    public LinearFunction subtract(LinearFunction other) {
        checkDimension(other);
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            resultB[i] = this.b[i] - other.b[i];
        }
        return new LinearFunction(resultB, this.c - other.c);
    }

    // Умножение на число
    public LinearFunction multiply(double scalar) {
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++) {
            resultB[i] = this.b[i] * scalar;
        }
        return new LinearFunction(resultB, this.c * scalar);
    }

    // Вычисление значения функции в точке
    public double valueAt(double[] x) {
        if (x.length != b.length)
            throw new IllegalArgumentException("Dimension mismatch");
        double sum = c;
        for (int i = 0; i < b.length; i++) {
            sum += b[i] * x[i];
        }
        return sum;
    }

    // Градиент функции (вектор b)
    public double[] gradient() {
        return Arrays.copyOf(b, b.length);
    }

    // Преобразование в строку
    @Override
    public String toString() {
        return "f(x) = " + Arrays.toString(b) + " * x + " + c;
    }

    // Статический метод Parse
    public static LinearFunction parse(String s) {
        // Ожидается формат: f(x) = [b1, b2, ...] * x + c
        String[] parts = s.split("\\* x \\+");
        String bPart = parts[0].replace("f(x) =", "").trim();
        String cPart = parts[1].trim();
        bPart = bPart.replace("[", "").replace("]", "");
        String[] bStrs = bPart.split(",");
        double[] b = new double[bStrs.length];
        for (int i = 0; i < bStrs.length; i++) {
            b[i] = Double.parseDouble(bStrs[i].trim());
        }
        double c = Double.parseDouble(cPart);
        return new LinearFunction(b, c);
    }

    protected void checkDimension(LinearFunction other) {
        if (this.b.length != other.b.length)
            throw new IllegalArgumentException("Dimension mismatch");
    }
}
