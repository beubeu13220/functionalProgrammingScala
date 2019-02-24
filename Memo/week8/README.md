# Week 8
###
### Streams
- Stream est un object similaire aux list mais il est évalué on-demand
 ```scala
 val xs = Stream.cons(1, Stream.cons(2, Stream.empty))
 (1 to 1000).toStream > res0: Stream[Int] = Stream(1, ?)
 ```
- Seulement head qui est calculé, le tail reste abstrait
- Pour les stream la méthode :: est remplacée par #::
- L'implementation des stream est également proche de celle des listes
 ```scala
 object Stream {
def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
def isEmpty = false
def head = hd
def tail = tl
}
val empty = new Stream[Nothing] {
def isEmpty = true
def head = throw new NoSuchElementException(”empty.head”)
def tail = throw new NoSuchElementException(”empty.tail”)
}
```
- La seule différence est sur tl où ici est évalué by-name, alors que sur les listes est évalué au call  

### Lazy Evaluation

- Un problème sur les streams c'est que si on en appele plusieurs le tail cela n'est plus très efficace
- Une solution pourrait être de stocker le tail pour ne pas recalculer x fois ces valeurs
- On appelle cela **lazy evaluation**
```scala
lazy val x = expr
```
- Ainsi dans les stream le tail est implémenté avec une lazy val
- Donc si on pose lazy val testStream = Stream(1,2,3,4) avec l'action testStream.take(3).toList qui donne List(1,2,3) le second appel ne nécéssitera pas de calcul.

### Computing with Infinite Sequences
- Avec les stream on peut définir des objets infinis puisqu'on a pas la nécessité d'évaluer ceux-ci
```scala
def from(n: Int): Stream[Int] = n #:: from(n+1)
//The Sieve of Eratosthenes
def sieve(s: Stream[Int]): Stream[Int] = s.head #:: sieve(s.tail filter (_ % s.head != 0))
val primes = sieve(from(2))
//To see the list of the first N prime numbers, you can write
(primes take N).toList
```