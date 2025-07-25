# 🏨 Sistema de Gestión de Reservas de Hotel

Proyecto final del curso basado en Java Swing con arquitectura MVC. Este sistema permite gestionar reservas, clientes, habitaciones y procesos de check-in/check-out, integrando conceptos de POO avanzada, persistencia de datos y una interfaz intuitiva.

---

## 👥 Integrantes

- Adriano Rodas (Rol: Vista / Controlador)
- Michael yumbla (Rol: Modelo / Persistencia)

---

## 📋 Requisitos

- Java 11 o superior
- Librerías utilizadas:
  - SQLite JDBC Driver (para persistencia)
  - JCalendar (para selección de fechas, si se usó)
- IDE recomendado: IntelliJ IDEA / Eclipse

---

src/
├── modelo/          # Clases lógicas (Habitacion, Cliente, Reserva, Usuario, etc.)
├── vista/           # Interfaces gráficas con Java Swing (JFrame, JPanel)
├── controlador/     # Clases que manejan eventos (ReservaController, etc.)
└── main/            # Clase principal para iniciar la app

# ✨ Funcionalidades Principales

Inicio de sesión por rol de usuario.

Registro y listado de clientes.

Gestión de habitaciones (disponibles, ocupadas).

Creación, cancelación y búsqueda de reservas.

Validación de datos:

No permite reservas solapadas.

Formularios con campos obligatorios.

Persistencia de datos en SQLite o archivos.

POO avanzada: herencia, interfaces, enums y polimorfismo.

# 📸 Capturas de Pantalla

1. Login de usuario

IMG

2. Menú principal con pestañas

IMG

3. Formulario de reservas

IMG


5. Tabla de habitaciones

IMG

# 📐 Diagrama de Clases UML

<img width="1477" height="595" alt="image" src="https://github.com/user-attachments/assets/ca0b4475-e30c-4fc8-b4fe-dd2354eb2d55" />

#🎥 Video de Sustentación

Link al video
