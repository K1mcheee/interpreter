import coffee.Eval;
import coffee.Expr;
import coffee.Writer;
import coffee.Expr.ADD;
import coffee.Expr.VAL;
import coffee.Expr.SUB;
import coffee.Expr.MUL;
import coffee.Expr.DIV;

public class Main {
    public static void main(String[] args) {
        Expr expr = new ADD(new MUL(new VAL(4), new VAL(4)), new VAL(3));

        System.out.println(Writer.write(expr));
        System.out.println(Eval.eval(expr).value());
    }
}