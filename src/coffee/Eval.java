package coffee;

import coffee.Expr.VAL;
import coffee.Expr.ADD;
import coffee.Expr.SUB;
import coffee.Expr.MUL;
import coffee.Expr.DIV;

public final class Eval {
    public static VAL eval(Expr expr) {
        return switch (expr) {
            case ADD a   -> new VAL(eval(a.lhs()).value() + eval(a.rhs()).value());
            case SUB s   -> new VAL(eval(s.lhs()).value() - eval(s.rhs()).value());
            case MUL m   -> new VAL(eval(m.lhs()).value() * eval(m.rhs()).value());
            case DIV d   -> new VAL(eval(d.lhs()).value() / eval(d.rhs()).value());
            case VAL val -> new VAL(val.value());
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
