# 🏦 Sistema de Simulación de Sucursal Bancaria

Proyecto de aplicación desarrollado para la cátedra de **Estructuras de Datos** (1º Cuatrimestre de 2025). El objetivo principal de este trabajo fue diseñar, seleccionar e implementar los Tipos de Datos Abstractos (TDAs) óptimos para gestionar eficientemente el flujo de atención en una sucursal bancaria.

## 📋 Descripción del Problema

El sistema modela el comportamiento de una sucursal bancaria real, gestionando el ciclo de vida de la atención desde que una persona ingresa hasta que es llamada por un puesto. 

La cátedra proveyó una base de código (`esqueleto` de clases y entidades), siendo nuestro trabajo implementar la lógica central en la clase `SucursalBancaria` (implementando la interfaz `SistemaBancario`), decidiendo qué estructuras de datos soportaban mejor los siguientes requerimientos:

### Lógica de Negocio a Resolver
* **Gestión de Turnos:** Generación de turnos alfanuméricos correlativos por tipo de trámite (ej. `C-1`, `A-1`).
* **Reglas de Puestos:** Cada puesto puede atender múltiples tipos de trámites. Cuando un puesto se libera, debe llamar al cliente con mayor antigüedad de espera *dentro de los trámites que ese puesto soporta*.
* **Roles de Usuarios:** * `Clientes:` Acceso total a los 6 trámites disponibles (Operaciones de caja, Atención al cliente, Atención comercial, Tarjetas, Comercio exterior, Otros).
    * `No Clientes:` Acceso restringido (solo Operaciones de caja y Atención comercial).
* **Pantalla de Llamados:** Mantener un registro ordenado y accesible de los últimos 4 turnos llamados para su visualización en pantallas (indicando Turno y Puesto).

## Tecnologías y Herramientas

* **Lenguaje:** Java
* **Estructuras de Datos:** *[Nota: Completá acá los TDAs que usaste, ej: Colas de Prioridad, Listas Enlazadas, Pilas, Diccionarios]*
* **Testing:** Pruebas unitarias implementadas con **JUnit** para asegurar el correcto funcionamiento de las estructuras personalizadas.
* **Control de Versiones:** Git & GitHub.

## Equipo de Desarrollo (Comisión 11)

* **Truninger, Justo Ulrich Scott**
* **Vazzano, Federico Nicolás**

---
*Este proyecto refleja el criterio para comparar alternativas de diseño y la capacidad para implementar estructuras de datos complejas desde cero.*
