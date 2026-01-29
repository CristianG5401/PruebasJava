# Ejemplo CRUD de Java JDBC con MariaDB

Este proyecto es una aplicación de línea de comandos simple en Java que demuestra cómo conectarse a una base de datos MariaDB y realizar un conjunto completo de operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando un controlador JDBC estándar.

## Características

- **Conectividad con la Base de Datos**: Establece una conexión con una base de datos MariaDB.
- **Configuración Externa**: Lee las credenciales de la base de datos (URL, usuario, contraseña) desde un archivo `config/jdbc.properties` para mantener los secretos fuera del código fuente.
- **Operaciones CRUD**: Demuestra cómo insertar, leer, actualizar y eliminar registros en una tabla `inventario`.
- **Código Limpio**: Sigue las mejores prácticas básicas con una clara separación de responsabilidades y comentarios descriptivos.

## Prerrequisitos

- Java Development Kit (JDK) 11 o superior.
- Una instancia de base de datos MariaDB o MySQL en ejecución.

## Cómo Empezar

Sigue estos pasos para poner en marcha la aplicación.

### 1. Configuración de la Base de Datos

Primero, necesitas crear la tabla de la base de datos y poblarla con algunos datos iniciales.

1.  Conéctate a tu instancia de MariaDB/MySQL.
2.  Crea una base de datos (si aún no tienes una).
3.  Ejecuta el script SQL ubicado en `db/create-populate-inventario-table.sql` para crear la tabla `inventario` e insertar algunos datos de muestra.

### 2. Configuración de la Aplicación

La aplicación necesita saber cómo conectarse a tu base de datos.

1.  Haz una copia del archivo de ejemplo de configuración:
    ```bash
    cp config/jdbc.properties.sample config/jdbc.properties
    ```
2.  Abre `config/jdbc.properties` y actualiza las propiedades `db.url`, `db.user` y `db.password` con tus credenciales reales de la base de datos.

### 3. Compilación

Compila el código fuente de Java desde el directorio raíz del proyecto:

```bash
javac -cp "lib/*" src/ejemploconexionjdbc/App.java -d bin
```

### 4. Ejecución

Ejecuta la aplicación:

Windows:

```bash
java -cp "bin;lib/*" ejemploconexionjdbc.App
```

Linux/macOS:

```bash
java -cp "bin:lib/*" ejemploconexionjdbc.App
```

La aplicación se conectará a la base de datos y ejecutará una demostración de las operaciones CRUD, imprimiendo los resultados en la consola.

## Estructura del Proyecto

- **`src/`**: Contiene el código fuente de Java (`App.java`).
- **`lib/`**: Contiene el controlador JDBC de MariaDB (archivo `.jar`).
- **`config/`**: Contiene los archivos de configuración de la conexión a la base de datos.
- **`db/`**: Contiene los scripts SQL para la configuración de la base de datos.
- **`bin/`**: El directorio de salida para los archivos `.class` de Java compilados.