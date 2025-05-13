package coffee;

import java.util.ArrayList;
import java.util.List;


public class Tokenizer {

    boolean isLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }

    boolean isSpace(char c) {
        return c == ' ' || c == '\n';
    }

    boolean isName(char c) {
        return isLower(c) || isUpper(c);
    }

    boolean isNames(char c) {
        return isLower(c) || isUpper(c) || isNum(c);
    }

    boolean isBool(char c) {
        return "&|".indexOf(c) >= 0;
    }

    boolean isArOp(char c) {
        return "+-*/".indexOf(c) >= 0;
    }

    boolean isReOp(char c) {
        return "<=>!".indexOf(c) >= 0;
    }

    boolean isComma(char c) {
        return c == ',';
    }

    boolean isParen(char c) {
        return "(){}".indexOf(c) >= 0;
    }

    boolean isSemi(char c) {
        return c == ';';
    }

    boolean isInv(char c) {
        return c == '"';
    }

    boolean isSymbol(char c) {
        return isBool(c) || isArOp(c) || isReOp(c) || isComma(c) || isParen(c) || isSemi(c);
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

    int str(String prog, int sidx) throws Exception {
        int idx = sidx + 1;
        int len = prog.length();

        while (idx < len && !isInv(prog.charAt(idx))) {
            idx++;
        }

        if (idx >= len) {
            throw new Exception("Expect \" for string declaration");
        }

        return ++idx;
    }

    int name(String prog, int sidx) {
        int idx = sidx;
        int len = prog.length();

        while (idx < len && isNames(prog.charAt(idx))) {
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
        } else {
            char next = prog.charAt(idx + 1);
            if ((c == '<' || c == '>' || c == '=') && (next == '=')) {
                idx += 2;
            } else if ((c == '&') && (next == '&')) {
                idx += 2;
            } else if ((c == '|') && (next == '|')) {
                idx += 2;
            } else if ((c == '!') && (next == '=')) {
                idx += 2;
            } else {
                idx++;
            }
        }
        return idx;
    }

    Pair<String, String> strs(String prog, int sidx,int eidx) {
        String token = prog.substring(sidx, eidx);
        return new Pair<>("STR", token);
    }

    Pair<String, String> names(String prog, int sidx, int eidx) {
        String token = prog.substring(sidx, eidx);
        return switch(token) {
            case "while" -> new Pair<>("WHILE", token);
            case "if"    -> new Pair<>("IF", token);
            case "else"  -> new Pair<>("ELSE", token);
            case "return" -> new Pair<>("RET", token);
            case "def"    -> new Pair<>("FUNC", token);
            case "var"    -> new Pair<>("DECL", token);
            default       -> new Pair<>("NAME", token);
        };
    }

    Pair<String, String> numbers(String prog,int sidx, int eidx) {
        String token = prog.substring(sidx, eidx);
        return new Pair<>("INT", token);
    }

    Pair<String, String> symbols(String prog, int sidx, int eidx) {
        String token = prog.substring(sidx, eidx).trim();
        return switch(token) {
            case "+"  -> new Pair<>("ADD", token);
            case "-"  -> new Pair<>("SUB", token);
            case "*"  -> new Pair<>("MUL", token);
            case "/"  -> new Pair<>("DIV", token);
            case "==" -> new Pair<>("EQS",token);
            case ">"  -> new Pair<>("GEQ", token);
            case ">=" -> new Pair<>("GTE" ,token);
            case "<"  -> new Pair<>("LEQ", token);
            case "<=" -> new Pair<>("LTE", token);
            case "!=" -> new Pair<>("NEQ", token);
            case "&&" -> new Pair<>("AND", token);
            case "!"  -> new Pair<>("NOT", token);
            case "||" -> new Pair<>("OR", token);
            case "{"  -> new Pair<>("OCUR", token);
            case "}"  -> new Pair<>("CCUR", token);
            case "("  -> new Pair<>("OPAR", token);
            case ")"  -> new Pair<>("CPAR", token);
            case ";"  -> new Pair<>("END", token);
            case ","  -> new Pair<>("SEP", token);
            case "="  -> new Pair<>("ASSG", token);
            default   -> throw new IllegalArgumentException("Unidentified token: " + token + " at ("
                    + sidx + ", " + eidx + ")");
        };
    }

    public List<Pair<String, String>> tokenize(String prog) throws Exception {
        List<Pair<String, String>> res = new ArrayList<>();
        int idx = skip(prog, 0);
        int len = prog.length();

        while (idx < len) {
            char c = prog.charAt(idx);
            if (isName(c)) {
                int sidx = idx;
                idx = name(prog, sidx);
                res.add(names(prog, sidx, idx));
            } else if (isNum(c)) {
                int sidx = idx;
                idx = number(prog, sidx);
                res.add(numbers(prog, sidx, idx));
            } else if (isInv(c)) {
                int sidx = idx;
                idx = str(prog, sidx);
                res.add(strs(prog, sidx, idx));
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
