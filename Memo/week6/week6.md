# Week 6
###
### Other collection

- On a vu que les list sont linéaire mais accéder au head est bien plus rapide que accéder au last
- **Vector** :
- Sequence alternative au list
- Les accès aux élements sont plus équitable que pour les listes head vs last
- '''Scala val nums = Vector(1, 2, 3, -88) '''
- Les vectors supportent les mêmes opérations que les listes sauf ::, on peut utiliser à la place :
 - x +: xs : On crée un nouveau vector avec x premier élement
 - xs +: x : On crée un nouveau vector avec x dernier élement
- Les vectors sont des Array de 32 élements
- Si un vectors est plus large que 32 élements on peut placer des Array de 32 élements dans les 32 premiers élements
- Avec la formule log10(N)/log10(32) on obtient la profondeur du vecteur avec N le nombre d'élement
- Avec math.pow(32,x) on obtient le nombre d'élement pour une profondeur x
- Vectors on accède plus rapidement au dernier élement grâce aux index  (la complexité augmente bien moins vite qu'avec une liste)
- On accède plus lentemment au head avec Vector car on doit parcourir des Array vs List
- **Pour les actions récursives head/tail les list sont préférables pour les bulk operation map,filter,... les vectors sont preferable**
- Vector et List sont des sous-classe de sequence, qui est elle même une sous classe de iterable
- **Array** et **String** sont des sous classe de sequence, donc ils supportent les opérations de sequence (map, filter,...)
- **Range** est également une sous classe de sequence :
- to (inclusive), until (exclusive), by (to determine step value)
- val r: Range = 1 until 5 => 1,2,3,4
    - val s: Range = 1 to 5 => 1,2,3,4,5
- Les opérations de sequence :
- **xs exists p** true if there is an element x of xs such that p(x) holds, false otherwise.
- **xs forall** p true if p(x) holds for all elements x of xs, false otherwise.
- **xs zip** ys A sequence of pairs drawn from corresponding elements of sequences xs and ys.
- **xs.unzip** Splits a sequence of pairs xs into two sequences consisting of the first, respectively second halves of all pairs.
- **xs.flatMap** f Applies collection-valued function f to all elements of xs and concatenates the results
- **xs.sum** The sum of all elements of this numeric collection.
- **xs.product** The product of all elements of this numeric collection
- **xs.max** The maximum of all elements of this collection (an Ordering must exist)
- **xs.min** The minimum of all elements of this collectio
- def foreach(f: (A) ⇒ Unit): Unit ==> This means that it's designed to execute functions with side-effects.

### Combinatorial seach and For-expressions
- Lorsque des opérations nécéssite des boules on peut utiliser des high order fonction sur les séquences
- Parfois map, filter, Fold sont très pratique mais rendent le code très difficile à comprendre alors les For-expressions peuvent aider
```scala
def isPrime(x : Int):  Boolean = { (2 until x) forall (y => x%y!=0) }

def methode1(n :Int) = {
   (1 until n) flatMap ( x => (1 until x) map  (y=> (x,y))) filter (pair =>isPrime(pair._1+pair._2))
}
def methode2 (n : Int)= for{
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i,j)


xs flatMap f = (xs map f).flatten

```
- Dans un langage imperatif, for-expressions est équivalent à une boucle for
- **for ( s ) yield e** :
- s : est une sequence de generateur et de filtre
 - **generateur**: forme p<-e avec p un pattern et e une expression dont la valeur est une collection
 - **filter** : forme avec if
 - la sequence s doit commencer par un générateur
- e : est une expressions avec la valeur retourné par une itération

### Combinatorial Search example

- **Sets** : est une collection scala sous classe de iterable
- La plupart des opérations sur les sequences sont aussi disponibles (map, filter, ...)
- **Sets** vs sequence :
1. Les sets n'ont pas d'ordre
2. Les sets n'ont pas de doublons, seulement des élements uniques
3. La méthode de référence est la méthode contain, Set(1,3,4)(4) = true => Le set contient bien la valeur 4
- Example N-Queens
- On souhaite placer un nombre n de queens sur un chessboard (n x n)
- Aucune queen doit être sur la même colonne, sur la même ligne ou la même diagonale
- Solution :
 - On suppose qu'on a déja généré les solutions pour placer k-1 queens sur un chessboard de n
 - Chaque solution est composé de la liste des colonnes par ordre de k-1 queen, k-2 queen, ...
 - On ajoute la k queen avec toutes les extensions possibles à partir des solutions déja existantes
```scala
def Queens(n:Int) : Set[List[Int]] = {
 
  def placeQueen(k:Int) : Set[List[Int]] = {
   
    if(k==0) Set(List())
    else for{
      queens <- placeQueen(k-1)
      col <- 0 until n
      if isSafe(col, queens)
    } yield col :: queens
   
  }
 
  def isSafe(col:Int,queens:List[Int]): Boolean = {
   val row = queens.length
   val queensWithRow = (row - 1 to 0 by -1) zip queens
   queensWithRow forall {
case(r,c) => col!=c && math.abs(col-c)!=row -r
   }
  }

  placeQueen(n)
}
```

### Map

- Nouvelle collection, Map, très spéciale car à la fois une fonction et un iterable
- Format Map : [Key,Value]
```scala
val romanNumerals = Map("I"-> 1, "V" -> 5,"X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Switzerland" -> "Bern")
```
- Map est une sous classe d'iterable
- Map est une fonction avec capitalOfCountry("US") return Washington avec le type Key -> Value
- Map(key) si la key n'existe pas on obtient une erreur java.util.NoSuchElement
- Map get key :
- None : si la map ne contient pas key
- Some(x) : Si map associe une valeur à la key
- **Option** : Option support des opérations d'autres collection, et surtout on peut utiliser le pattern matching
```scala
trait Option[+A]
case class Some[+A](value: A) extends Option[A]
object None extends Option[Nothing]
```
```scala
val test : List[Option[Int]] = List(None, Option(2),Option(3),None)
test map {
  case Some(x) => x
  case None => 0
}
```
- **OrderBy** :
- SortWith : aList sortWith(_.length<_.length)
- sorted : aList.sorted
- **GroupBy** : Retourne une Map
- **Default Values** :  Si une key est absente on leve une exception donc on peut définir une valeur par défaut pour lever cette
valeur plutot qu'une exception
```scala
val cap1 = capitalOfCountry withDefaultValue "<unknown>"
cap1("Andorra") // "<unknown>"
```
- Lorsqu'on utilise une Map en argument cela peut rapidement devenir un inconvénient
- On peut transformer l'argument en ne mentionnant plus le Map tel que :
```scala
Polynom(Map(1->2,2->3))
class Polynom(term:Map[Int,Double]){
...
def this(bindings: (Int, Double)*) = this(bindings.toMap)
...
}
Polynom(1->2,2->3)
```
### Putting the pieces together

```scala
val mnemonics = Map(
’2’ -> ”ABC”, ’3’ -> ”DEF”, ’4’ -> ”GHI”, ’5’ -> ”JKL”,
’6’ -> ”MNO”, ’7’ -> ”PQRS”, ’8’ -> ”TUV”, ’9’ -> ”WXYZ”)
```
- Créer une fonction translate(phoneNumber) pour obtenir le mot/phrase correspondant à ces chiffres (exemple “7225247386” -> "Scala is fun")
- 100 lignes de code pour les langages de scripting
- 200-300 lignes de code pour les autres langage
- Les collections immutables Scala sont 
    - Simple à utiliser : peu d'étape
    - Concise : un mot remplace une loop for-expressions
    - Sécurisé : Type checker
    - Rapide 
    - Universel : un vocabulaire pour toutes les collections




