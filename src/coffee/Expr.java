package coffee;

import java.util.List;

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

    public static class LIT extends Expr{
        public String string() {
            return "";
        }

        public int value() {
            return 0;
        }
    }

    public static class STR extends LIT {
        String string;

        public STR(String string) {
            this.string = string;
        }

        @Override
        public String string() {
            return this.string;
        }

        @Override
        public String toString() {
            return this.string;
        }
    }

    public static class VAL extends LIT {
        int value;

        public VAL(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return this.value;
        }

        @Override
        public String toString() {
            return "" + this.value;
        }
    }

    public static class VAR extends Expr {
        private String name;

        public VAR(String name) {
            this.name = name;
        }

        public String name() {
            return this.name;
        }
    }

    public static class CALL extends Expr {
        private String name;
        private List<Expr> args;

        public CALL(String name, List<Expr> args) {
            this.name = name;
            this.args = args;
        }

        public String name() {
            return this.name;
        }
        public List<Expr> args() {
            return this.args;
        }
    }

}
