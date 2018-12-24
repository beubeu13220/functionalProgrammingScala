# Week 2
###
### Higher-Order Functions 
- Les fonctions sont considérées comme **first-class values**
- Cad que toutes les fonctions peuvent être utilisées comme des paramètres et retourner un résultat
- Une fonction qui prend en paramètre une fonction ou bien retourne une fonction est appelée **high order functions**
- **anonymous functions** : ecrire une fonction sans lui donner de nom exemple : (x: Int) => x * x * x
- Dans une fonctione anonyme on peut omettre le type return si il peut être inféré dans le contexte 
- **syntactic sugar**

### Currying
La curryfication est la transformation d'une fonction à plusieurs arguments en une fonction à un argument qui retourne 
une fonction sur le reste des arguments. La curryfication permet de créer des fonctions pures. L'opération inverse est
 possible et s'appelle la décurryfication
```scala
 def sum(f: Int => Int): (Int, Int) => Int = {
     def sumF(a: Int, b: Int): Int =
     if (a > b) 0
     else f(a) + sumF(a + 1, b)
 sumF
 }
```
- La fonction retourne une fonction, fonction qu'on appliquera sur (Int,Int)
- def f(args1)...(argsn−1)(argsn) = E is equivalent to def f = (args1 ⇒ (args2 ⇒ ...(argsn ⇒ E)...))
   
### Example: Finding Fixed Points 
```scala
val tolerance = 0.0001
def isCloseEnough(x: Double, y: Double) = abs((x - y) / x) / x < tolerance
def fixedPoint(f: Double => Double)(firstGuess: Double) = {
def iterate(guess: Double): Double = {
val next = f(guess)
if (isCloseEnough(guess, next)) next
else iterate(next)
}
def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)
```

### Scala Syntax Summary
- Extended Backus-Naur form (EBNF), where :
    - | denotes an alternative,
    - [...] an option (0 or 1),
    - {...} a repetition (0 or more)
- Un type peut être :
    - Numeric
    - Boolean
    - String
    - Function type 
- Une expression peut être :
    - An identifier such as x, isGoodEnough,
    - A literal, like 0, 1.0, ”abc”,
    - A function application, like sqrt(x),
    - An operator application, like -x, y + x,
    - A selection, like math.abs,
    - A conditional expression, like if (x < 0) -x else x,
    - A block, like { val x = math.abs(y) ; x * 2 }
    - An anonymous function, like x => x + 1.
- Une définition peut être :
    - A function definition, like def square(x: Int) = x * x
    - A value definition, like val y = square(2)
- Un paramètre peut être :
    - A call-by-value parameter, like (x: Int),
    - A call-by-name parameter, like (y: => Double).

### Functions and Data
- Cour pour créer des data structure 
- Exemple sur les nombres rationnels
- Plutot qu'utiliser des fonctions on crée une nouvelle class pour avoir un nouveau type rationnel
- scala conserve les noms des types et des valeurs dans deux namespasce distinct donc pas de risque de conflit
- on peut créer un nouvel object avec la commande new par exemple new Rationnal(1,2)
- Les fonctions dans la class sont appelées des méthodes
- **override** signifie qu'on définit une méthode qui existe déja (par défaut dans the class java.lang.Object) 

### More Fun with Rationals
- private val/def : on peut accéder seulement à ces méthodes à l'intérieur d'une class
- La capacité à choisir différentes implémentations de la structure data sans affecté la class est appelée **data abstraction** 
- **this** dans classe représente l'object actuelle sur lequelle la méthode est appliquée
- **that** un autre object sur lequel on applique une méthode
- **require** is a predefined function : permet de tester des conditions sur la class, is used to enforce a precondition on the caller of a function.
- **assert** is used as to check the code of the function itself.
- **Constructors**, en scala une classe indroduit implicitement un constructeur appelé **primary constructor** 
- Scala permet aussi de déclarer d'autres constructeurs **auxiliary constructors**
```scala
def this(x: Int) = this(x, 1)
```

### Evaluation and Operators
- Réecriture des objects 
    - new Rational(1, 2).numer
    - [1/x, 2/y] [] [new Rational(1, 2)/this] x
    - = 1
    - new Rational(1, 2).less(new Rational(2, 3))
    - → [1/x, 2/y] [newRational(2, 3)/that] [new Rational(1, 2)/this]
    - this.numer * that.denom < that.numer * this.denom
    - = new Rational(1, 2).numer * new Rational(2, 3).denom <
- N'importe quelle méthode en scala avec un paramètre peut être écrite comme infix opérator
    - r add s au lieu de r.add(s)
    - r less s au lieu de r.less(s)
- En scala les opérateurs peuvent etre utilisées comme identifieurs 
    - Alphanumeric/ Symbolic/ Underscore
```scala
def + (r: Rational) = new Rational(numer * r.denom + r.numer * denom,denom * r.denom)
def - (r: Rational) = ...
def * (r: Rational) = ...
```
- L'ordre de priorité en scala est déterminé par le premier charactère 
- Ordre croissant des priorités :
    - (all letters) (the lowest)
    - |
    - ^
    - &
    - < >
    - = !
    - :
    - + -
    - * / %
    - (all other special characters) (the highest)