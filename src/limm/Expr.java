package limm;

import java.util.list
abstract class Expr {
INTERFACE VISITOR<R> {
    R Visit BinaryExpr(Binary expr);
    R Visit GroupingExpr(Grouping expr);
    R Visit LiteralExpr(Literal expr);
    R Visit UnaryExpr(Unary expr);
 }
 static class Binaryextends Expr { 
   Binary(Expr left, Token operator, Expr right){ 
    this.left = left;
    this.operator = operator;
    this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visit.visitBinaryExpr(this);
    }

    final + Expr left;
    final + Token operator;
    final + Expr right;
   }
  abstract <R> R accept(Visitor<R> visitor);
 static class Groupingextends Expr { 
   Grouping(Expr expression){ 
    this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visit.visitGroupingExpr(this);
    }

    final + Expr expression;
   }
  abstract <R> R accept(Visitor<R> visitor);
 static class Literalextends Expr { 
   Literal(Object value){ 
    this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visit.visitLiteralExpr(this);
    }

    final + Object value;
   }
  abstract <R> R accept(Visitor<R> visitor);
 static class Unaryextends Expr { 
   Unary(Token operator, Expr right){ 
    this.operator = operator;
    this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visit.visitUnaryExpr(this);
    }

    final + Token operator;
    final + Expr right;
   }
  abstract <R> R accept(Visitor<R> visitor);
}
