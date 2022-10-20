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
        defineAst(outputDir,"Expr",Arrays.asList(
                "Binary : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator, Expr right"
        ));
    }
    private static void defineAst(String outputDir, String inClass,List<String> types) throws IOException{
        String path = outputDir + "/" + inClass + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        writer.println("package limm;");
        writer.println();
        writer.println("import java.util.list");
        writer.println("abstract class " + inClass + " {");
        defineVisitor(writer,inClass,types);
        for (String type : types) {
            String className = type.split(":")[0].trim(); // pass regex : bc class ends with it 0 is first side
            String fields = type.split(":")[1].trim(); // 1 is the other side
            defineType(writer,inClass, className,fields);
            writer.println("  abstract <R> R accept(Visitor<R> visitor);");

        }
        writer.println("}");
        writer.close();
    }
    private static void defineType(PrintWriter writer,String inClass,String className, String fieldsAsAList){
        writer.println(" static class " + className + "extends " + inClass + " { ");
        writer.println("   " + className + "(" + fieldsAsAList + ")" + "{ ");
        String[] convertedFields = fieldsAsAList.split(", ");
        for (String field : convertedFields) {
            String name = field.split(" ")[1]; //1 is the other side
            writer.println("    this." + name + " = " + name + ";");
        }
        writer.println("    }");

        writer.println();
        writer.println("    @Override");
        writer.println("    <R> R accept(Visitor<R> visitor) {");
        writer.println("      return visit.visit" + className + inClass + "(this);");
        writer.println("    }");

        writer.println();
        for (String field : convertedFields ){
            writer.println("    final + " + field + ";");

        }
        writer.println("   }");
    }
    private static void defineVisitor(PrintWriter writer,String inClass,List<String> types){
        writer.println("INTERFACE VISITOR<R> {");
        for(String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println("    R Visit " + typeName + inClass + "(" + typeName + " " + inClass.toLowerCase() + ");");
        }
        writer.println(" }");
    }

}
