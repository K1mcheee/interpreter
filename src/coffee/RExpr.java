package coffee;

public abstract class RExpr extends BExpr{
    public static class EQS extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public EQS(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class NEQ extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public NEQ(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class LEQ extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public LEQ(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class LTE extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public LTE(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class GEQ extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public GEQ(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }

    public static class GTE extends RExpr {
        private Expr lhs;
        private Expr rhs;

        public GTE(Expr lhs, Expr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        Expr lhs() {
            return this.lhs;
        }
        Expr rhs() {
            return this.rhs;
        }
    }
}
