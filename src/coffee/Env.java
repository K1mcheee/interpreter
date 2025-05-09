package coffee;

import coffee.Expr.*;

import java.util.HashMap;

public class Env {
    private Env global;
    private HashMap<String, VAL> vars = new HashMap<>();

    public Env(Env global) {
        this.global = global;
    }

    public void decl(VAR name) {
        this.vars.put(name.name(), new VAL(0));
    }

    public void assg(VAR name, VAL value) {
        this.vars.put(name.name(), value);
    }

    public VAL getv(VAR name) {
        return this.vars.get(name.name());
    }
}
