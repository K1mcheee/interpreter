package coffee;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;


public class Tokenizer {

    boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    boolean isSpace(char c) {
        return c == ' ' || c == '\n';
    }

    boolean isArOp(char c) {
        return "+-*/".indexOf(c) >= 0;
    }

    boolean isSymbol(char c) {
        return isArOp(c);
    }

    // Scanning
    int skip(String prog, int sidx) {
        int idx = sidx;
        int len = prog.length();

        while (idx < len && isSpace(prog.charAt(idx))) {
            idx++;
        }
        return idx;
    }

    int number(String prog, int sidx) {
        int idx = sidx;
        int len = prog.length();

        while (idx < len && isNum(prog.charAt(idx))) {
            idx++;
        }
        return idx;
    }

    int symbol(String prog, int sidx) {
        int idx = sidx;
        int len = prog.length();
        char c = prog.charAt(idx);

        if (isArOp(c) || idx + 1 == len) {
            idx++;
        }

        idx++;

        return idx;

    }

    Pair<String, String> numbers(String prog,int sidx, int eidx) {
        String token = prog.substring(sidx, eidx);
        return new Pair<>("INT", token);
    }

    Pair<String, String> symbol(String token, int sidx, int eidx) {
        return switch(token) {
            case "+" -> new Pair<>("ADD", token);
            case "-" -> new Pair<>("SUB", token);
            case "*" -> new Pair<>("MUL", token);
            case "/" -> new Pair<>("DIV", token);
            default  -> throw new IllegalArgumentException("Unidentified token: " + token + " at ("
            + sidx + ", " + eidx + ")");
        };
    }

    Pair<String, String> symbols(String prog, int sidx, int eidx) {
        String token = prog.substring(sidx, eidx).trim();
        return symbol(token, sidx, eidx);
    }

    public List<Pair<String, String>> tokenize(String prog) {
        List<Pair<String, String>> res = new ArrayList<>();
        int idx = skip(prog, 0);
        int len = prog.length();

        while (idx < len) {
            char c = prog.charAt(idx);
            if (isNum(c)) {
                int sidx = idx;
                idx = number(prog, idx);
                res.add(numbers(prog, sidx, idx));
            } else if (isSymbol(c)) {
                int sidx = idx;
                idx = symbol(prog, sidx);
                res.add(symbols(prog, sidx, idx));
            }
            idx = skip(prog, idx);
        }

        return res;
    }


}
