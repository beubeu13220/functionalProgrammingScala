# Week 4
###
### Object Everywhere

- Un pure langage orient objet est un language où toutes les valeurs sont des objets
- Slides avec implémentation de classe pour les types primitifs Int, Boolean, ...
- Ref : unary
```scala
abstract class Boolean {
    def ifThenElse[T](t: => T, e: => T): T
    def && (x: => Boolean): Boolean = ifThenElse(x, false)
    def || (x: => Boolean): Boolean = ifThenElse(true, x)
    def unary_!: Boolean = ifThenElse(false, true)
    def == (x: Boolean): Boolean = ifThenElse(x, x.unary_!)
    def != (x: Boolean): Boolean = = ifThenElse(x.unary_!, x)
}
```
### Functions as Objects

- En scala les fonctions sont traitées comme des objets
```scala
trait Functions1[A,B]{
 def apply(x:A):B
}
```
- Donc les fonctions sont des objets avec une méthode apply
```scala
(x : Int) => x * x
new Functions1[Int,Int]{
def apply(x:Int) = x*x
}
```


### Subtyping and generics

```scala
def assertAllPos[S <: IntSet](r: S): S = ...
```
- “<: IntSet” is an **upper bound** of the type parameter S
- On peut utiliser des types seulement conforme à IntSet donc NonEmpty, Empty
- S <: T means: S is a subtype of T
- S >: T means: S is a supertype of T, or T is a subtype of S.
- **lower bound** [S >: NonEmpty], on peut utiliser seulement des supertypes de NonEmpty donc soit
NonEmpty, IntSet, AnyRef, Any
- **Mixed Bound** : [S >: NonEmpty <: IntSet]
- **Covariance** : Types sont covariant lorsque la relation entre subtype et supertype est respectée avec un autre type parameter
```scala
NonEmpty <: IntSet
List[NonEmpty] <: List[IntSet]
```
- **Liskov Substitution Principle** : If A <: B, then everything one can to do with a value of
type B one should also be able to do with a value of type A.
- Les Array ne sont pas covariant en Scala (Mutable)
- Les list sont covariant (Immutable)

### Variance
- Les types immutable peuvent être covariant
- Les types mutable ne peuvent pas être covariant
- C is a parameterized type
- C[A] <: C[B] C est covariant
- C[A] >: C[B] C est contravariant
- Neither C[A] nor C[B] C is nonvariant
- class C[+A] { ... } C covariant
- class C[-A] { ... } C contravariant
- class C[A] { ... } C nonvariant
- Variance check :
- Covariant type parameters can only appear in methode result
- Contravariant type can only appear in method parameters
- Invariant type can appear anywhere

### Decomposition
- Demonstration avec Expression que ça devient très rapidement fastidieux avec notre hiérarchie d'ajouter des classes
- Extend hierarchy : method grow quatradiquement
- non solution : On utilise TypeTest and TypeCast pour ne plus à avoir a implémenter les méthodes isNumber, isEval, ... on peut checker rapidement le type de l'objet. Donc plus besoin de classification methode. Low level and unsafe solution
- isInstanceOf : Check si l'objet est de type T et renvoie un boolean
- asInstanceOf : Essaie de caster l'objet dans le type T sinon lève une exception si le cast n'est pas possible
- Il faut prendre des précautions avec TypeCast car on peut rapidement avoir une exception dans notre code
- Solution : Object Oriented Decomposition. Cette solution ne fonctionne pas toujours, parfois on a besoin de modifier chaque classe avec cette approche

### Pattern Matching
- Un bon outil pour la décomposition
- **case class** est similaire au class normal sauf qu'il y a le prefixe case devant. Le prefixe case ajoute des fonctionnalitées à la classe  
- Pattern matching is a generalization of switch from C/Java to class hierarchies.
- match is followed by a sequence of cases, pat => expr.
- Each case associates an expression expr with a pattern pat.
- A MatchError exception is thrown if no pattern matches the value of the selector.
- variables doivent toujours commencer par une miniscule
- Constantes commencent par une lettre majuscule
- Le matching des conditions est éffectué dans l'ordre où on a écrit match case

### Lists
- Les list sont une structure fondamentale dans la programmation fonctionelle
- Les list sont immutable (Les élements ne peuvent être changé vs Array)
- Les list sont récursive (Les array sont flat)
- Les list sont homogènes : tous les élements d'une liste sont du même type
```scala
val fruit: List[String] = List("apples", "oranges", "pears")
val nums : List[Int] = List(1, 2, 3, 4)
```
- En Scala : Nil fait référence à une liste vide
- l'opération x :: xs entre deux liste renvoie : une liste avec le premier élement de x  et les élements de xs, commme avec la classe cons (Head, tail)
```scala
fruit = "apples" :: ("oranges" :: ("pears" :: Nil))
nums = 1 :: (2 :: (3 :: (4 :: Nil)))
nums = 1 :: 2 :: 3 :: 4 :: Nil
```
- On peut omettre les parenthèses avec ::, scala interprete directement avec les parenthèses
- **insertion sort** : Complexité n*n (quatratic)
```scala
def insert(x: Int, xs: List[Int]): List[Int] = xs match {
case List() => List(x)
case y :: ys => if(x<=y) x::xs else y::insert(x,ys)
}
def isort(xs: List[Int]): List[Int] = xs match {
case List() => List()
case y :: ys => insert(y, isort(ys))
}
```
- Compléxité d'insertion sort : N*N avec N la longueur de la liste