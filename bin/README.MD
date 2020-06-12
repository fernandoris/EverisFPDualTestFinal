# Test Final - FP Dual / Everis

Buenas llegados a este punto debéis afontar la prueba final.
En este repositorio tenéis un proyecto de un microservicio montado en springboot, que realizará (cuando creéis la entity de forma correcta) una carga de datos en una base de datos en memoria. 
La aplicación posee 3 urls importantes, deberéis conseguir que funcionen las dos últimas.

  - http://localhost:8080/h2-console  Para consultar la base de datos (JDBC URL -> jdbc:h2:mem:test, User Name -> root, Password -> root)
  - http://localhost:8080/getexcel Debe descargar un excel con los datos de la base de datos.
  - http://localhost:8080/addusersfromcsv?filename=usuarios debe cargar los datos del fichero src/main/resources/{filename}.csv en la base de datos.
# Tareas a realizar!

  ### PRIMERO 
  - Haz fork de este reposoitorio y clónate en local el proyecto forkeado. 
  - No olvides hacer commits frecuentes y crear ramas para cada funcionalidad. 
  - Tampoco olvides hacer push de todas las ramas que crees.
  ### SEGUNDO
  - Importa el proyecto a eclipse como proyecto Maven.
  ### TERCERO
  - Completa la @Entity en el archivo com.everisfpdual.testfinal.domain.Usuario. Ten encuenta que debe adaptarse al schema que se incluye src/main/resources/schema.sql es el siguiente:
  ```sh
  CREATE TABLE users (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(64) UNIQUE NOT NULL,
  firstname VARCHAR(64) NOT NULL,
  lastname VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL);
  ```
  - Una vez completada podrás levantar la aplicación haciendo click derecho sobre TestfinalApplication.java -> Run As -> Java Application. Y una vez levantada la aplicación podrás acceder a la url para consultar la base de datos.
 
  ### CUARTO
  - Importa las librerías de maven necesarias (Encontrás comentarios indicando cuales son)
  
  ### QUINTO
  - Completa el código del método getExcel() de la clase com.everisfpdual.testfinal.serviceimpl.TestFinalServiceImpl
  - Este método debe rescatar todos los usuarios de la base de datos e incluirlos en un excel. Se adjunta un ejemplo del excel en src/main/resources/Excel_03_06_2020_16-38.xlsx
  - Al entrar con el navegador en http://localhost:8080/getexcel se debe descargar dicho fichero.
  ### SEXTO
  - Completa el código del método addUsersToDbFromCsvFile() de la clase com.everisfpdual.testfinal.serviceimpl.TestFinalServiceImpl
  - Este método debe cargar a la base de datos los usuarios incluidos en el archivo src/main/resources/usuarios.csv
  - Al entrar con el navegador en http://localhost:8080/addusersfromcsv?filename=usuarios se debe producir dicha carga que podrás comprobar accediendo a la url para consultar la base de datos.
  ### SEPTIMO
  - Mergea todas tus ramas a la rama principal y haz un pull request.
 
  ### NOTA IMPORTANTE:
  - Google es tu amigo! No dudes en buscar soluciones navegando en internet.

# MUCHA SUERTE