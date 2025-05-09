package coffee;

import coffee.Expr.*;

public final class Eval {
    public static VAL eval(Expr expr, Env env) {
        return switch (expr) {
            case ADD a    -> new VAL(eval(a.lhs(), env).value() + eval(a.rhs(), env).value());
            case SUB s    -> new VAL(eval(s.lhs(), env).value() - eval(s.rhs(), env).value());
            case MUL m    -> new VAL(eval(m.lhs(), env).value() * eval(m.rhs(), env).value());
            case DIV d    -> new VAL(eval(d.lhs(), env).value() / eval(d.rhs(), env).value());
            case VAL val  -> new VAL(val.value());
            case VAR name -> env.getv(new VAR(name.name()));
            default       -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
