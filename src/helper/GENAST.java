package helper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


public class GENAST {

    public static void main(String[] args) throws IOException{
        if(args.length != 1){
            System.err.println("INCORRECT USAGE 1 ARGUMENT REQUIRED/ACCEPTED");
            System.exit(64); //exit according to unix standards
        }
        String outputDir = args[0];
    }
}
