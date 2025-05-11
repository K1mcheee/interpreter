package coffee;

import java.util.List;

import coffee.Expr.*;

public abstract class Stmt {
    public static class ASSG extends Stmt {
        private VAR lhs;
        private Expr rhs;

        public ASSG(VAR lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        VAR lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class DECL extends Stmt {
        private VAR lhs;
        private Expr rhs;

        public DECL(VAR lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        VAR lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class IF extends Stmt {
        private BExpr cond;
        private Stmt thenStmt;
        private Stmt elseStmt;

        public IF(BExpr cond, Stmt thenStmt, Stmt elseStmt) {
            this.cond = cond;
            this.thenStmt = thenStmt;
            this.elseStmt = elseStmt;
        }

        public BExpr cond() {
            return this.cond;
        }

        public Stmt thenStmt() {
            return this.thenStmt;
        }

        public Stmt elseStmt() {
            return this.elseStmt;
        }
    }

    public static class WHILE extends Stmt {
        private BExpr cond;
        private Stmt loop;

        public WHILE(BExpr cond, Stmt loop) {
            this.cond = cond;
            this.loop = loop;
        }

        public BExpr cond() {
            return this.cond;
        }

        public Stmt loop() {
            return this.loop;
        }
    }

    public static class BLOCK extends Stmt {
        private List<Stmt> stmts;

        public BLOCK(List<Stmt> stmts) {
            this.stmts = stmts;
        }

        public List<Stmt> stmts() {
            return this.stmts;
        }

    }

    public static class FUNC extends Stmt {
        private String name;
        private List<VAR> pars;
        private Stmt body;

        public FUNC(String name, List<VAR> pars, Stmt body) {
            this.name = name;
            this.pars = pars;
            this.body = body;
        }

        public String name() {
            return this.name;
        }
        public List<VAR> pars() {
            return this.pars;
        }
        public Stmt body() {
            return this.body;
        }
    }

    public static class RET extends Stmt {
        private Expr expr;

        public RET(Expr expr) {
            this.expr = expr;
        }

        public Expr expr() {
            return this.expr;
        }
    }

    public static class PRINT extends Stmt {
        private String name;

        public PRINT(String name) {
            this.name = name;
        }

        public String name() {
            return this.name;
        }
    }

    public static class SHOW extends Stmt {
        private Expr expr;

        public SHOW(Expr expr) {
            this.expr = expr;
        }

        public Expr expr() {
            return this.expr;
        }
    }
}
