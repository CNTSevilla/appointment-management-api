# Sistema de Gestión de Citas

Este proyecto es un sistema de gestión de citas diseñado para facilitar la coordinación entre personas que necesitan ayuda y los asistentes que proporcionan dicha ayuda. El sistema permite crear, visualizar y gestionar citas, así como añadir comentarios y seguir el estado de cada cita.

## Requisitos Mínimos

Para ejecutar este proyecto necesitarás:

- Java 21 o superior
- MySQL 8.0 o superior
- Gradle 8.14.0 o superior
- Docker (opcional, para despliegue en contenedores)

## Instalación

### Configuración de la Base de Datos

1. Crea una base de datos MySQL para el proyecto.
2. Configura las variables de entorno necesarias:
   - `SPRING_DATASOURCE_URL`: URL de conexión a la base de datos MySQL
   - `DB_NAME`: Nombre de usuario de la base de datos
   - `DB_PASSWORD`: Contraseña de la base de datos

### Configuración de JWT

1. Configura las variables de entorno para JWT:
   - `JWT_EXP_TIME`: Tiempo de expiración del token JWT
   - `JWT_SECRET`: Clave secreta para firmar los tokens JWT

### Configuración de Email

1. Configura las variables de entorno para el servicio de email:
   - `MAIL_HOST`: Host del servidor de correo
   - `MAIL_PORT`: Puerto del servidor de correo
   - `MAIL_USERNAME`: Nombre de usuario del correo
   - `MAIL_PASSWORD`: Contraseña del correo
   - `MAIL_PROTOCOL`: Protocolo de correo (ej. smtp)
   - `MAIL_SSL`: Habilitar SSL (true/false)
   - `MAIL_SMTP_AUTH`: Autenticación SMTP (true/false)
   - `MAIL_SMTP_TLS_ENABLE`: Habilitar TLS (true/false)
   - `MAIL_SMTP_TLS_REQUIRED`: Requerir TLS (true/false)

### Ejecución Local

1. Clona el repositorio:
   ```
   git clone [URL_DEL_REPOSITORIO]
   ```

2. Navega al directorio del proyecto:
   ```
   cd AppointmentManagementDevOps/projects/backend
   ```

3. Compila el proyecto:
   ```
   ./gradlew build
   ```

4. Ejecuta la aplicación:
   ```
   ./gradlew bootRun
   ```

### Despliegue con Docker

1. Construye la imagen Docker:
   ```
   docker build -t appointment-management .
   ```

2. Ejecuta el contenedor:
   ```
   docker run -p 8080:8080 --env-file .env appointment-management
   ```

   Nota: Debes crear un archivo `.env` con todas las variables de entorno mencionadas anteriormente.

## Uso

La API REST está disponible en `http://localhost:8080/api/v1/` y proporciona los siguientes endpoints:

- `GET /api/v1/appointment/all`: Obtiene todas las citas
- `GET /api/v1/appointment/{id}`: Obtiene una cita específica por ID
- `POST /api/v1/appointment`: Crea una nueva cita

Para más detalles sobre cómo usar la API, consulta la documentación completa de la API (pendiente de implementar).

## Contribución

¡Gracias por considerar contribuir a este proyecto! Como este proyecto está bajo la licencia GNU/GPL v3, tus contribuciones deben seguir los mismos términos de licencia.

### Proceso de Contribución

1. Haz un fork del repositorio
2. Crea una rama para tu característica (`git checkout -b feature/nueva-caracteristica`)
3. Realiza tus cambios
4. Ejecuta las pruebas para asegurarte de que todo funciona correctamente
5. Haz commit de tus cambios (`git commit -m 'Añade nueva característica'`)
6. Sube tus cambios a tu fork (`git push origin feature/nueva-caracteristica`)
7. Crea un Pull Request

### Directrices de Contribución

- Sigue el estilo de código existente
- Escribe pruebas para tus cambios
- Mantén las dependencias al mínimo
- Documenta cualquier cambio en la API o funcionalidad
- Actualiza la documentación si es necesario

### Reportar Problemas

Si encuentras un problema, por favor crea un issue en el repositorio con la siguiente información:
- Descripción detallada del problema
- Pasos para reproducir el problema
- Comportamiento esperado vs. comportamiento actual
- Capturas de pantalla si es aplicable
- Entorno (sistema operativo, versión de Java, etc.)

## Licencia

Este proyecto está licenciado bajo la [GNU General Public License v3.0](LICENSE.txt) - consulta el archivo LICENSE.txt para más detalles.

La GNU GPL v3 es una licencia de software libre y copyleft que garantiza a los usuarios finales la libertad de usar, estudiar, compartir y modificar el software. Cualquier software derivado debe distribuirse bajo los mismos términos de licencia.

Para más información sobre la licencia GNU GPL v3, visita [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html).
