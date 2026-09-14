# Plan de Arquitectura - MVP Sistema de Reserva de Inmuebles Deportivos (Spring Boot)

Este documento contiene la arquitectura actualizada para el MVP de reserva de inmuebles (1 cancha de fútbol, 1 de tenis y 1 multidisciplinaria) integrando el diseño e implementación de la **suite completa de vistas en Thymeleaf**, **pago multidestino (Mercado Pago, Efectivo, Transferencia)**, **bloques horarios de reserva**, **home pública para consulta de disponibilidad** y **dashboard privado protegido por JWT**.

---

## Decisiones de Arquitectura Incorporadas

> [!IMPORTANT]
> **1. Vistas Thymeleaf en el MVP:**
> Se construirán plantillas HTML5 modernas y responsivas con **Bootstrap 5 (vía CDN)** y Thymeleaf dialect para:
> - **`layout/base.html`**: Layout maestro con barra de navegación, footer y manejo de sesión JWT.
> - **`index.html` (Home Pública)**: Muestra los 3 inmuebles y la grilla de disponibilidad del día sin necesidad de login.
> - **`login.html` & `register.html`**: Autenticación con formulario y recepción de Cookie JWT.
> - **`dashboard.html` (Dashboard de Usuario)**: Panel interactivo para seleccionar cancha, horario y método de pago (Mercado Pago, Efectivo, Transferencia), así como el historial de turnos.
> - **`admin/dashboard.html` (Panel Admin)**: Control de reservas para confirmar pagos por transferencia/efectivo.

> [!IMPORTANT]
> **2. Acceso Público vs Autenticado (Spring Security & JWT):**
> - **Rutas Públicas (`PermitAll`)**: `/`, `/home`, `/disponibilidad/**`, `/login`, `/register`, `/css/**`, `/js/**`.
> - **Rutas Protegidas (`JWT Required`)**: `/dashboard`, `/reservas/crear`, `/reservas/mis-turnos`, `/api/bookings/**`.

> [!NOTE]
> **3. Ubicación del Proyecto:**
> Se creará el código fuente en la carpeta `/Users/facundo/.gemini/antigravity/scratch/reserva-canchas-mvp`. Te recomendamos establecer dicha carpeta como tu espacio de trabajo activo (workspace).

---

## Proposed Changes

### Estructura del Proyecto (Backend + Templates Thymeleaf)

```text
reserva-canchas-mvp
├── src/main/java/com/reserva/canchas
│   ├── config/                  # Seguridad Spring Security y JWT
│   │   ├── SecurityConfig.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtUtils.java
│   ├── domain/                  # Entidades JPA (User, Facility, Booking)
│   │   ├── User.java
│   │   ├── Role.java
│   │   ├── Facility.java (Fútbol, Tenis, Multidisciplinaria)
│   │   ├── FacilityType.java
│   │   ├── Booking.java
│   │   ├── BookingStatus.java (PENDIENTE, CONFIRMADA, CANCELADA)
│   │   ├── PaymentMethod.java (MERCADO_PAGO, EFECTIVO, TRANSFERENCIA)
│   │   └── PaymentStatus.java (PENDIENTE, APROBADO, RECHAZADO)
│   ├── repository/              # Spring Data JPA
│   │   ├── UserRepository.java
│   │   ├── FacilityRepository.java
│   │   └── BookingRepository.java
│   ├── dto/                     # DTOs para MVC y REST
│   │   ├── AuthRequestDTO.java
│   │   ├── BookingRequestDTO.java
│   │   ├── AvailabilitySlotDTO.java
│   │   └── MercadoPagoPreferenceDTO.java
│   ├── service/                 # Lógica de Negocio compartida
│   │   ├── AuthService.java
│   │   ├── FacilityService.java
│   │   ├── BookingService.java
│   │   └── payment/
│   │       ├── PaymentService.java
│   │       └── MercadoPagoService.java
│   └── controller/
│       ├── mvc/                 # Controladores para Thymeleaf
│       │   ├── HomePublicMvcController.java  # GET / (Home pública)
│       │   ├── AuthMvcController.java        # GET /login, POST /login
│       │   └── DashboardMvcController.java   # GET /dashboard, POST /reservas
│       └── api/                 # Controladores REST (para futura migración a Angular)
│           ├── PublicRestController.java
│           ├── AuthRestController.java
│           └── BookingRestController.java
└── src/main/resources
    ├── application.properties
    └── templates/               # Vistas Thymeleaf del MVP
        ├── layout/
        │   └── base.html        # Header, Navbar, Toast notificatorio y Footer
        ├── index.html           # Home Pública con Grilla de Canchas y Turnos
        ├── login.html           # Formulario de inicio de sesión
        ├── register.html        # Formulario de registro de cliente
        ├── dashboard.html       # Panel de usuario: Nueva reserva + Mis turnos
        └── admin/
            └── dashboard.html   # Panel administrador para gestión de pagos
```

---

### Detalle de las Vistas Thymeleaf (`templates/`)

#### 1. `index.html` (Home Pública)
- Encabezado atractivo con presentación de las 3 canchas: **Fútbol 7**, **Tenis Clay Court** y **Multidisciplinaria**.
- Filtro por fecha (selector `input date`).
- Tabla / Grilla interactiva de bloques horarios (ej: 08:00 a 22:00 hs):
  - Badge **Verde ("Disponible")** con botón para reservar (redirige a login si no hay sesión).
  - Badge **Rojo ("Reservado")** indicando el rango de horario ocupado.

#### 2. `login.html` y `register.html`
- Formularios limpios y responsivos.
- En el submit del login, genera JWT y guarda Cookie `JWT-TOKEN` para navegación continua en Thymeleaf.

#### 3. `dashboard.html` (Panel de Reservas del Cliente)
- Resumen de perfil y reservas activas/históricas con sus estados de pago.
- Modal / Formulario para **Reservar Turno**:
  - Seleccionar Cancha.
  - Seleccionar Fecha y Bloque Horario.
  - Seleccionar Método de Pago:
    - **Mercado Pago**: Muestra botón para redirigir a Checkout MP.
    - **Transferencia**: Muestra CBU/Alias e instrucciones para adjuntar comprobante.
    - **Efectivo**: Confirmación de reserva pendiente de pago en puerta.

---

## Verification Plan

### Automated Tests
- **Pruebas de Vistas MVC (`MvcControllersTest`)**:
  - Verificar que `GET /` devuelva la plantilla `index.html` con la lista de inmuebles y bloques horarios sin requerir autenticación.
  - Verificar que `GET /dashboard` devuelva redirección a `/login` si no se envía Cookie JWT válida.
- **Pruebas de Servicios (`BookingServiceTest`)**:
  - Validar que no se permitan reservas duplicadas para la misma cancha y horario.

### Manual Verification
1. **Verificación de UI en Navegador**: Probar la fluidez visual de `index.html`, `login.html` y `dashboard.html` en pantallas de escritorio y móviles.
2. **Proceso Completo de Reserva**:
   - Navegar en `/` sin sesión -> Ver canchas -> Click en reservar -> Iniciar sesión en `/login` -> Seleccionar cancha, horario y método de pago (Mercado Pago / Transferencia / Efectivo) -> Confirmar turno y verificar actualización inmediata en el dashboard.
