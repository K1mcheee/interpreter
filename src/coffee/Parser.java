package coffee;

import java.util.ArrayList;
import java.util.List;

import coffee.BExpr.*;
import coffee.Expr.*;
import coffee.RExpr.*;
import coffee.Stmt.*;


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

    boolean check(String tag, int ahead) {
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
        } else if (check("OPAR", 0)) {
            read("OPAR");
            Expr expr = parseExpr();
            read("CPAR");
            return expr;
        } else if (check("NAME", 0)) {
            String atom = read("NAME");
            if (check("OPAR", 0)) {
                read("OPAR");
                List<Expr> args = parseArg();
                read("CPAR");
                return new CALL(atom, args);
            }
            return new VAR(atom);
        } else {
            err("INT/OPAR/NAME");
            return null;
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

    public BLOCK parseStmt() throws Exception {
        if (end()) {
            return new BLOCK(List.of());
        }
        Stmt lstmt = null;
        if (accept("DECL")) {
            lstmt = parseDecl();
        } else if (check("NAME", 0)) {
            lstmt = parseAssg();
        } else if (accept("IF")) {
            lstmt = parseIf();
        } else if (accept("WHILE")) {
            lstmt = parseWhile();
        } else if (accept("FUNC")) {
            lstmt = parseFunc();
        } else if (accept("RET")) {
            lstmt = parseRet();
        } else {
            fail("unmatched case: " + this.tokens.get(this.idx));
        }
        BLOCK rstmt = parseStmts();
        List<Stmt> lst = new ArrayList<>();
        lst.add(lstmt);
        lst.addAll(rstmt.stmts());
        return new BLOCK(lst);
    }

    public ASSG parseAssg() throws Exception {
        String name = read("NAME");
        read("ASSG");
        Expr expr = parseExpr();
        read("END");
        return new ASSG(new VAR(name), expr);
    }

    public DECL parseDecl() throws Exception {
        String name = read("NAME");
        read("ASSG");
        Expr expr = parseExpr();
        read("END");
        return new DECL(new VAR(name), expr);
    }

    public IF parseIf() throws Exception {
        read("OPAR");
        BExpr cond = parseBExpr();
        read("CPAR");

        read("OCUR");
        Stmt thenStmt = parseStmt();
        read("CCUR");

        read("ELSE");
        read("OCUR");
        Stmt elseStmt = parseStmt();
        read("CCUR");

        return new IF(cond, thenStmt, elseStmt);
    }

    public WHILE parseWhile() throws Exception {
        read("OPAR");
        BExpr cond = parseBExpr();
        read("CPAR");

        read("OCUR");
        Stmt loop = parseStmt();
        read("CCUR");
        return new WHILE(cond, loop);
    }

    public FUNC parseFunc() throws Exception {
        String name = read("NAME");
        read("OPAR");
        List<VAR> pars = parsePar();
        read("CPAR");

        read("OCUR");
        Stmt body = parseStmt();
        read("CCUR");
        return new FUNC(name, pars, body);
    }

    public RET parseRet() throws Exception {
        Expr expr = parseExpr();
        read("END");
        return new RET(expr);
    }

    public List<Expr> parseArg() throws Exception {
        if (check("CPAR", 0)) {
            return List.of();
        } else {
            Expr expr = parseExpr();
            List<Expr> buffer = parseArgs();
            buffer.add(0, expr);
            return buffer;
        }
    }

    public List<VAR> parsePar() throws Exception {
        if (check("CPAR", 0)) {
            return List.of();
        } else {
            String name = read("NAME");
            List<VAR> buffer = parsePars();
            buffer.add(0, new VAR(name));
            return buffer;
        }
    }

    private BLOCK parseStmts() throws Exception {
        if (end() || check("CCUR", 0)) {
            return new BLOCK(List.of());
        }
        Stmt lstmt = null;
        if (accept("DECL")) {
            lstmt = parseDecl();
        } else if (check("NAME", 0)) {
            lstmt = parseAssg();
        } else if (accept("IF")) {
            lstmt = parseIf();
        } else if (accept("WHILE")) {
            lstmt = parseWhile();
        } else if (accept("FUNC")) {
            lstmt = parseFunc();
        } else if (accept("RET")) {
            lstmt = parseRet();
        } else {
            fail("unmatched case: " + this.tokens.get(this.idx));
        }
        BLOCK rstmt = parseStmts();
        List<Stmt> lst = new ArrayList<>();
        lst.add(lstmt);
        lst.addAll(rstmt.stmts());
        return new BLOCK(lst);
    }

    private List<Expr> parseArgs() throws Exception {
        List<Expr> buffer = new ArrayList<>();
        while (!check("CPAR", 0)) {
            read("SEP");
            Expr expr = parseExpr();
            buffer.add(expr);
        }
        return buffer;
    }

    private List<VAR> parsePars() throws Exception {
        List<VAR> buffer = new ArrayList<>();
        while (!check("CPAR", 0)) {
            read("SEP");
            String name = read("NAME");
            buffer.add(new VAR(name));
        }
        return buffer;
    }

    private void err(String tag) throws Exception {
        Pair<String, String> token = this.tokens.get(this.idx);
        String msg = "unexpected element: expect `" + tag + "` ; actual: `" + token.getFirst() + "` at: " + this.idx;
        fail(msg);
    }

    private void fail() throws Exception {
        throw new Exception("Unexpected end of input");
    }

    private void fail(String msg) throws Exception {
        throw new Exception(msg);
    }


}
