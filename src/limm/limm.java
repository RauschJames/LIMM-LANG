package limm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class limm {
    static boolean hadError = false;
    public static void main(String[] args) throws IOException{
        if(args.length > 1){
            System.out.println("Usage: jlox [script]");
            System.exit(64);//throw error 64

        } else if (args.length == 1){
            runFile(args[0]);
        }else {
            runPrompt();
        }
    }
    private static void runFile(String path) throws IOException {
       byte[] bytes = Files.readAllBytes(Paths.get(path));
    }
    private static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader((System.in));
        BufferedReader reader = new BufferedReader(input);
        for (;;){
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null){
                break;
            }
            run(line);
            if (hadError) System.exit(65);
        }

    }
    public static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens){
            System.out.println(token);
        }
    }
    static void error(int line, String message){
        report(line, "",message);

    }
    static void report(int line, String where, String message){
        System.err.println("[line " + line + "] Error" + where + ": " + message);
    }

}
