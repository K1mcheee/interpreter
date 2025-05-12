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
                          int x = 0;
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
                        
                        def order1(a,b,c,d) {
                          return a + b * c + d;
                        }
                        def order2(a,b,c,d) {
                          return a - b - c - d;
                        }
                        def order3(a,b,c,d) {
                          return a * b - c * d;
                        }
                        int m = "fib(7)";
                        int b = "the quick brown fox";
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
        /*
         Env global = new Env(null);
         Env env = new Env(global);
         Stmt func = new BLOCK(List.of(
                 new DECL(new VAR("res"), new VAL(1))                         ,
                 new WHILE( new GEQ(new VAR("n"), new VAL(0)), new BLOCK(List.of(
                         new ASSG(new VAR("res"), new MUL(new VAR("res"), new VAR("n")))    ,
                         new ASSG(new VAR("n")  , new SUB(new VAR("n")  , new VAL(1)))
                 )))                                              ,
                 new RET(new VAR("res"))
         ));
         Stmt prog = new BLOCK(List.of(
                 new FUNC("fac", List.of(new VAR("n")), func)
         ));


        System.out.println("-- Environment: Before");
        System.out.println(env);
        System.out.println(global.funcStr());
        System.out.println("-------------------------");
        System.out.println(Writer.write(prog, ""));
        System.out.println(Eval.eval(prog, env, global));
        System.out.println("-------------------------");
        System.out.println("-- Environment: After");
        System.out.println(env);
        System.out.println(global.funcStr());
        */
    }
}