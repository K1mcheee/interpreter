package coffee;

public abstract class BExpr {
    public static class NOT extends BExpr {
        private BExpr arg;

        public NOT(BExpr arg) {
            this.arg = arg;
        }

    }

    public static class AND extends BExpr {
        private BExpr lhs;
        private BExpr rhs;

        public AND(BExpr lhs, BExpr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        BExpr lhs() {
            return this.lhs;
        }
        BExpr rhs() {
            return this.rhs;
        }
    }

    public static class OR extends BExpr {
        private BExpr lhs;
        private BExpr rhs;

        public OR(BExpr lhs, BExpr rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        BExpr lhs() {
            return this.lhs;
        }
        BExpr rhs() {
            return this.rhs;
        }
    }


}
