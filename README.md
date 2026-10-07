# RegistroEmpleadosFX

Aplicación de escritorio JavaFX para registrar empleados en PostgreSQL y consultar los registros en un `TableView`.

## Requisitos

- JDK 17 o posterior
- Maven 3.8 o posterior
- PostgreSQL con la base de datos existente `biblioteca_fx`

## Preparar PostgreSQL

1. Conéctese a `biblioteca_fx` desde pgAdmin o `psql`.
2. Ejecute `sql/01_crear_y_poblar_empleado.sql`.
3. Ejecute `sql/02_consultas_practica.sql` para probar las consultas solicitadas.

La tabla usa `BIGINT IDENTITY` para la clave primaria; cédula es única; salario no puede ser negativo; departamento, salario, fecha y estado son obligatorios. Correo es opcional y único cuando se proporciona. Teléfono y cargo son opcionales.

## Configurar conexión

La configuración está centralizada en `DatabaseConnection`. Por defecto intenta `jdbc:postgresql://localhost:5432/biblioteca_fx`, usuario `postgres` y contraseña vacía. Se recomienda establecer estas variables de entorno para ajustarla a su instalación:

- `DB_URL`, por ejemplo `jdbc:postgresql://localhost:5432/biblioteca_fx`
- `DB_USER`
- `DB_PASSWORD`

No se guardan credenciales en el código fuente.

## Ejecutar

Desde esta carpeta:

```bash
mvn clean javafx:run
```

La ventana consulta los registros al abrirse. El formulario valida los campos requeridos y el salario, inserta mediante `PreparedStatement`, informa el resultado y vuelve a cargar la tabla.

## Estructura

```text
src/main/java/com/example/registroempleados/
  RegistroEmpleadosApplication.java
  controller/EmpleadoController.java
  database/DatabaseConnection.java
  model/Empleado.java
src/main/resources/com/example/registroempleados/
  views/empleado-view.fxml
  estilos/estilos.css
sql/
  01_crear_y_poblar_empleado.sql
  02_consultas_practica.sql
```
