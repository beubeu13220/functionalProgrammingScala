 # Week 10
 ###
# Imperative Event Handling: The Observer Pattern
- Première vidéo avec l'exemple Subscriber/Publisher
- On parle d'observer pattern, très utilisé quand on a besoin d'avoir un modèle qui change au cours du temps
- On utiliser ces notions pour propager l'information d'un changement sur un compte dans l'exemple Bank Accounts
- Simple à mêttre en place
- Permet d'avoir plusieurs vues d'un même état
- Force à programmer de façon imperative, Unit-typed
- De nombreuses fonctions doivent être coordonnées

# Functional Reactive Programming
- Est une méthode de programmation impérative qui permet de traiter une séquence d'évenement dans le temps
- On aggrège une séquence d'évenements dans un signal
- **Signal** : Une valeur qui change au cours du temps
- Deux opérations fondamentales sur les Signal :
    - Obtenir la valeur à une date t avec ()
    - Définir un signal en fonction des autres signaux
- Signal(Value) est constant dans le temps
- Var(Value) est un signal qui peut varier dans le temps notamment avec l'option update
- La différence forte entre une variable mutable et un signal est que la relation entre deux signaux est maintenue lorsqu'on modifie un des signaux
- Attention dans la définition des signaux
    - S() = S() + 1 => aucun sens car on définit S tel que S est toujours plus grand de 1 à travers le temps (infinite loop)
    - a = b + 1 et b = a * 3 => Cyclic dependency
 
# Latency as an Effect  
- 4 effets en programmation :
    - Processsus synchrone :
        - Un : Try
        - Plusieurs : Iterable
    - Processus Asynchrone :
        - Un : Future
        - Plusieurs : Observable
- Les effets de latence sont parfois peu parlant car en nanoseconde
- Il faut comprendre que des nanosecondes traduits en jours peuvent démontrer que des nanosecondes prennent trop de temps => Latence
- Sur des process asynchrone sans les futures on introduit de la latence également (cf exemple send parquet)
- **Future** est un monad qui permet de gérer les exceptions et la latence
- Un Future est un monad avec un callback qui permet d'avoir une information lorsque l'action à reussi ou à échouer
- An object is a closure with multiple methods.
- A closure is an object with a single method. 