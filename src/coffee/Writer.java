package coffee;

import coffee.Expr.VAL;
import coffee.Expr.ADD;
import coffee.Expr.SUB;
import coffee.Expr.MUL;
import coffee.Expr.DIV;

public class Writer {
    public static String write(Expr expr) {
        return switch(expr) {
            case ADD a   -> "(" + write(a.lhs()) + " + " + write(a.rhs()) + ")";
            case SUB s   -> "(" + write(s.lhs()) + " - " + write(s.rhs()) + ")";
            case MUL m   -> "(" + write(m.lhs()) + " * " + write(m.rhs()) + ")";
            case DIV d   -> "(" + write(d.lhs()) + " / " + write(d.rhs()) + ")";
            case VAL val -> "" + val.value();
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
