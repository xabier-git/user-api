# User API - Instrucciones para Probar el Proyecto

Este proyecto es una API REST para la gestión de usuarios, que incluye funcionalidades para crear y modificar usuarios, así como manejar sus teléfonos asociados.

## Requisitos Previos

1. **Java**: Asegúrate de tener instalado Java 11 o superior.
2. **Maven**: Asegúrate de tener instalado Maven 3.6 o superior.
3. **Base de Datos**: Este proyecto utiliza H2 como base de datos en memoria, que se configura automáticamente.

## Configuración del Proyecto

1. Clona el repositorio o copia los archivos del proyecto en tu máquina local.
2. Navega al directorio raíz del proyecto: 
    cd /home/jaguirre/develop/jobtest/workspace/user-api
3. Verifica que el archivo `application.properties` esté configurado correctamente en `src/main/resources`. Por defecto, el proyecto utiliza H2 como base de datos en memoria.

## Compilar y Construir el Proyecto

1. Limpia y compila el proyecto usando Maven:
    mvn clean install
2. Si la compilación es exitosa, se generará un archivo JAR ejecutable en el directorio `target`.

## Ejecutar el Proyecto

1. Ejecuta el archivo JAR generado:
    java -jar target/user-api-1.0.0.jar
2. La API estará disponible en `http://localhost:8080/user-api`.

## Probar la API

### Endpoints Disponibles

1. **Crear Usuario**:
- **URL**: `POST http://localhost:8080/user-api/users/create`
- **Cuerpo de la Solicitud (JSON)**:
  ```json
  {
      "name": "Juan Perez",
      "email": "juan.perez@example.com",
      "password": "Password@123",
      "phones": [
          {
              "number": "1234567",
              "citycode": "1",
              "contrycode": "57"
          }
      ]
  }
  ```
- **Respuesta Exitosa (JSON)**:
  ```json
  {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
      "name": "Juan Perez",
      "email": "juan.perez@example.com",
      "password": "Password@123",
      "phones": [
          {
              "number": "1234567",
              "citycode": "1",
              "contrycode": "57"
          }
      ]
  }
  ```

2. **Modificar Usuario**:
- **URL**: `PUT http://localhost:8080/user-api/users/modify`
- **Cuerpo de la Solicitud (JSON)**:
  ```json
  {
      "name": "Juan Perez Actualizado",
      "email": "juan.perez@example.com",
      "password": "NewPassword@123",
      "phones": [
          {
              "number": "7654321",
              "citycode": "2",
              "contrycode": "58"
          }
      ]
  }
  ```
- **Respuesta Exitosa (JSON)**:
  ```json
  {
      "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
      "name": "Juan Perez Actualizado",
      "email": "juan.perez@example.com",
      "password": "NewPassword@123",
      "phones": [
          {
              "number": "7654321",
              "citycode": "2",
              "contrycode": "58"
          }
      ]
  }
  ```

3. **Endpoint de Bienvenida**:
- **URL**: `GET http://localhost:8080/user-api/users/home`
- **Respuesta Exitosa**:
  ```
  Welcome to the User API
  ```

## Ejecutar las Pruebas

1. Ejecuta las pruebas unitarias con Maven:
    mvn test
2. Verifica que todas las pruebas pasen correctamente.

## Notas Adicionales

- La base de datos H2 se reinicia cada vez que se reinicia la aplicación.
- Puedes acceder a la consola de H2 en `http://localhost:8080/user-api/h2-console` con las siguientes credenciales:
  - **URL JDBC**: `jdbc:h2:mem:testdb`
  - **Usuario**: `sa`
  - **Contraseña**: *(vacío)*

## Contacto

Javier Aguirre  
javier.aguirre.araya@gmail.com  
+56941894839