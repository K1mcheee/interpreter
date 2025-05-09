package coffee;

import coffee.Expr.*;

public class Writer {
    public static String write(Expr expr) {
        return switch(expr) {
            case ADD a    -> "(" + write(a.lhs()) + " + " + write(a.rhs()) + ")";
            case SUB s    -> "(" + write(s.lhs()) + " - " + write(s.rhs()) + ")";
            case MUL m    -> "(" + write(m.lhs()) + " * " + write(m.rhs()) + ")";
            case DIV d    -> "(" + write(d.lhs()) + " / " + write(d.rhs()) + ")";
            case VAL val  -> "" + val.value();
            case VAR name -> name.name();
            default       -> throw new IllegalArgumentException("unidentified variable");
        };
    }

    public static String write(BExpr expr) {
        return switch(expr) {
            case BExpr.NOT n -> "(!" + write(n) + ")";
            case BExpr.AND a -> "(" + write(a.lhs()) + " && " + write(a.rhs()) + ")";
            case BExpr.OR o  -> "(" + write(o.lhs()) + " || " + write(o.rhs()) + ")";
            default    -> {
                if (expr instanceof RExpr rexpr) {
                    yield write(rexpr);
                } else {
                    throw new IllegalArgumentException("unidentified variable");
                }
            }
        };
    }

    public static String write(RExpr expr) {
        return switch(expr) {
            case RExpr.EQS eqs -> "(" + write(eqs.lhs()) + " == " + write(eqs.rhs()) + ")";
            case RExpr.NEQ neq -> "(" + write(neq.lhs()) + " != " + write(neq.rhs()) + ")";
            case RExpr.LEQ leq -> "(" + write(leq.lhs()) + " < " + write(leq.rhs()) + ")";
            case RExpr.LTE lte -> "(" + write(lte.lhs()) + " <= " + write(lte.rhs()) + ")";
            case RExpr.GEQ geq -> "(" + write(geq.lhs()) + " > " + write(geq.rhs()) + ")";
            case RExpr.GTE gte -> "(" + write(gte.lhs()) + " >= " + write(gte.rhs()) + ")";
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
