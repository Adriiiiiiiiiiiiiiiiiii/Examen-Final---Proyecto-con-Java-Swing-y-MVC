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

Persistencia de datos en TXT.

POO avanzada: herencia, interfaces, enums y polimorfismo.

# 📸 Capturas de Pantalla

1. Login de usuario

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/3e61d8a8-5f67-4adc-aa42-f6b6cf3b6783" />


2. Menú principal con pestañas

<img width="789" height="602" alt="image" src="https://github.com/user-attachments/assets/887c35c1-a394-45e8-8b94-adf2fc24741d" />


3. Formulario de reservas

<img width="1913" height="1079" alt="image" src="https://github.com/user-attachments/assets/2ab6b11b-1d2e-41a6-b26f-676ba1a19114" />



5. Tabla de habitaciones

<img width="366" height="214" alt="image" src="https://github.com/user-attachments/assets/276ac53f-3e78-49b8-9d87-46fc94d2418e" />


# 📐 Diagrama de Clases UML

<img width="1479" height="484" alt="image" src="https://github.com/user-attachments/assets/1200b8f6-0a20-4e1c-a006-4c2f8227e5c4" />

#🎥 Video de Sustentación

https://youtu.be/xiqQLwGUuhg
