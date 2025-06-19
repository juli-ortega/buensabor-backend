# 🍔 Buen Sabor

Bienvenido a **Buen Sabor**, una solución integral para la gestión de locales de comidas rápidas. Este proyecto está diseñado para facilitar la administración de productos, pedidos y usuarios, integrando tecnologías modernas y una arquitectura robusta.

---

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.4.5**
- **PostgreSQL**
- **Spring Data JPA**
- **Spring Security & OAuth2 (Google)**
- **JWT (JSON Web Tokens)**
- **MapStruct**
- **Lombok**
- **MercadoPago SDK**
- **OpenAPI (Swagger UI)**
- **Maven**

---

## ⚙️ Instalación y Ejecución

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/juli-ortega/buensabor-backend Back
   cd Back
   ```

2. **Configura las variables de entorno**
   - Copia `.env.example` a `.env` y completa los valores requeridos.

3. **Prepara la base de datos**
   - Crea una base de datos PostgreSQL y ajusta las credenciales en el archivo de configuración (`application.properties` o `.env`).

4. **Compila y ejecuta la aplicación**
   - En Linux/Mac:
     ```bash
     ./mvnw spring-boot:run
     ```
   - En Windows:
     ```bat
     mvnw.cmd spring-boot:run
     ```

5. **Accede a la API**
   - [http://localhost:8080](http://localhost:8080)
   - Documentación interactiva: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧩 Módulos Principales

- **Usuarios:** Registro, autenticación (incluyendo Google OAuth2), gestión de roles y permisos.
- **Productos:** Administración de productos, categorías y stock.
- **Pedidos:** Creación, seguimiento y gestión de pedidos.
- **Pagos:** Integración con MercadoPago para pagos online.
- **Seguridad:** Protección de endpoints con JWT y OAuth2.
- **Documentación:** API documentada y navegable con Swagger UI.
- **Utilidades:** Uso de MapStruct para mapeo de entidades y Lombok para reducir boilerplate.

---

## 👥 Integrantes del Grupo

- Luciano Losada
- Juan Guerrero
- Ignacio Molina
- Gabriel Kitanovich
- Julian Ortega

---

¡Gracias por visitar nuestro proyecto! Si tienes dudas o sugerencias, no dudes en contactarnos.
