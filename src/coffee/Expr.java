package coffee;

public abstract class Expr {
    public static class ADD extends Expr {
        private final Expr lhs;
        private final Expr rhs;

        public ADD(Expr lhs, Expr rhs) {
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

    public static class SUB extends Expr {
        private final Expr lhs;
        private final Expr rhs;

        public SUB(Expr lhs, Expr rhs) {
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

    public static class MUL extends Expr {
        private final Expr lhs;
        private final Expr rhs;

        public MUL(Expr lhs, Expr rhs) {
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

    public static class DIV extends Expr {
        private final Expr lhs;
        private final Expr rhs;

        public DIV(Expr lhs, Expr rhs) {
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

    public static class VAL extends Expr {
        int value;

        public VAL(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
