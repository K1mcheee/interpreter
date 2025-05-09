import java.util.List;

import coffee.Eval;
import coffee.Expr;
import coffee.Pair;
import coffee.Parser;
import coffee.Tokenizer;
import coffee.Writer;
import coffee.Expr.ADD;
import coffee.Expr.VAL;
import coffee.Expr.SUB;
import coffee.Expr.MUL;
import coffee.Expr.DIV;

public class Main {
    public static void main(String[] args) throws Exception {
        String expr = "1 + 2";
        System.out.println(expr);
        Tokenizer tokenizer = new Tokenizer();
        List<Pair<String, String>> tokens = tokenizer.tokenize(expr);
        System.out.println(tokens);
        Parser parser = new Parser(tokens);
        System.out.println(parser.parseExpr());
    }
}