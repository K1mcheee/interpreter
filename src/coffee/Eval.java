package coffee;

import coffee.BExpr.*;
import coffee.Expr.*;
import coffee.RExpr.*;

public final class Eval {
    public static VAL eval(Expr expr, Env env) {
        return switch (expr) {
            case ADD a    -> new VAL(eval(a.lhs(), env).value() + eval(a.rhs(), env).value());
            case SUB s    -> new VAL(eval(s.lhs(), env).value() - eval(s.rhs(), env).value());
            case MUL m    -> new VAL(eval(m.lhs(), env).value() * eval(m.rhs(), env).value());
            case DIV d    -> new VAL(eval(d.lhs(), env).value() / eval(d.rhs(), env).value());
            case VAL val  -> new VAL(val.value());
            case VAR name -> env.getv(new VAR(name.name()));
            default       -> throw new IllegalArgumentException("Unidentified variable");
        };
    }

    public static boolean eval(BExpr expr, Env env) {
        return switch(expr) {
            case NOT n -> !eval(n, env);
            case AND a -> eval(a.lhs(),env) && eval(a.rhs(), env);
            case OR o  -> eval(o.lhs(),env) || eval(o.rhs(), env);
            default    -> {
                if (expr instanceof RExpr rexpr) {
                    yield eval(rexpr, env);
                } else {
                    throw new IllegalArgumentException("unidentified variable");
                }
            }
        };
    }

    public static boolean eval(RExpr expr, Env env) {
        return switch(expr) {
            case EQS eqs -> eval(eqs.lhs(), env).value() == eval(eqs.rhs(), env).value();
            case NEQ neq -> eval(neq.lhs(), env).value() != eval(neq.rhs(), env).value();
            case LEQ leq -> eval(leq.lhs(), env).value() < eval(leq.rhs(), env).value();
            case LTE lte -> eval(lte.lhs(), env).value() <= eval(lte.rhs(), env).value();
            case GEQ geq -> eval(geq.lhs(), env).value() > eval(geq.rhs(), env).value();
            case GTE gte -> eval(gte.lhs(), env).value() >= eval(gte.rhs(), env).value();
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
