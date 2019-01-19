# Week 1
###
### Programming paradigms
Plusieurs paradigmes de programmation :
- Imperatif :
    - Modification des variables mutables
    - assignements
    - Control structure comme if-else, loops, break, continue, ...
    - Limité
- Fonctionnelle :  
est un paradigme de programmation de type déclaratif qui considère le calcul en tant qu'évaluation de fonctions mathématiques.
    - Restrictif : sans variable mutable, sans assignement, sans loops, ...
    - plus largement : focus sur les fonctions
    - en particulier : les fonctions peuvent etre des valeurs, composées, consommées
    
### Elements of Programming  
- On évalue la variable le plus à gauche
- puis l'opération la plus à gauche
- puis on itère jusqu'a la valeur
Exemple **call-by-value** :
    - (2 * pi) * radius
    - (2 * 3.14159) * radius
    - 6.28318 * radius
    - 6.28318 * 10
    - 62.8318     
- Les fonctions sont évaluées comme les opérateurs (gauche à droite)
Exemple :
    - sumOfSquares(3, 2+2)
    - sumOfSquares(3, 4)
    - square(3) + square(4)
    - 3 * 3 + square(4)
    - 9 + square(4)
    - 9 + 4 * 4
    - 9 + 16
    - 25

- Le schema ci-dessous d'évaluation est appelé **"the substitution model"**
- L'idée est de réduire une expression à une valeur
- Le modèle de subsitution est formalisé dans lambda calculus
- Parfois la stratégie de réduction à la valeur ne fonctionne pas il faut alors changer de méthode d'évaluation
- Evaluation par nom  **call-by-name** : 
    -  sumOfSquares(3, 2+2)
    -  square(3) + square(2+2)
    -  3 * 3 + square(2+2)
    -  9 + square(2+2)
    -  9 + (2+2) * (2+2)
    -  9 + 4 * (2+2)
    -  9 + 4 * 4
    -  25
- **call-by-value** : Avantage on évalue les arguments d'une fonction qu'une fois
- **call-by-name** : les arguments d'une fonction ne sont pas évalués si ils ne sont pas utiles 


### Evaluation Strategies and Termination
- Si une évaluation par valeur est terminée alors l'évaluation par nom aussi
- La réciproque n'est pas vrai 
- **Evalution par défaut est call-by-value**
- Si un paramètre commence avec **=>** alors on utilise call-by-name
- Example : def constOne(x: Int, y: => Int) = 1

### Conditionals and Value Definitions
- !true --> false
- !false --> true
- true && e --> e
- false && e --> false
- true || e --> true
- false || e --> e
- || et && n'ont pas besoin de l'opérateur de droite pour être évalué
- *def* est évalué par **call-by-name**
- On a aussi *val* par valeur
- Exemple : 
    - val y = square(x)
    - def square(x: Double) = x * x
- La différence entre val & def est apparente lorsque l'évaluation n'est pas terminée
- L'expression def=loop est ok par contre val x = loop est infinie 
    
### Blocks and Lexical Scope
- Nested function : On split les étapes dans un fonction en plusieurs fonctions en définisant les fonctions auxiliaires 
à l'interieur de la fonction. ça évite aussi de polluer le namespace et que l'utilisateur ait accès à ces fonctions.
- Un block en scala est délimité par des {} : Il contient une séquence de définitions ou d'expressions
- le dernier élément d'un block est une valeur
- Dans un block une définition par exemple le x en paramètre est visible de partout, et signifie toujours la même chose. 
Exemple dans srqt() 

### Tail Recursion
- Une fonction appelle elle même en dernière action
- the function’s stack frame can be reused
- Il s'agit d'un processus itératif 
- In general, if the last action of a function consists of calling a function (which may be the same), one stack frame
 would be sufficient for both functions. Such calls are called tail-calls.
 - **Lorsqu'on souhaite avoir une fonction tail recursive on peut écrire @tailrec comme ça si ce n'est pas le cas on aura 
 une erreur**
