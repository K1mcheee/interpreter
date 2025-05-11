package coffee;

import coffee.Expr.*;

import java.util.HashMap;
import java.util.List;

public class Env {
    private Env global;
    private HashMap<String, VAL> vars = new HashMap<>();
    private HashMap<String, List<VAR>> pars = new HashMap<>();
    private HashMap<String, Stmt> body = new HashMap<>();
    private VAL ret = null;

    public Env(Env global) {
        this.global = global;
    }

    public void assg(VAR name, VAL value) {
        if (this.insv(name)) {
            this.vars.put(name.name(), value);
        } else {
            this.global.assg(name, value);
        }
    }

    public void decl(VAR name, VAL value) {
        this.vars.put(name.name(), new VAL(0));
    }

    public VAL getv(VAR name) {
        if (this.insv(name)) {
            return this.vars.get(name.name());
        }
        return this.global.getv(name);
    }

    public boolean hasv(VAR name) {
        return this.insv(name) || this.global.hasv(name);
    }

    public boolean insv(VAR name) {
        return this.vars.containsKey(name.name());
    }

    public void func(String name, List<VAR> pars, Stmt body) {
        this.pars.put(name, pars);
        this.body.put(name, body);
    }

    public List<VAR> pars(String name) {
        return this.pars.get(name);
    }

    public Stmt body(String name) {
        return this.body.get(name);
    }

    public VAL ret() {
        return this.ret;
    }

    public void setRet(VAL value) {
        this.ret = value;
    }

    public String funcStr() {
        return this.pars.toString();
    }

    @Override
    public String toString() {
        return this.vars.toString() + (this.global != null ? " ==> " + this.global : "");
    }
}
