package calculator

sealed abstract class Expr
final case class Literal(v: Double) extends Expr
final case class Ref(name: String) extends Expr
//abstract class twoExpr(a: Expr, b: Expr) extends Expr
//final case class Plus(a: Expr, b: Expr) extends twoExpr(a: Expr, b: Expr)
final case class Plus(a: Expr, b: Expr) extends Expr
final case class Minus(a: Expr, b: Expr) extends Expr
final case class Times(a: Expr, b: Expr) extends Expr
final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  def computeValues(
      namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {

    def helperIsRef(anExpression:Expr):  List[String] ={

      def iterationExpr(anExpression:Expr, accList : List[String]): List[String] = anExpression match {
        case Ref(name) => name :: accList
        case Literal(_) => accList
        case Divide(a, b) => iterationExpr(a, accList) ::: iterationExpr(b, accList)
        case Times(a, b) => iterationExpr(a, accList) ::: iterationExpr(b, accList)
        case Minus(a, b) => iterationExpr(a, accList) ::: iterationExpr(b, accList)
        case Plus(a, b) => iterationExpr(a, accList) ::: iterationExpr(b, accList)
      }
      iterationExpr(anExpression, Nil)
    }

    val tupleTransition : List[(String,String)]= {
      for{
        nameExpressions <- namedExpressions
        namesExpressionsContains <- namedExpressions.filterKeys( key => helperIsRef(nameExpressions._2()).contains(key))
        if helperIsRef(namesExpressionsContains._2()).contains(nameExpressions._1)
      } yield (nameExpressions._1, namesExpressionsContains._1)
    } toList


    tupleTransition match {
      case Nil => namedExpressions.map{ case (name, signalExpr) => (name, Signal(eval(signalExpr(),namedExpressions))) }
      case _ => throw new IllegalArgumentException("namedExpressions : cyclic dependencies")
    }

  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {

    def iterateOver(expr : Expr): Double = {
      expr match {
        case Divide(a, b) => iterateOver(a) / iterateOver(b)
        case Times(a, b) => iterateOver(a) * iterateOver(b)
        case Minus(a, b) => iterateOver(a) - iterateOver(b)
        case Plus(a, b) => iterateOver(a) + iterateOver(b)
        case Ref(name) => iterateOver(getReferenceExpr(name, references))
        case Literal(v) => v
      }
    }

    iterateOver(expr)

  }


  /** Get the Expr for a referenced variables.
   *  If the variable is not known, returns a literal NaN.
   */
  private def getReferenceExpr(name: String,
      references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } { exprSignal =>
      exprSignal()
    }
  }
}
