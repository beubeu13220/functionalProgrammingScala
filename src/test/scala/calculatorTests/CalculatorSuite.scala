package calculatorTests

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CalculatorSuite extends FunSuite with ShouldMatchers {

  /******************
   ** TWEET LENGTH **
   ******************/

  def tweetLength(text: String): Int =
    text.codePointCount(0, text.length)

  test("tweetRemainingCharsCount with a constant signal") {
    val result = TweetLength.tweetRemainingCharsCount(Var("hello world"))
    assert(result() == MaxTweetLength - tweetLength("hello world"))

    val tooLong = "foo" * 200
    val result2 = TweetLength.tweetRemainingCharsCount(Var(tooLong))
    assert(result2() == MaxTweetLength - tweetLength(tooLong))
  }

  test("tweetRemainingCharsCount with a supplementary char") {
    val result = TweetLength.tweetRemainingCharsCount(Var("foo blabla \uD83D\uDCA9 bar"))
    assert(result() == MaxTweetLength - tweetLength("foo blabla \uD83D\uDCA9 bar"))
  }


  test("colorForRemainingCharsCount with a constant signal") {
    val resultGreen1 = TweetLength.colorForRemainingCharsCount(Var(52))
    assert(resultGreen1() == "green")
    val resultGreen2 = TweetLength.colorForRemainingCharsCount(Var(15))
    assert(resultGreen2() == "green")

    val resultOrange1 = TweetLength.colorForRemainingCharsCount(Var(12))
    assert(resultOrange1() == "orange")
    val resultOrange2 = TweetLength.colorForRemainingCharsCount(Var(0))
    assert(resultOrange2() == "orange")

    val resultRed1 = TweetLength.colorForRemainingCharsCount(Var(-1))
    assert(resultRed1() == "red")
    val resultRed2 = TweetLength.colorForRemainingCharsCount(Var(-5))
    assert(resultRed2() == "red")
  }

  trait ValuesSignal{
    val a = Var(1.0)
    val b = Var(4.0)
    val c = Var(4.0)
    val delta = computeDelta(a,b,c)
    val solutions = computeSolutions(a,b,c,delta)
  }
  test("computeDelta zero and positive with instaneous maj"){
    new ValuesSignal{
      assert(delta() == 0.0)
      c.update(3)
      assert(delta() == 4.0)
    }
  }


  test("computeDelta zero and negative with instaneous maj"){
    new ValuesSignal{
      assert(delta() == 0.0)
      c.update(5)
      assert(delta() == -4.0)
    }
  }

  test("computeSolutions zero and negative delta"){
    new ValuesSignal{
      assert(solutions() == Set(-2.0))
      c.update(5)
      assert(solutions() == Set(0.0))
    }
  }

  test("computeSolutions zero and positive delta"){
    new ValuesSignal{
      assert(solutions() == Set(-2.0))
      c.update(3)
      assert(solutions() == Set(-1.0,-3.0))
    }
  }

  trait ValuesEval{
    val myRef : Map[String, Signal[Expr]] = Map("a"->Signal(Literal(2.0)))
    val myFormula : Expr = Divide(Literal(4.0),Literal(2.0))
  }

  test("eval with simple expression divide"){
    new ValuesEval{
      assert(eval(myFormula, myRef) == 2.0)
    }
  }
  test("eval with reference/update"){
    new ValuesEval{
      override val myFormula: Divide = Divide(Literal(4.0),Ref("a"))
      override val myRef : Map[String, Var[Expr]] = Map("a"->Var(Literal(2.0)))
      val resultEvalWithSignal = Var(eval(myFormula, myRef))
      assert(resultEvalWithSignal() == 2.0)
      myRef("a").update(Literal(4.0))
      assert(resultEvalWithSignal() == 1.0)
    }
  }

  test("eval with complex formula") {
    new ValuesEval {
      override val myFormula: Minus = Minus(Divide(Ref("b"), Literal(2.0)), Divide(Literal(2.0), Ref("a")))
      override val myRef: Map[String, Var[Expr]] = Map("a" -> Var(Literal(2.0)), "b" -> Var(Literal(5.0)))
      assert(eval(myFormula, myRef) == 1.5)
    }
  }


  test("computeValues without cyclic effect") {
    val expressionsToCompue : Map[String, Signal[Expr]] = Map(
      "expression1" -> Signal(Divide(Literal(4.0),Literal(2.0))),
      "expression2" -> Signal(Plus(Ref("expression1"), Literal(3.0))),
      "expression3" -> Signal(Minus(Divide(Ref("expression1"), Literal(2.0)), Divide(Literal(2.0), Ref("expression2"))))
    )
    val expressionsValues = computeValues(expressionsToCompue)

    assert(expressionsValues("expression1")() == 2.0)
    assert(expressionsValues("expression2")() == 5.0)
    assert(expressionsValues("expression3")() == 0.6)

  }

  //test("computeValues raise exception with cyclic depedency") {
  //  val expressionsToCompue : Map[String, Signal[Expr]] = Map(
  //    "expression1" -> Signal(Divide(Literal(4.0),Ref("expression1"))),
  //    "expression2" -> Signal(Plus(Ref("expression1"), Literal(3.0)))
  //  )
  //  assertThrows[IllegalArgumentException] {
  //    computeValues(expressionsToCompue)
  //  }
  //}
}
