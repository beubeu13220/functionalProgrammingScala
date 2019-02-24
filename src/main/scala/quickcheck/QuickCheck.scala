package quickcheck

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[Int]
    h <- oneOf[H](genHeap,empty)
  } yield insert(x, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  //for any heap, adding the minimal element, and then finding it, should return the element in question
  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }
  //adding a single element to an empty heap, and then removing this element, should yield the element in question.
  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  //If you insert any two elements into an empty heap, finding the minimum of the resulting heap should get the smallest of the two elements back.
  property("min2") = forAll { (a: Int, b:Int) =>
    val h = insert(b,insert(a, empty))
    findMin(h) == (a min b)
  }

  //If you insert an element into an empty heap, then delete the minimum, the resulting heap should be empty.
  property("empty") = forAll{ a:Int =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }
  //Given any heap, you should get a sorted sequence of elements when continually finding and deleting minima. (Hint: recursion and helper functions are your friends.)
  //property("list sorted") = forAll{ a : H =>

  //  def iteration(heap:H, memoryList : List[A]): List[A] =
  //    if (isEmpty(heap)) memoryList
  //      else iteration(deleteMin(heap), findMin(heap)::memoryList)


  //  val heapList = iteration(a, Nil)
  //  heapList == heapList.sorted

  //}

  property("order of mins") = forAll { (h:H) =>
    toList(h).zip(toList(h).drop(1)).forall {
      case (x, y) => x <= y
    }
  }

  property("associative meld") = forAll { (h:H, i:H, j:H) =>
    val a = meld(meld(h, i), j)
    val b = meld(h, meld(i, j))
    toList(a) == toList(b)
  }

  def toList(h:H):List[Int] = if (isEmpty(h)) Nil else findMin(h) :: toList(deleteMin(h))

  //Finding a minimum of the melding of any two heaps should return a minimum of one or the other.
  property("min melding") = forAll{ (a : H, b :H) =>
    val minMeldingH = findMin(meld(a,b))
    minMeldingH == (findMin(a) min findMin(b))
  }


}
