# Week 7
###
### Queries with For

- For expressions sont très pratiques pour requêter de la data (Queries)
- Plusieurs exemples dans la vidéo sur databases
```scala
for {
 b1 <- books
 b2 <- books
 if b1 != b2
 a1 <- b1.authors
 a2 <- b2.authors
 if a1 == a2
} yield a1
```

### Translation of For  
- On peut exprimer les fonctions high-order suivantes avec for-expressions
```scala
def mapFun[T, U](xs: List[T], f: T => U): List[U] = for (x <- xs) yield f(x)
def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U] = for (x <- xs; y <- f(x)) yield y
def filter[T](xs: List[T], p: T => Boolean): List[T] = for (x <- xs if p(x)) yield x
```
- Traduire une simple expression for avec map
```scala
for( x <- e1) yield e2
e1.map( x => e2)
```
- Traduire une expression avec une condition if
```scala
for( x <- e1 if f; s) yield e2
for (x <- e1.withFilter(x => f); s) yield e2
```
- Traduire une for-expression avec 2 loop
```scala
for (x <- e1; y <- e2; s) yield e3
e1.flatMap(x => for (y <- e2; s) yield e3)
```
- *withFilter* est comme Filter mais il ne produit pas de liste intermiédiaire
- Exemple
```scala
for {
 i <- 1 until n
 j <- 1 until i
 if isPrime(i + j)
} yield (i, j)
 
(1 until n).flatMap(i => (1 until i).withFilter(j => isPrime(i+j)).map(j => (i, j)))
```
- La translation des for-expression n'est pas limitée aux collections, elle peut être utilisée pour tous les types avec les fonctions map,flatMap,withFilter
-**Les for-expressions sont très utiles et pas seulement pour les collections**

### Functionnal Random Generators
- Random value generators offre les interprétations map,flatMap,withFilter
```scala
trait Generator[+T] {
 self =>
 def generate: T
 
 def map[S](f: T => S): Generator[S] = new Generator[S] {
  def generate = f(self.generate)
 }
 def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
  def generate = f(self.generate).generate
 }
}
val integers = new Generator[Int] {
 val rand = new java.util.Random
 def generate = rand.nextInt()
}


def single[T](x: T): Generator[T] = new Generator[T] {
 def generate = x
}
```
- Self est un alias assez pratique, il s'agit d'un alias pour this (exemple ci-dessous), avec this on aurait créé une loop infinie
- Avec les nombres aléatoires on peut créer des tests qui deviennent des **postcondition** => une propriété sur les résultats attendus
- On utilise un générateur de valeurs aléatoires et on teste notre fonction x fois sur ces nombres
- On peut utiliser directement la library **ScalaCheck** pour réaliser cette approche https://github.com/rickynils/scalacheck/blob/master/doc/UserGuide.md
    - La library permet d'implémenter des propriétés soit standalone, soit dans les tests
    - On peut créer des proprietés testées x fois, puis scalaCheck donne la modalité pour laquelle il y a eu une erreur si il y'en a une
    - On peut créer des propriétés qui utilisent des générateurs custom
    - On peut combiner des proprietés
    - On peut labéliser les propriétés pour mieux comprendre la raison de l'échec
 
### Monads
- Un **Monads** est une data structure avec les implémentations map et flatMap
```scala
trait M[T] {
 def flatMap[U](f: T => M[U]): M[U]
}
def unit[T](x: T): M[T]
```
- On parle de **Monads with Zero** lorsque withFilter est implémenté
- **Monoid** : est un any type qui respecte la règle (a op b) op c == a op (b op c) (par exemple les int)
- List is a monad with unit(x) = List(x)
- Set is monad with unit(x) = Set(x)
- Option is a monad with unit(x) = Some(x)
- Generator is a monad with unit(x) = single(x)
- Pour les monads, *map* est défini comme la combinaison de Unit & flatMap => m map f == m flatMap (x => unit(f(x)))
- Les monads doivent respecter des régles précises :
    - Associativité  : m flatMap f flatMap g == m flatMap (x => f(x) flatMap g)
    - Left Unit  : unit(x) flatMap f == f(x)
    - Right Unit : m flatMap unit == m
- Ces règles permettent des simplifications lorsqu'on utilise des for-expressions sur les monads
    - Associativité : On peut écrire les for-expressions sur une ligne
    - Right Unit : for (x <- m) yield x == m
- Type Try ressemble au type Option sauf qu'on utilise les notions Success et Failure
```scala
abstract class Try[+T]
case class Success[T](x: T) extends Try[T]
case class Failure(ex: Exception) extends Try[Nothing]
Try(expr) // gives Success(someValue) or Failure(someException)
```
- Try n'est pas un monad car il ne respecte pas la règle left unit 