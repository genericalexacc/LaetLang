package com.genericalexacc.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
     public static void main(String[] args) throws IOException {
          if (args.length != 1) {
               System.err.println("Usage: generate_ast <output_directory>");
               System.exit(64);
          }
          String outputDir = args[0];
          defineAst(outputDir, "Expr", Arrays.asList(
                  "Assign   : Token name, Expr value",
                  "Binary   : Expr left, Token operator, Expr right",
                  "Call     : Expr callee, Token paren, List<Expr> arguments",
                  "Grouping : Expr expression",
                  "Literal  : Object value",
                  "Logical  : Expr left, Token operator, Expr right",
                  "Variable : Token name",
                  "Unary    : Token operator, Expr right"
          ));

          defineAst(outputDir, "Stmt", Arrays.asList(
                  "Block        : List<Stmt> statements",
                  "Expression   : Expr expression",
                  "Function     : Token name, List<Token> params, List<Stmt> body",
                  "If           : Expr condition, Stmt thenBranch, Stmt elseBranch",
                  "Print        : Expr expression",
                  "Return       : Token keyword, Expr value",
                  "While        : Expr condition, Stmt body",
                  "Var          : Token name, Expr initializer"
          ));
     }

     public static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
          String path = outputDir + "/" + baseName + ".java";
          PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);

          writer.println("package com.company;");
          writer.println();
          writer.println("import com.company.Token;");
          writer.println("import java.util.List;");
          writer.println();
          writer.println("abstract class " + baseName + " {");

          defineVisitor(writer, baseName, types);

          for (String type : types) {
               String className = type.split(":")[0].trim();
               String fields = type.split(":")[1].trim();
               defineType(writer, baseName, className, fields);
          }

          writer.println();
          writer.println("  abstract <R> R accept(Visitor<R> visitor);");

          writer.println("}");
          writer.close();
     }

     public static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
          writer.println("  interface Visitor<R> {");
          for (String t : types) {
               String typeName = t.split(":")[0].trim();
               writer.println("    R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
          }

          writer.println("  }");
     }

     private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
          writer.println("  static class " + className + " extends " + baseName + " {");
          writer.println("    " + className + "(" + fieldList + ") {");

          String[] fields = fieldList.split(", ");
          for (String f : fields) {
               String name = f.split(" ")[1];
               writer.println("      this." + name + " = " + name + ";");
          }
          writer.println("      }");

          writer.println();
          writer.println("     @Override");
          writer.println("     <R> R accept(Visitor<R> visitor) {");
          writer.println("     return visitor.visit" + className + baseName + "(this);");
          writer.println("    }");

          writer.println();

          for (String f : fields) {
               writer.println("    final " + f + ";");
          }

          writer.println("  }");
     }
}
