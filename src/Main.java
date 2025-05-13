import java.util.List;


import coffee.BExpr;
import coffee.BExpr.*;
import coffee.Env;
import coffee.Eval;
import coffee.Expr;
import coffee.Expr.*;
import coffee.RExpr;
import coffee.RExpr.*;
import coffee.Stmt;
import coffee.Stmt.*;
import coffee.Pair;
import coffee.Parser;
import coffee.Tokenizer;
import coffee.Writer;


public class Main {
    public static void main(String[] args) throws Exception {
        Env global = new Env(null);
        String prog = """
                        def fac(n) {
                          var x = 0;
                          if (n < 0 || n == 0) {
                            return 1;
                          } else {
                            if (n == 0) {
                              return 1;
                            } else {
                              return n * fac(n - 1);
                            }
                          }
                        }
                        def fib(n) {
                            if (n <= 1) {
                                return n;
                           } else {
                                return fib(n - 2) + fib(n - 1);
                               }
                        }
                        
                        var b = 7;
                        b = fib(7);
                        """;
        Tokenizer tokenizer = new Tokenizer();
        List<Pair<String, String>> tokens = tokenizer.tokenize(prog);
        System.out.println(tokens);
        Parser parser = new Parser(tokens);
        Stmt ast = parser.parseStmt();
        System.out.println(Writer.write(ast, ""));
        System.out.println(">" + global);
        Eval.eval(ast,global, global);
        System.out.println("<" + global);

    }
}