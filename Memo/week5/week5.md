# Week 5
###
### More Functions on lists

**List Methods**
- .head / .tail : Premier éléments / Tous les élements sauf le premier (complexité : constante time)
- xs.length : Longueur de la liste
- xs.last  : Le dernier élément (complexité : proportionnelle à la taille de xs n)
- xs.init  : Tous les élements saut le dernier
- xs take n : Les n premiers éléments de xs
- xs drop n : Le reste de xs après avoir enlevé n élements
- xs(n)  : L'élement à l'index n de la liste xs
- Created New list
- xs ++ ys  like (:::) : Une liste avec tous les éléments de xs suivie de tous les élements de ys (complexité : proportionnelle à la taille xs n)
- xs.reverse  : Tous les élements de xs dans l'ordre inverse (Complexité : avec solution simple n², on parcourt xs puis le concat en complexité n)
- xs updated (n, x) : La liste xs avec l'élement n remplacé par la valeur x
- xs :: ys : Une nouvelle liste où xs est le premier élement de la nouvelle liste, et les autres élements la liste ys
- Finding Elements
- xs indexOf x  : L'index de l'élement x dans la liste xs, l'index de la première occuenre sinon -1
- xs contains x :  Pareille que (xs indexOf x)>= 0, il existe un index pour la variable recherché

```Scala
def flatten(xs: List[Any]): List[Any] = xs match {
  case Nil => Nil
  case (x::xs) :: ys =>   (x :: flatten(xs)) ::: flatten(ys)
  case x :: xs => x :: flatten(xs)
}
```

### Pairs and Tuples

- Implémentation de **merge sort** (tri fusion), plus éfficace qu' insertion sort :
- Separer la liste en deux sous liste avec chacune d'entre elle qui contient la moitié des éléments
- Trier les deux listes
- Merge les deux listes
- Complexité O(n log n)
```Scala
def msort(xs: List[Int]): List[Int] = {
val n = xs.length/2
if (n == 0) xs
else { //Avec la version pattern matching sur les pairs (v2)
def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs,ys) match {
    case (xs, Nil) => xs
    case (Nil, ys) => ys
    case (x::xs1, y::ys1) => if(x<y) x :: merge(xs1,ys)  else y :: merge(ys1,xs)
 }
val (fst, snd) = xs splitAt n
merge(msort(fst), msort(snd))
}
}
```
- **splitAt** retourne deux sous listes sous forme de **pair**, avec la premier liste de longueur du paramètre n de splitAt
- **pair** s'écrit (x,y) en scala. On peut aussi utiliser pattern matching sur les pair
- **tuples** il s'agit de pair avec plus de deux élements donc même fonctionnement que les pairs
- On peut accéder aux élements tuples/pair avec _1,_2, ...
```Scala
val (label,value) = pair //Pattern matching, is prefered
val label = pair._1
val value = pair._2
```

### Implicit Parameters

- Objectif rendre des fonctions plus générale, par exemple msort en List[Int] to List[T]
- Il faut parametriser la merge avec la bonne fonction de comparaison
- def msort[T](xs: List[T])(lt : (T,T)=> Boolean): List[T] : On ajoute en paramètre une fonction lt qu'on va utiliser pour la comparaison
- Il existe une class **scala.math.Ordering[T]** pour comparer les élements de type T. On utilise ensuite l'opération lt
- Définir Ordering ou une fonction lt est très lourd. Ainsi on peut utiliser la notion **implicit**
- Le compiler comprend seule de quelle fonction Ordering il s'agit à partir du type
- Le compiler cherche une fonction implicit tel que
    -   Elle est définit implicit
    -   Elle est compatible avec le Type
    -   Elle est définit dans l'objet ou elle est visible au moment de l'appel

### Higher-Order List Functions
- Map : Une fonction pour appliquer une fonction/transformation à chaque élement
```Scala
//La véritable implémentation est plus complexe
def map[U](f: T => U): List[U] = this match {
case Nil => this
case x :: xs => f(x) :: xs.map(f)
}
```
- Filtering : Filter une liste avec les élements qui respectent les conditions d'une fonction
```Scala
def filter(p: T => Boolean): List[T] = this match {
case Nil => this
case x :: xs => if (p(x)) x :: xs.filter(p) else xs.filter(p)
}
```
- xs filterNot p : Fonction similaire à Filter mais avec la négation
- xs partition p : Similaire à Filter et FilterNot, partition en deux sous listes
- xs takeWhile p : On conserve les élements d'une liste tant que la condition est respectée
- xs dropWhile p : On conserve les élements d'une liste dès que la condition n'est plus respectée
- xs span p : Fusion entre takeWhile & dropWhile

### Reduction of lists

- ReduceLeft : Réalisation une suite d'opération entre les élements d'une liste en partant de la gauche
- ReduceLeft ne fonctionne pas pour une liste vide, dans ce cas utiliser FoldLeft
- FoldLeft est la fonction générique de ReduceLeft avec un accumulateur z qui représente la valeur initiale 
```Scala
def sum(xs: List[Int]) = (0 :: xs) reduceLeft ((x, y) => x + y)
def sum(xs: List[Int]) = (0 :: xs) reduceLeft ( _  +  _)
def sum(xs: List[Int]) = (xs foldLeft 0) (_ + _)

def reduceLeft(op: (T, T) => T): T = this match {
  case Nil => throw new Error('Nil.reduceLeft')
  case x :: xs => (xs foldLeft x)(op)
}

def foldLeft[U](z: U)(op: (U, T) => U): U = this match {
  case Nil => z
  case x :: xs => (xs foldLeft op(z, x))(op)
}
```
- Il existe les mêmes implémentations avec Right : ReduceRight, FoldRight
- Avec Right on commencera nos opérations par la fin de la liste et non par le début comme avec Left
```Scala
def reduceRight(op: (T, T) => T): T = this match {
  case Nil => throw new Error('Nil.reduceRight')
  case x :: Nil => x
  case x :: xs => op(x, xs.reduceRight(op))
}
def foldRight[U](z: U)(op: (T, U) => U): U = this match {
  case Nil => z
  case x :: xs => op(x, (xs foldRight z)(op))
}
```
- FoldLeft et FoldRight sont équivalent lorsque l'opération est associative et commutative 
