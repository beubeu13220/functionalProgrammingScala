 # Week 9
 ###
 # Function and State
- Jusqu'a maintenant on a travaillé sans side-effect, seulement des variables immutables
- Parfois, on a besoin d'avoir un état qui change dans le temps et donc d'avoir des variables mutables
- Pour définir une variable mutable on utilise **var**
- Un object qui a un état est un object qui dépend de son historique
- On peut parler d'object **Statefulness**

# Identity and Change
- On souhaite définir si 2 variables/objects sont les mêmes
- The same est défini par la propriété **operational equivalence** : *x and y are operationally equivalent if no possible test can distinguish between them*
- Procédure :
    - On réalise une opération avec x et y 
    - On réalise la même opération avec x et x 
    - Si les résultats ne sont pas similaires alors x et y ne sont pas similaires 
- Avec la classe mutable BankAccount, x deposit 20 + y withDraw 20 n'est pas similaire à  x deposit 20 + x withDraw 20
- *Avec la notion d'assignement (var) le modèle de substitution n'est plus valide*. Il ne permet plus de dire si x et y sont les mêmes comme avec des variables immutables
- On peut conserver la notion de modèle de substitution avec la notion de *store* mais cela est très complexe 

# Loops
- Les deux premiers cours permettent de voir comment combiner functional programming et imperative programming 
- On peut implémenter des boucles custom ou utiliser la built-in fonction *while*
- Lorsqu'on définit une WHILE loop il faut bien que la condition et la commande soit évalués à chaque appel, donc evalués by-name
- La WHILE loop est tail-recursive 
 ```scala
def WHILE(condition: => Boolean)(command: => Unit): Unit =
 if (condition) {
  command
  WHILE(condition)(command)
 }
 else ()
def REPEAT(command: => Unit)(condition: => Boolean) = {
 command
 if(condition)()
 else REPEAT(command)(condition)
}
 ```
- On a aussi accès à une notion for-loop similaire à celle de Java 
 ```scala
for (i <- 1 until 3) { System.out.print(i + ” ”) }
 ```
- Les boucles for en Scala se rapprochent de l'utilisation de Foreach, comme les for-expression se rapprochent des map/flatMap
- On peut donc transformer toutes ces loops en scala language 
