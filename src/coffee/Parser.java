package coffee;

import java.util.List;

import coffee.BExpr.*;
import coffee.Expr.*;
import coffee.RExpr.*;


public class Parser {
    private int idx = 0;
    private List<Pair<String, String>> tokens;

    public Parser(List<Pair<String, String>> tokens) {
        this.tokens = tokens;
    }
    private boolean end() {
        return this.idx >= this.tokens.size();
    }

    private void next() {
        this.idx++;
    }

    boolean accept(String tag) throws Exception {
        if (this.end()) {
            fail();
        }
        Pair<String, String> token = this.tokens.get(this.idx);

        if (token.getFirst().equals(tag)) {
            // debug
            System.out.println("! accepting " + tag);
            next();
            return true;
        }
        return false;
    }

    boolean check(String tag, int ahead) throws Exception {
        if (this.end()) {
            return false;
        }
        Pair<String, String> token = this.tokens.get(this.idx + ahead);

        if (token.getFirst().equals(tag)) {
            return true;
        }
        return false;
    }

    boolean expect(String tag) throws Exception {
        if (this.end()) {
            fail();
        }
        Pair<String, String> token = this.tokens.get(this.idx);

        if (!token.getFirst().equals(tag)) {
            fail("Unexpected element: expect `" + tag + "` ; actual: `" +
                    token.getFirst() + "` at: " + this.idx);
        }

        return true;
    }

    String read(String tag) throws Exception {
        expect(tag);
        Pair<String, String> token = this.tokens.get(this.idx);
        next();
        return token.getSecond();
    }

    public Expr parseAtom() throws Exception {
        if (check("INT", 0)) {
            String atom = read("INT");
            return new VAL(Integer.parseInt(atom));
        } else {
            String atom = read("NAME");
            return new VAR(atom);
        }
    }

    public Expr parseTerm() throws Exception {
        Expr latom = parseAtom();
        while (check("MUL", 0) || check("DIV", 0)) {
            if (check("MUL", 0)) {
                read("MUL");
                Expr rexpr = parseAtom();
                latom = new MUL(latom,rexpr);
            } else if (check("DIV", 0)) {
                read("DIV");
                Expr rexpr = parseAtom();
                latom = new DIV(latom, rexpr);
            }
        }
        return latom;
    }

    public Expr parseExpr() throws Exception {
        Expr lterm = parseTerm();
        while (check("ADD", 0) || check("SUB", 0)) {
            if (check("ADD", 0)) {
                read("ADD");
                Expr rexpr = parseTerm();
                lterm = new ADD(lterm, rexpr);
            } else if (check("SUB", 0)) {
                read("SUB");
                Expr rexpr = parseTerm();
                lterm = new SUB(lterm, rexpr);
            }
        }
        return lterm;

    }

    public BExpr parseBConj() throws Exception {
        BExpr lnot = parseBNot();
        while (check("AND", 0)) {
            read("AND");
            BExpr rnot = parseBNot();
            lnot = new AND(lnot, rnot);
        }
        return lnot;
    }

    public BExpr parseBExpr() throws Exception {
        BExpr lconj = parseBConj();
        while (check("OR", 0)) {
            read("OR");
            BExpr rconj = parseBConj();
            lconj = new OR(lconj,rconj);
        }
        return lconj;
    }

    public BExpr parseBNot() throws Exception {
        if (accept("NOT")) {
            BExpr bexpr = parseBExpr();
            return new NOT(bexpr);
        } else {
            return parseRExpr();
        }
    }

    public RExpr parseRExpr() throws Exception {
        Expr lexpr = parseExpr();
        if (accept("EQS")) {
            Expr rexpr = parseExpr();
            return new EQS(lexpr, rexpr);
        } else if (accept("GEQ")) {
            Expr rexpr = parseExpr();
            return new GEQ(lexpr, rexpr);
        } else if (accept("GTE")) {
            Expr rexpr = parseExpr();
            return new GTE(lexpr, rexpr);
        } else if (accept("LEQ")) {
            Expr rexpr = parseExpr();
            return new LEQ(lexpr, rexpr);
        } else if (accept("LTE")) {
            Expr rexpr = parseExpr();
            return new LTE(lexpr, rexpr);
        } else if (accept("NEQ")) {
            Expr rexpr = parseExpr();
            return new NEQ(lexpr, rexpr);
        } else {
            fail("unmatched case: " + this.tokens.get(this.idx));
            return null;
        }
    }

    private void fail() throws Exception {
        throw new Exception("Unexpected end of input");
    }

    private void fail(String msg) throws Exception {
        throw new Exception(msg);
    }


}
