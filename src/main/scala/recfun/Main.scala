package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (c > r) throw new IllegalArgumentException("columns > rows")

    def iterate(c: Int, r: Int): Int = {
      if (c == 0 || r == c) {
        1
      } else {
        iterate(c - 1, r - 1) + iterate(c, r - 1)
      }
    }

    iterate(c, r)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {

    def checkTrue(chars: List[Char], counter: Int = 0): Boolean = {
      if (chars.isEmpty & counter == 0) {
        true
      } else if (chars.isEmpty & counter != 0) {
        false
      } else {
        if (chars.head == '(') {
          checkTrue(chars.tail, counter + 1)
        } else if (chars.head == ')' & counter > 0) {
          checkTrue(chars.tail, counter - 1)
        } else if (chars.head == ')') {
          false
        } else {
          checkTrue(chars.tail, counter)
        }
      }
    }

    checkTrue(chars)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {

    def accum(acc: Int, co: List[Int]): Int = {

      if (acc < 0) {
        0
      } else if (acc == 0) {
        1
      } else if (co.isEmpty) {
        0
      } else {
        accum(acc - co.head, co) + accum(acc, co.tail)
      }

    }

    accum(money, coins)
  }


  def factorial(n: Int): Int = {

    def iterate(value: Int, acc: Int): Int = {
      if (value <= 0) {
        acc
      } else {
        iterate(value - 1, acc * value)
      }
    }

    iterate(n, 1)
  }

  def fibonacci(n : Int, a : Int = 0, b: Int =1): Int ={

    //Fibo not tail recursive
    //def acc(n : Int): Int ={
    //  if(n<=0) a
    //  else if (n==1) b
    //  else acc(n-1) + acc(n-2)
    //}

    // https://medium.com/@frank.tan/fibonacci-tail-recursive-explained-876edf5e86fc
    def iterate(n : Int, a: Int, b: Int): Int ={
      if(n<=0) a
      else iterate(n-1, b, a+b)
    }

    iterate(n, a, b)

  }

  def sqrt(x : Double) : Double ={

    def abs(x : Double) : Double = if(x<0) -x else x

    def sqrtIteration(guess:Double): Double ={
      if (isGoodEnough(guess)) guess
      else sqrtIteration(improve(guess))

    }

    def isGoodEnough(guess : Double) : Boolean = {
      if(  abs(guess*guess - x)/ x < 0.001) true
      else false
    }

    def improve(guess : Double) : Double =  ((x/guess) + guess)/ 2

    sqrtIteration(1)
  }

/**
  * Here’s an implementation of gcd using Euclid’s algorithm
  */
  def gcd(x : Int, y : Int) : Int = {
    if(y==0) x else gcd(y, x%y)
  }

}
