# <center> Proyecto UD5 SpringBoot + HIBERNATE + MongoDB </center>
### <center> Proyecto para el módulo de Acceso a Datos del curso 2º de DAM </center>


## Requisitos

Necesitas tener instalado en tu sistema:

- Java 8

## Ejecución

Puedes ejecutar la aplicación usando el _goal_ `run` del _plugin_ Maven 
de Spring Boot:

```
$ ./mvnw spring-boot:run 
```   

También puedes generar un `jar` y ejecutarlo:

```
$ ./mvnw package
$ java -jar target/mads-todolist-inicial-0.0.1-SNAPSHOT.jar 
```

Una vez lanzada la aplicación puedes abrir un navegador y probar la página de inicio:

- [http://localhost:8080/login](http://localhost:8080/login)


## Supuesto:
##### El proyecto consiste en una aplicación java que permita la gestión de una biblioteca. La biblioteca tiene una colección de libros, que se prestaran a los clientes. Los clientes tienen que estar dados de alta en el sistema antes de poder llevarse libros prestados.
### Las siguientes entidades se guardaran en una base de datos SQL ###
##### Los campos comunes de los libros son:
- ID: Identificador único del libro.
- ISBN: Código normalizado internacional para libros.
- Titulo: Título del libro.
- Fecha de publicación: Fecha de publicación del libro.
- Editorial: Editorial del libro.
- Longitud de impresión: Longitud de páginas del libro.
- Lista de préstamos: Guarda todos los préstamos en los que fue incluido el libro.

### Los libros se pueden dividir en tres tipos:
#### Novelas:
Sus campos unicos son:
- Autor: Autor de la novela.
- Tema: Tema de la novela.
- Subgénero: Subgénero de la novela.

#### Mangas:
Sus campos unicos son:
- Autor: Autor del manga.
- Demografía: Demografía del manga.
- Género: Género del manga.
- Color: Indica si el manga está a color o no.

#### Revistas:
Su campo único es:
- Tipo: Tipo de la revista.

##### Para los clientes guardaremos los siguientes datos: 
- ID: Identificador único del cliente.
- Nombre: Nombre del cliente.
- Apellido: Apellido del cliente.
- DNI: DNI del cliente.
- Dirección: Dirección del cliente.
- Edad: Edad del cliente.
- Lista de préstamos: Guarda todos los préstamos del cliente.

##### Para la dirección usaremos un objeto incrustado con los siguientes campos: 
- Calle: Calle de la dirección.
- Número: Número de la dirección.
- País: País de la dirección.
- Ciudad: Ciudad de la dirección.
- Provincia: Provincia de la dirección.
- Código postal: Código postal de la dirección.

##### Para los préstamos guardaremos los siguientes datos: 
- ID: Identificador único del préstamo.
- Fecha de inicio: Fecha de inicio del préstamo.
- Fecha de límite: Fecha límite del préstamo.
- Fecha de devolución: Fecha de devolución del préstamo.
- Devolución: Indica si el préstamo ha sido devuelto.
- Lista de libros: Guarda todos los libros que se prestaron.
- Cliente: Cliente al que se le prestaron los libros.

### Las usuarios se guardaran en una base de datos MongoDB 
##### Para los usuarios guardaremos los siguientes datos: 
- ID: Identificador único del usuario.
- Email: Email del usuario.
- Contraseña: Contraseña del usuario.
- Nombre: Nombre del usuario.
- Fecha de nacimiento: Fecha de nacimiento del usuario.
- Administrador: Indica si el usuario es administrador o no.
- Cliente: Indica el cliente al que pertenece el usuario.

## Diagramas:
![Diagrama de clases](images/biblioteca.png)
![CFM](images/biblioteca2.png)
![Mongo](images/usuarioMongo.png)

## Propuestas de mejora:
- Controlar la entrada de datos para que corresponda con el tipo de dato que se espera. (Por ejemplo, no se puede introducir una edad negativa, o no se puede introducir una fecha de nacimiento mayor a la actual etc...)
- Añadir búsqueda
- Añadir opcion para crear un nuevo administrador
- Añadir opcion para crear asociar un cliente a un usuario desde el programa
