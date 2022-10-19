package limm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static limm.TokenType.*;

public class Scanner {
    private int start = 0;
    private int curr = 0;
    private int line = 1;
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    Scanner(String source){
        this.source = source;
    }
    private boolean over() {
        return curr >= source.length();
    }
    List<Token> scanTokens() {
        while(!over()) {
            start = curr;
            scanToken();
        }
        tokens.add(new Token(EOF, "",null,line));
        return tokens;
    }
    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("func", FUNC);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("self", SELF);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);

    }
    public void scanToken(){
        char c = next();
        switch (c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!':
                addToken(match('=' ) ? BANG_EQUAL : BANG); break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS); break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            case '/':
                if(match('/')) {
                    while (peek() != '\n' && !over()) next();
                } else {
                    addToken(SLASH);
                } break;
            case '\n':
                line++; break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '"': string(); break;
            //now or etc
            case 'o': if (match('r')) {
                addToken(OR);
            } break;


            default:
                if(isNum(c)) {
                    num();
                }else if(isAlpha(c)){
                    identify(); //what am I???
                }
                else {
                    limm.error(line, "Invalid Character Please Try Again");
                }
                break;
        }

    }
    private char next(){
        return source.charAt(curr++);

    }
    private void addToken(TokenType type){
        addToken(type , null);
    }
    private void addToken(TokenType type,Object literal) {
        String text = source.substring(start, curr);
        tokens.add(new Token(type,text,literal,line));
    }
    private boolean match(char next){
        if(!over() && source.charAt(curr) == next){
            curr++;
            return true;
        }
        return false;
    }
    private char peek(){
        if(!over()){
            return source.charAt(curr);
        }
        return '\0';

    }
    private boolean isNum(char c){
        return c >= '0' && c <= '9';
    }
    private void string(){
        while(peek() != '"' && !over()){
            if (peek() == '\n') line++;
            next();
        }
        if(over()) {
            limm.error(line,"Ya done forgot to close your string");
            return;
        }
        next(); //because peek doesnt run though "
        addToken(STRING,source.substring(start+1,curr-1));
    }
    private char peek2(){
        if(curr + 1 >= source.length()) return '\0';
        return source.charAt(curr+1);
    }
    private void num() {
        while(isNum(peek())) next();
        if (peek() == '.' && isNum(peek2())){
            next();
            while(isNum(peek())) next();
        }
        addToken(NUMBER, Float.parseFloat(source.substring(start,curr)));
    }
    private boolean isAlpha(char c){
        return ( c >= 'a' && c <= 'z') || ( c >= 'A' || c <='Z') || c == '_';
    }
    private boolean isVALIDALPHANUM(char c){
        return isAlpha(c) || isNum(c);
    }
    private void identify(){
           //as
            while(isVALIDALPHANUM(peek()) && !over() && isVALIDALPHANUM(peek2())) next();
            addToken(keywords.getOrDefault(source.substring(start,curr-1),IDENTIFIER));
    }
}
