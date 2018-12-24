package recfunTests

import recfun.Main._
import org.scalatest._


class MainTests extends FunSuite {

  //Fibonacci
  test("getFibonacci") {
    assert(fibonacci(0) == 0)
    assert(fibonacci(1) == 1)
    assert(fibonacci(2) == 1)
    assert(fibonacci(3) == 2)
    assert(fibonacci(4) == 3)
    assert(fibonacci(6) == 8)
    assert(fibonacci(10) == 55)
    assert(fibonacci(12) == 144)
  }

  //BalanceSuite
  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  //CountChange
  test("countChange: example given in instructions") {
    assert(countChange(4,List(1,2)) == 3)
  }

  test("countChange: sorted CHF") {
    assert(countChange(300,List(5,10,20,50,100,200,500)) == 1022)
  }

  test("countChange: no pennies") {
    assert(countChange(301,List(5,10,20,50,100,200,500)) == 0)
  }

  test("countChange: unsorted CHF") {
    assert(countChange(300,List(500,5,50,100,20,200,10)) == 1022)
  }

  //PascalSuite
  test("pascal: col=0,row=2") {
    assert(pascal(0,2) == 1)
  }

  test("pascal: col=1,row=2") {
    assert(pascal(1,2) == 2)
  }

  test("pascal: col=1,row=3") {
    assert(pascal(1,3) == 3)
  }

}
