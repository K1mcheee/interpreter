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
        /*
        String expr = "x < 0 || x == 0";
        System.out.println(expr);
        Tokenizer tokenizer = new Tokenizer();
        List<Pair<String, String>> tokens = tokenizer.tokenize(expr);
        System.out.println(tokens);
        Parser parser = new Parser(tokens);
        RExpr prog = parser.parseRExpr();
        System.out.println(prog);
        System.out.println(Writer.write(prog));
        */

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

    }
}