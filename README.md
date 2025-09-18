# RecuerdaMe API 💊

Una API REST desarrollada con Java y Spring Boot que permite a las usuarias registrar medicamentos y programar recordatorios para las tomas.

## 📋 Descripción

RecuerdaMe es una aplicación de gestión de medicamentos que ayuda a las usuarias a organizar sus tratamientos médicos. La aplicación permite registrar medicamentos con sus dosis, fechas de inicio y fin, y genera automáticamente recordatorios para cada toma según los horarios configurados.

## 🚀 Características

- **Gestión de usuarias**: Registro y autenticación básica
- **Administración de medicamentos**: CRUD completo con validaciones
- **Sistema de recordatorios**: Generación automática de tomas según horarios
- **Estados de tomas**: Seguimiento de medicamentos tomados, pendientes o saltados
- **Arquitectura en capas**: Separación clara de responsabilidades
- **Documentación con Swagger**: API documentada automáticamente
- **Manejo de errores**: Sistema robusto de excepciones personalizadas

## 🛠️ Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **Base de datos** (configurada via variables de entorno)
- **Swagger/OpenAPI 3** para documentación
- **Lombok** para reducir código boilerplate
- **Bean Validation** para validación de datos
- **JUnit 5** para testing

## 📁 Estructura del Proyecto

```
src/main/java/com/sanitas/recuerdame/
├── config/              # Configuraciones (Swagger)
├── intake/              # Gestión de tomas
│   ├── controller/
│   ├── dtos/
│   ├── mappers/
│   └── service/
├── medications/         # Gestión de medicamentos
│   ├── controller/
│   ├── dtos/
│   ├── mapper/
│   ├── repository/
│   └── services/
├── shared/              # Código compartido
│   └── exceptions/      # Excepciones personalizadas
└── user/                # Gestión de usuarias
    ├── controller/
    ├── dto/
    ├── entity/
    ├── mapper/
    ├── repository/
    └── service/
```

## 🎯 Endpoints Principales

### Usuarias
- `POST /users/register` - Registro de nueva usuaria
- `POST /users/login` - Iniciar sesión

### Medicamentos
- `GET /api/v1/medications/{id}` - Obtener medicamento por ID
- `GET /api/v1/medications/user/{userId}` - Listar medicamentos de una usuaria
- `POST /api/v1/medications` - Registrar nuevo medicamento
- `DELETE /api/v1/medications/{id}` - Eliminar medicamento

### Tomas
- `GET /api/v1/intakes` - Listar todas las tomas
- `GET /api/v1/intakes/{id}` - Obtener toma específica
- `POST /api/v1/intakes` - Crear nueva toma
- `PATCH /api/v1/intakes/{id}/{status}` - Actualizar estado de toma

## ⚙️ Configuración

### Variables de Entorno
Crea un archivo `.env` en la raíz del proyecto:

```properties
DB_URL=jdbc:mysql://localhost:3306/recuerdame_db
DB_USER=tu_user
DB_PASSWORD=tu_password
SERVER_PORT=8080
```

### Configuración de Base de Datos
La aplicación está configurada para:
- MySQL como motor de base de datos
- Crear tablas automáticamente (`create-drop`)
- Poblar con datos de prueba via `data.sql`
- Mostrar consultas SQL en consola

## 🏃‍♀️ Ejecución

### Requisitos Previos
- Java 17 o superior
- Maven 3.6+

### Pasos
1. Clona el repositorio
2. Configura las variables de entorno
3. Ejecuta la aplicación:

```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## 📖 Documentación

Una vez ejecutada la aplicación, accede a:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🧪 Testing

Ejecuta las pruebas:

```bash
mvn test
```

El proyecto incluye:
- Tests unitarios para servicios y controladores
- Tests de integración para endpoints
- Tests de entidades

## 📊 Modelo de Datos

### Entidades Principales

- **User**: Información de usuarias
- **Medication**: Datos del medicamento y tratamiento
- **Intake**: Registro individual de cada toma

### Relaciones
- Una usuaria puede tener múltiples medicamentos
- Un medicamento genera múltiples tomas automáticamente
- Cada toma está vinculada a un medicamento específico

## 🎭 Horarios de Toma

```java
public enum IntakeSlot {
    BREAKFAST,  // Desayuno
    LUNCH,      // Almuerzo
    DINNER      // Cena
}
```

## 📝 Estados de Toma

```java
public enum Status {
    PENDING,    // Pendiente
    TAKEN,      // Tomada
    SKIPPED     // Saltada
}
```

## 🚦 Manejo de Errores

La aplicación incluye un sistema completo de manejo de excepciones:
- Excepciones personalizadas
- Validación automática de datos de entrada
- Respuestas de error estandarizadas

## 👩‍💻 Equipo RecuerdaMe

Este proyecto ha sido desarrollado por un equipo comprometido con mejorar la gestión de medicamentos y la salud de las usuarias:

- **Alexandra Rojas** - Desarrolladora backend
- **Jesús Martín** - Desarrollador backend
- **Lara Pla** - Desarrolladora backend
- **Mari Carmen Tajuelo** - Desarrolladora frontend
- **Paula Apse** - Desarrolladora frontend
- **Mariany de Araujo** - Desarrolladora frontend
- **Rocío Alondra** - Desarrolladora frontend

### Contacto
📧 **Email**: support@recuerdame.com  
💬 **Slack**: #general-hackaton-f5-grupo-5  
🐙 **GitHub**: [@RecuerdaMe-Organization](https://github.com/Sanitas-Hackaton-somosF5)

---

*"Nuestro compromiso es crear tecnología que cuide de ti y simplifique tu día a día"*

## 👥 Contribuciones

1. Fork el proyecto
2. Crea una rama para tu funcionalidad
3. Realiza tus cambios
4. Añade tests apropiados
5. Envía un pull request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

---

**Desarrollado con ❤️ y Spring Boot**