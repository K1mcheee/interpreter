package coffee;

import coffee.BExpr.*;
import coffee.Expr.*;
import coffee.RExpr.*;
import coffee.Stmt.*;

public final class Eval {
    public static VAL eval(Expr expr, Env env, Env global) {
        return switch (expr) {
            case ADD a    -> new VAL(eval(a.lhs(), env, global).value() + eval(a.rhs(), env, global).value());
            case SUB s    -> new VAL(eval(s.lhs(), env, global).value() - eval(s.rhs(), env, global).value());
            case MUL m    -> new VAL(eval(m.lhs(), env, global).value() * eval(m.rhs(), env, global).value());
            case DIV d    -> new VAL(eval(d.lhs(), env, global).value() / eval(d.rhs(), env, global).value());
            case VAL val  -> new VAL(val.value());
            case VAR name -> env.getv(new VAR(name.name()));
            default       -> throw new IllegalArgumentException("Unidentified variable");
        };
    }

    public static boolean eval(BExpr expr, Env env, Env global) {
        return switch(expr) {
            case NOT n -> !eval(n, env, global);
            case AND a -> eval(a.lhs(),env, global) && eval(a.rhs(), env, global);
            case OR o  -> eval(o.lhs(),env, global) || eval(o.rhs(), env, global);
            default    -> {
                if (expr instanceof RExpr rexpr) {
                    yield eval(rexpr, env, global);
                } else {
                    throw new IllegalArgumentException("unidentified variable");
                }
            }
        };
    }

    public static boolean eval(RExpr expr, Env env, Env global) {
        return switch(expr) {
            case EQS eqs -> eval(eqs.lhs(), env, global).value() == eval(eqs.rhs(), env, global).value();
            case NEQ neq -> eval(neq.lhs(), env, global).value() != eval(neq.rhs(), env, global).value();
            case LEQ leq -> eval(leq.lhs(), env, global).value() < eval(leq.rhs(), env, global).value();
            case LTE lte -> eval(lte.lhs(), env, global).value() <= eval(lte.rhs(), env, global).value();
            case GEQ geq -> eval(geq.lhs(), env, global).value() > eval(geq.rhs(), env, global).value();
            case GTE gte -> eval(gte.lhs(), env, global).value() >= eval(gte.rhs(), env, global).value();
            default      -> throw new IllegalArgumentException("unidentified variable");
        };
    }

    public static Env eval(Stmt stmt, Env env, Env global) {
        return switch(stmt) {
            case ASSG a -> {
                env.assg(a.lhs(), eval(a.rhs(), env, global));
                yield env;
            }
            case DECL d -> {
                env.decl(d.lhs());
                env.assg(d.lhs(), eval(d.rhs(), env, global));
                yield env;
            }
            case IF i -> {
                if (eval(i.cond(), env, global)) {
                    eval(i.thenStmt(), env, global);
                } else {
                    eval(i.elseStmt(), env, global);
                }
                yield env;
            }
            case WHILE w -> {
                while(eval(w.cond(), env, global)) {
                    eval(w.loop(), env, global);
                }
                yield env;
            }
            case BLOCK b -> {
                for (Stmt stmts : b.stmts()) {
                    eval(stmts, env, global);
                }
                yield env;
            }
            case FUNC f -> {
                global.func(f.name(), f.pars(), f.body());
                yield env;
            }
            case RET r -> {
                env.setRet(eval(r.expr(), env, global));
                yield env;
            }
            default -> throw new IllegalArgumentException("unidentified variable");
        };
    }
}
