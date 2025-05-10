package coffee;

import coffee.BExpr.*;
import coffee.Expr.*;
import coffee.RExpr.*;
import coffee.Stmt.*;

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
            case NOT n -> "(!" + write(n) + ")";
            case AND a -> "(" + write(a.lhs()) + " && " + write(a.rhs()) + ")";
            case OR o  -> "(" + write(o.lhs()) + " || " + write(o.rhs()) + ")";
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
            case EQS eqs -> "(" + write(eqs.lhs()) + " == " + write(eqs.rhs()) + ")";
            case NEQ neq -> "(" + write(neq.lhs()) + " != " + write(neq.rhs()) + ")";
            case LEQ leq -> "(" + write(leq.lhs()) + " < " + write(leq.rhs()) + ")";
            case LTE lte -> "(" + write(lte.lhs()) + " <= " + write(lte.rhs()) + ")";
            case GEQ geq -> "(" + write(geq.lhs()) + " > " + write(geq.rhs()) + ")";
            case GTE gte -> "(" + write(gte.lhs()) + " >= " + write(gte.rhs()) + ")";
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }

    public static String write(Stmt stmt, String tabs) {
        return switch(stmt) {
            case DECL d  -> tabs + "int "  + write(d.lhs()) + " = " + write(d.rhs()) + ";\n";
            case ASSG a  -> tabs           + write(a.lhs()) + " = " + write(a.rhs()) + ";\n";
            case IF   i  -> tabs + "if"    + write(i.cond()) + " {\n"     +
                                    write(i.thenStmt(), tabs + "  ") +
                            tabs + "} else {\n"                           +
                                    write(i.elseStmt(), tabs + "  ") +
                            tabs + "}\n";
            case WHILE w -> tabs + "while" + write(w.cond()) + " {\n"     +
                                    write(w.loop(), tabs + "  ")     +
                            tabs + "}\n";
            case BLOCK b -> b.stmts().stream().map(s -> write(s, tabs))
                    .reduce("", (x, y) -> x + y);
            case FUNC f  ->  tabs + "def " + f.name() + "("                                +
                             f.pars().stream().map(par -> write(par))
                             .reduce(", ", (x, y) -> x + y) + ") {\n"  +
                                     write(f.body(), tabs + "  ")                     +
                             tabs + "}\n";
            case RET  r -> tabs + "return " + write(r.expr()) +  ";\n";
            default     -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
