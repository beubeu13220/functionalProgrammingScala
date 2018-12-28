# Week 3
###
### Class Hierarchies
- ** Abstract Classes ** : est une classe qui peut contenir des membres sans implémentation, on ne peut créer une nouvelle instance avec new
- ** Abstract Classes ** : permet ensuite de créer des extensions **extend** à cette classe, les classes extend doivent être conforme à l'abstract classe
- Lorsqu'on crée une classe à partir d'une abstract classe on parle de **superclass** et de **subclasse** pour la classe créée à partir de l'abstract classe
- La superclasse par défaut est la classe object (in the Java package java.lang)
- Dans les subclasse on implémente les abstract fonctions non définies
- On peut aussi redéfinir des fonctions avec la commande **override**
- **object** : On peut utiliser object lorsqu'une classe est créée plusieurs fois alors qu'il s'agit toujours de la même, exemeple new Empty devient Empty, On parle de **singleton object**
- standalone applications, on crée une fonction main dans un object. Une fois le programme compilé il suffit de faire scala le nom de l'object
- **dynamic method dispatch** : This means that the code invoked by a method call depends on the runtime type of the object that contains the method. Dynamic dispatch of methods is analogous to calls to higher-order functions.

### How Classes are Organized

- classes et object sont organisés dans des packages
```scala
package progfun.examples
object Hello { ... }
```
- Pour accéder à l'object : import progfun.examples.Hello (fully qualified name)
- named imports : import week3.Rational ou import week3.{Rational, Hello}
- wildcard import : import week3._
- Automatic Imports : All members of package scala, All members of package java.langAll members of the singleton object scala.Predef
- On peut définir une abstract classe comme un **trait**. Simplement on remplace abstract classe par trait
- En scala, une classe peut avoir seulement une superclasse mais elle peut hériter de plusieurs trait donc on peut utiliser les traits pour conformer une classe à plusieurs types
- Les traits ne peuvent pas avoir de valeurs/paramètres seulement les classes peuvents
- Any the base type of all types
- AnyRef The base type of all reference types Alias of java.lang.Object
- AnyVal The base type of all primitive types Int, Double, Boolean ...
- Nothing est sous type de tous les autres types, il n'a pas de valeur. Le type nothing est notamment le type des exceptions
- Le type de null est Null. Null est une sous classe de tous les objects par contre il est incompatible avec les AnyVal

### Polymorphism

- Une strucutre importante dans les langages fonctionnel est la liste lié immutable. Elle est construit en deux block : Nil & Cons
- On peut utiliser **val** directement dans la définition des paramètres de classe. Ainsi, on a pas a définir la variable dans le block de la classe
- En scala on peut définir/généraliser une définition avec **Type Parameters**. Ainsi, la classe ou def existe pour plusieurs type (Int, Double, ...)
```scala
class Cons[T](val head: T, val tail: List[T]) extends List[T]
def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
```
- On peut ensuite appeler la fonction avec notre type singleton[Int](1) / singleton[Boolean](true) ou bien scala arrive à inférer le type dans le contexte singleton(1)/singleton(true)
- **type erasure** : Le type n'affecte pas l'évaluation, all type parameters and type argument are removed before evaluating the program
- **Polymorphism** : Une fonction provient de plusieurs formes
- Une fonction peut être appliquée à plusieurs types par exemple Int, Double, Boolean
- Un type peut être une instance de plusieurs types exemple List provient de Nil & Cons
- Polymorphism generics : une classe ou une fonction est créée par type parameterization
- Polymorphism subtyping : Exemple des List avec Cons & Nil sous la superclasse List
