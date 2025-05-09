import java.util.List;


import coffee.Env;
import coffee.Eval;
import coffee.Expr;
import coffee.Expr.*;
import coffee.Pair;
import coffee.Parser;
import coffee.Tokenizer;
import coffee.Writer;


public class Main {
    public static void main(String[] args) throws Exception {
        /*
        String expr = "1 + 2";
        System.out.println(expr);
        Tokenizer tokenizer = new Tokenizer();
        List<Pair<String, String>> tokens = tokenizer.tokenize(expr);
        System.out.println(tokens);
        Parser parser = new Parser(tokens);
        System.out.println(parser.parseExpr());
        */
         Env global = new Env(null);
         Expr expr = new ADD(new MUL(new VAL(3), new VAL(5)), new VAR("x"));

        global.decl(new VAR("x"));
        global.assg(new VAR("x"), new VAL(2));

        System.out.println(Writer.write(expr));
        System.out.println(Eval.eval(expr, global));

    }
}