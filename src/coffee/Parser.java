package coffee;

import java.util.List;

import coffee.Expr.*;

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
            next();
            return true;
        }
        return false;
    }

    boolean check(String tag, int ahead) throws Exception {
        if (this.end()) {
            //fail();
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
        } else if (check("NAME", 0)) {
            String atom = read("NAME");
            return new VAR(atom);
        } else {
            throw new IllegalArgumentException("Unidentified variable");
        }
    }

    public Expr parseExpr() throws Exception {
        Expr latom = parseAtom();
        while (check("ADD", 0) || check("SUB", 0) ||
                check("MUL", 0) || check("DIV", 0)) {
            if (check("ADD", 0)) {
                read("ADD");
                Expr rexpr = parseAtom();
                latom = new ADD(latom, rexpr);
            } else if (check("SUB", 0)) {
                read("SUB");
                Expr rexpr = parseAtom();
                latom = new SUB(latom, rexpr);
            } else if (check("MUL", 0)) {
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



    private void fail() throws Exception {
        throw new Exception("Unexpected end of input");
    }

    private void fail(String msg) throws Exception {
        throw new Exception(msg);
    }


}
