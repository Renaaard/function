import java.util.Arrays;

public class QuadraticFunction extends LinearFunction {
    private double[][] A; // Матрица квадратичных коэффициентов

    public QuadraticFunction(double[][] A, double[] b, double c) {
        super(b, c);
        // Проверка размерности
        if (A.length != b.length || A[0].length != b.length)
            throw new IllegalArgumentException("Matrix A must be n x n, where n is the dimension of b");
        this.A = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++)
            this.A[i] = Arrays.copyOf(A[i], A[i].length);
    }

    // Сложение функций
    public QuadraticFunction add(QuadraticFunction other) {
        checkDimension(other);
        double[][] resultA = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                resultA[i][j] = this.A[i][j] + other.A[i][j];
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++)
            resultB[i] = this.b[i] + other.b[i];
        return new QuadraticFunction(resultA, resultB, this.c + other.c);
    }

    // Вычитание функций
    public QuadraticFunction subtract(QuadraticFunction other) {
        checkDimension(other);
        double[][] resultA = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                resultA[i][j] = this.A[i][j] - other.A[i][j];
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++)
            resultB[i] = this.b[i] - other.b[i];
        return new QuadraticFunction(resultA, resultB, this.c - other.c);
    }

    // Умножение на число
    public QuadraticFunction multiply(double scalar) {
        double[][] resultA = new double[A.length][A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A.length; j++)
                resultA[i][j] = this.A[i][j] * scalar;
        double[] resultB = new double[b.length];
        for (int i = 0; i < b.length; i++)
            resultB[i] = this.b[i] * scalar;
        return new QuadraticFunction(resultA, resultB, this.c * scalar);
    }

    // Вычисление значения функции в точке
    @Override
    public double valueAt(double[] x) {
        if (x.length != b.length)
            throw new IllegalArgumentException("Dimension mismatch");
        double quad = 0.0;
        for (int i = 0; i < x.length; i++)
            for (int j = 0; j < x.length; j++)
                quad += x[i] * A[i][j] * x[j];
        double linear = 0.0;
        for (int i = 0; i < b.length; i++)
            linear += b[i] * x[i];
        return quad + linear + c;
    }

    // Градиент функции
    @Override
    public double[] gradient() {
        // Градиент квадратичной функции: grad = 2Ax + b
        // Для произвольной точки x, но если не задана точка, возвращаем b (как для линейной)
        return Arrays.copyOf(b, b.length);
    }

    public double[] gradientAt(double[] x) {
        if (x.length != b.length)
            throw new IllegalArgumentException("Dimension mismatch");
        double[] grad = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            grad[i] = b[i];
            for (int j = 0; j < x.length; j++)
                grad[i] += 2 * A[i][j] * x[j];
        }
        return grad;
    }

    // Преобразование в строку
    @Override
    public String toString() {
        return "f(x) = x^T * " + Arrays.deepToString(A) + " * x + " + Arrays.toString(b) + " * x + " + c;
    }

    // Статический метод Parse
    public static QuadraticFunction parse(String s) {
        // Ожидается формат: f(x) = x^T * [[...], [...], ...] * x + [b1, b2, ...] * x + c
        String[] parts = s.split("\\* x \\+");
        String aPart = parts[0].replace("f(x) = x^T *", "").trim();
        String bPart = parts[1].trim();
        String cPart = parts[2].trim();

        aPart = aPart.replace("[[", "").replace("]]", "");
        String[] aRows = aPart.split("\\], \\[");
        double[][] A = new double[aRows.length][];
        for (int i = 0; i < aRows.length; i++) {
            String[] aElems = aRows[i].replace("[", "").replace("]", "").split(",");
            A[i] = new double[aElems.length];
            for (int j = 0; j < aElems.length; j++) {
                A[i][j] = Double.parseDouble(aElems[j].trim());
            }
        }

        bPart = bPart.replace("[", "").replace("]", "");
        String[] bStrs = bPart.split(",");
        double[] b = new double[bStrs.length];
        for (int i = 0; i < bStrs.length; i++) {
            b[i] = Double.parseDouble(bStrs[i].trim());
        }
        double c = Double.parseDouble(cPart);
        return new QuadraticFunction(A, b, c);
    }

    protected void checkDimension(QuadraticFunction other) {
        if (this.b.length != other.b.length || this.A.length != other.A.length)
            throw new IllegalArgumentException("Dimension mismatch");
    }
}
