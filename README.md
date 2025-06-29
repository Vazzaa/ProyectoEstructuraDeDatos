# Proyecto - Estructuras de Datos


## Introducción

Bienvenidos al Proyecto de Estructuras de Datos del 1º Cuatrimestre de 2025.

El presente proyecto tiene como objetivo el desarrollo de un pequeño caso de aplicación en el que deberán seleccionar e implementar las estrucuturas de datos que consideren más adecuadas para la solución del problema planteado. No existe una única solución correcta para el desarrollo del mismo por lo que se espera que ejerciten el criterio para comparar entre varias alternativas y seleccionar la que consideren más adecuada. Además, como resultado del proyecto se espera que (si aún no lo han hecho durante la ejercitación práctica) se familiaricen con la programación de los TDAs elegidos, su testeo usando JUnit, su uso, y también en menor medida con _Git_ y _Github_.


## Sobre Github Classroom

El proyecto está alojado en Github Classroom y se utilizará dicha plataforma para su distribución y entrega. Para ello:

1. Se les va a enviar un enlace de invitación al proyecto para que puedan acceder
2. Si no lo han hecho, deberán iniciar sesión con su cuenta de Github (creándose una si aún no tienen ninguna). La cuenta a utilizar es personal y cada alumno deberá tener la suya propia (la cual, si lo desean, podrán continuar usando como desarrolladores el resto de su vida profesional)
3. Al ingresar, si no lo han hecho, se les va a pedir que se busquen en el listado de alumnos que se presenta y que se "autoseleccionen". Esto permitirá cargar un Mapeo CuentaGithub-Alumno necesario para identificarlos correctamente
4. A continuación se les pedirá que acepten la tarea (assignment) y que formen comisión (a lo sumo 2 integrantes cada una). El primero de los integrantes deberá crear un grupo, y el 2do, añadirse al mismo. **IMPORTANTE: Para una mejor organización, nombrar la comisión usando los apellidos de sus integrantes**.
5. Luego de todos los pasos anteriores, se les va a generar un repositorio en Github (en la organización de la materia `ed-dcic-uns`) conteniendo los archivos que son el punto de partida del proyecto.
6. Este repositorio es privado para cada comisión y deberán clonarlo localmente en sus equipos para comenzar a trabajar. Adicionalmente, encontrarán un Pull Request abierto que podrán utilizar para realizar comentarios, consultas a la cátedra (quienes estaremos administrando el repo), iniciar discusiones o revisiones de código, coordinar cuestiones del desarrollo entre los miembros de la comisión, o mencionar cualquier cosa de la que quieran dejar registro.
7. La forma de trabajo con _Git/Github_ la definen ustedes. Si prefieren abrir ramas y usar pull requests, o si prefieren hacer push directos a la rama `main`... es elección libre. La cátedra puede hacer recomendaciones que les pueden servir a futuro, pero no se va a exigir ninguna metodología de trabajo en particular.
8. A medida que suban cambios a la rama main del repo, se ejecutarán testeos unitarios automáticos. Sean libres de ir subiendo los cambios a medida que lo consideren adecuado sin importar si hay tests que fallan cuando el proyecto aún no está completo. 
9. El proyecto se considerará **Aprobado** cuando la **ejecución de los tests unitarios automáticos sea exitosa en el entorno remoto** (lo que implicará una resolución completa del mismo), siempre que se produzca con anterioridad a la fecha límite de entrega.

Ante cualquier incoveniente en alguno de estos pasos, comunicarse con la cátedra.


## Enunciado del Proyecto

Se desea implementar una solución para optimizar la atención de personas en una sucursal bancaria. Para ello se cuentan con los siguientes elementos:

- **Trámites**: son las posibles actividades que cada una de las personas va a realizar al banco. Cada _trámite_ tiene un nombre, una descripción, una letra que oficia de código, una duración estimada, y una duración efectiva.
- **Puestos** de atención: cada puesto tiene un nombre y permite atender un conjunto de _trámites_. 
- **Personas**: son quienes llegan al banco a realizar algún trámite y esperan hasta ser atendidos. Las personas pueden ser: 
    - _Clientes_: personas que son clientes del banco y tienen acceso a todos los _trámites_ (servicios bancarios)
    - _NoClientes_ (o público en general): el resto de las personas, sólo tienen acceso a ciertos _trámites_
- **Turnos**: el número de espera otorgado a la persona al ingresar a la sucursal. Identifica para qué _trámite_ fue solicitado, y qué _persona_ es la poseedora del mismo.

Todos los elementos previos se dan ya implementados y listos para usar. Además: 
- los _trámites_ disponibles son un conjunto fijo que determinan la razón por la que espera cada persona: _operaciones de caja_ (`C`), _atención al cliente_ (`A`), _atención comercial_ (`B`),  _operaciones con tarjetas_ (`T`), _comercio exterior_ (`X`), y _otros trámites_ (`O`)
- durante la apertura de un _puesto_ se determinan y asocian el o los _trámites_ que atiende:
    - cada _puesto_ llama a la _persona_ con el próximo _turno_ de entre aquellas que esperan por alguno de los _trámites_ que ese puesto atiende,
    - si para los trámites de un _puesto_ hubiese varias _personas_ esperando, se atiende primero a quien haya obtenido el _turno_ primero, independientemente del _trámite_ por el que espera,
- cuando una _persona_ llega al banco se identifica y se clasifica según el tipo que sea entre los dos disponibles (a efectos de simplificar la implementación asumiremos que se ya se tiene la _persona_ identificada y clasificada), y luego elige qué trámite quiere realizar. Como resultado de ello se le da un turno (número) para que espere:
    - el turno (número) dado es una combinación de una letra indicando el trámite, y un número secuencial ascendente que comienza en 1 (ej. si la persona selecciona _operaciones de caja_ y se le da el número `C-23`, esto implica que antes de dicha persona, hubo otras 22 que seleccionaron _operaciones de caja_),
    - cada trámite tiene su propia numeración secuencial independiente (la primer persona que selecciona _operaciones de caja_ recibe el número `C-1`, la primera que selecciona _atención al cliente_ recibe el `A-1`, y así siguiendo),
    - para ser atendida, cada persona deberá esperar a que todas las personas con su mismo trámite que llegaron antes sean atendidas,
    - los _Clientes_ pueden hacer cualquier _trámite_ disponible, y
    - los _NoClientes_ sólo pueden hacer _operaciones de caja_ y solicitar _atención comercial_.

Teniendo en consideración todo lo anterior, se pide la selección e implementación de las estructuras de datos necesarias para dar soporte a la solución del problema planteado y gestionar la espera de las personas en la sucursal bancaria.

Adicionalmente, se pide la implementación del soporte de datos para una pantalla donde se visualicen los últimos 4 llamados realizados, indicando para cada uno el _turno_ (número) y el _puesto_. De esta manera, las personas esperando pueden saber cuándo son llamados y a qué puesto deben dirigirse.

El desarrollo del proyecto consiste en completar la implementación de los métodos de la clase `SucursalBancaria` (cuyo esqueleto y clases de soporte se proveen) de manera de cumplir los requerimientos explicados más arriba. Esta clase implementa la interface `SistemaBancario`, interface en la cual se encuentran documentados y pueden consultar los detalles de implementación de cada método.


## Estructura del proyecto

La estructura del proyecto en general sigue las mismas convenciones utilizadas para el repositorio de TDAs [`https://github.com/ed-dcic-uns/tdas`](https://github.com/ed-dcic-uns/tdas) utilizado a lo largo de todo el cuatrimestre.

El proyecto se estructura de la siguiente manera:

- Carpeta `src/main/java`: Contiene el código fuente (interfaces e implementaciones) del proyecto, y los siguientes paquetes:
    - `ar.edu.uns.cs.ed.proyectos.banco`: Es el paquete central del proyecto. Contiene la interface `SistemaBancario` y su implementación parcial `SucursalBancaria` que se debe completar como parte de la resolución del proyecto. 
    También incluye una clase `AppCLI` lista para usar que implementa una aplicación de interface por línea de comandos (CLI) para experimentar con el proyecto una vez resuelto. Esta clase y esta experimentación no es relevante para determinar la correctitud/aprobación de la implementación, la cual se determina en base a los casos de prueba incluídos.
    - `ar.edu.uns.cs.ed.proyectos.banco.entities`: Contiene las clases `Persona`, `Puesto`, `Tramite` y `Turno`, que modelan el problema, completamente implementadas y listas para usar. Las clases definen objetos mayormente inmutables (una vez creados no se modifican) con unos pocos atributos simples básicos. El único setter disponible lo tiene `Persona` para asignarle un `Turno` luego de creada.
    - `ar.edu.uns.cs.ed.proyectos.banco.util`: Contiene la clase auxiliar `Par`, provista para evitar añadir dependencias adicionales al proyecto.
    - `ar.edu.uns.cs.ed.tdas`: Contiene las interfaces de todos los TDAs vistos en la práctica de la materia. En función de los TDA elegidos para la resolución del proyecto, deberán eliminar todos aquellos que no utilicen a efectos de presentar una entrega limpia de cosas innecesarias y que compile en su totalidad.


- Carpeta `src/test/java`: Contiene los tests JUnit del proyecto en los siguientes paquetes:
    - `ar.edu.uns.cs.ed.proyectos.banco`: Contiene la clase de casos de prueba JUnit `SucursalBancariaTest`. Estos tests se distribuyen para que puedan verificar el correcto funcionamiento del proyecto. El criterio de aprobación del proyecto se define en base al resultado de estos tests, por lo que **antes de dar por finalizado el proyecto y la entrega, deberán asegurarse de que todos los tests de esta clase pasan**.
    - `ar.edu.uns.cs.ed.tdas`: Contiene los tests de todos los TDAs vistos en la práctica de la materia. En función de los TDA elegidos para la resolución del proyecto, deberán eliminar todos aquellos tests que no utilicen a efectos de presentar una entrega limpia de cosas innecesarias. Pueden usar los tests para asegurarse que no incorporan al proyecto ninguna implementación defectuosa.

- Otras carpetas y archivos adicionales de configuración de proyecto: Son elementos fijos que no influyen en el desarrollo y pueden ignorar con tranquilidad. Cumplen las siguientes funciones:
    - carpeta `.git` (oculta): información del repositorio Git
    - carpeta `.vscode`: archivos de configuración de proyecto para aquellos que utlizan Visual Studio Code
    - carpeta `bin`: carpeta donde se generan los archivos binarios con el código compilado (`.class`) del proyecto. Se regenera cada vez que se compila y no se versiona
    - carpeta `lib`: contiene el archivo `.jar` de JUnit necesario para ejecutar los casos de test. Forma parte del versionado pero está freezado
    - archivo `.classpath`: configuración de ubicación de elementos del proyecto, tanto para usuarios de VSCode como de Eclipse
    - archivo `.gitignore`: archivo de configuración de Git que determina qué archivos dejar fuera del control de versiones
    - archivo `.project`: configuración de proyecto para aquellos que utilizan Eclipse
    - archivo `README.md`: este archivo, en notación Markdown.


**MUY IMPORTANTE**: Todo el proyecto está estructurado para ser resuelto **trabajando exclusivamente sobre la clase `SucursalBancaria`**. Se provee el esqueleto de dicha clase y una serie de comentarios marcando las tareas a realizar (que deberán ir eliminando a medida que las completen). El objetivo del proyecto es completar la implementación de la clase `SucursalBancaria`, y, salvo el ajuste en el nombre de la implementación a testear en los testers de TDAs **no deben modificar ningún otro archivo de la distribución**.

El repositorio tiene configurado un sistema de Integración Continua (con GitHub Actions) que detecta y **reporta cambios a archivos no autorizados**. Por eso, si consideran que para resolver determinada tarea necesitan modificar algún otro archivo además de `SucursalBancaria.java` y los testers de TDAs, consulten con la cátedra para averiguar por qué están equivocados.


## Tareas a realizar

Inicialización:

- [x] T1: Acceder a la tarea de Github Classroom y aceptarla (si pueden leer esto ya está realizada). Esto crea automáticamente un repositorio remoto exclusivo y privado para la comisión con el PR abierto para comunicarse. Si les sirve, pueden marcar las tareas a medida que las completan editando esta sección del README.md, añadiendo una `X` entre el par de corchetes correctos como se ejemplifica en esta tarea.
- [ ] T2: Clonar el repositorio que se crea automáticamente para comenzar a trabajar localmente en sus computadoras.
- [ ] T3: Familiarizarse con la estructura del proyecto (ver sección anterior y estructura de carpetas y archivos en el repositorio).
- [ ] T4: Analizar el problema, diseñar soluciones y seleccionar los TDAs que utilizarán para la resolución.


Implementación (trabajo dentro de la clase `SucursalBancaria`):

- [ ] T5: Implementar el soporte para gestionar y consultar las asociaciones entre _Puestos_ y _Tramites_. Métodos: `asociarTramiteAPuesto(...)`, `desasociarTramiteAPuesto(...)`, `obtenerTramitesAsociadosAPuesto(...)` y `obtenerCantidadDePuestosAtendiendoElTramite(...)`.
- [ ] T6: Implementar el soporte para ingresar personas al sistema y otorgar turnos (modelar la llegada de personas al banco). Métodos: `sacarTurno(...)` y `obtenerTiempoDeEsperaEstimado(...)`.
- [ ] T7: Implementar el soporte para gestionar el llamado y atención de _Personas_/_Turnos_ desde los _Puestos_ y la producción de la información para la pantalla. Métodos: `llamarYAtenderProximoTurno(...)` y `obtenerUltimos4Llamados(...)`.


Clean-up, preparación y entrega:

- [ ] T8: Verificar que todos los comentarios `TODO`s del código han sido resueltos y eliminados.
- [ ] T9: Eliminar los archivos (interfaces y tests), paquetes y carpetas de todos aquellos TDAs que no hayan utilizado para resolver el proyecto (en `ar.edu.uns.cs.ed.tdas`).
- [ ] T10: Hacer una verificación final (ejecutando los tests en la clase `SucursalBancariaTest`) para confirmar que el proyecto compila en su totalidad y funciona correctamente.
- [ ] T11: Hacer el push con la versión final del proyecto al repositorio.


Las tareas de implementación las encontrarán reflejadas como comentarios `TODO` en el archivo `SucursalBancaria`. La idea es que eliminen los `TODO`s correspondientes a medida que las van realizando. Estos comentarios son accesibles desde los IDEs forman una lista de tareas pendientes (visible en el panel inferior en la solapa **Problems** en VSCode, y en el panel inferior en la solapa **Tasks** en Eclipse).

Se recomienda ir ejecutando los casos de prueba a medida que se completa cada tarea de implementación, verificando que pasen antes de pasar a la siguiente. Inicialmente, van a fallar los casos de prueba pero a medida que implementen la funcionalidad correctamente, van a empezar a pasar, hasta finalmente, con el proyecto completo, tenerlos todos en verde.

Dentro de la clase `SucursalBancaria` tienen libertad total para utilizar las estructuras y métodos auxiliares que consideren necesarios.

También, son completamente libres para gestionar el historial de cambios de Git como prefieran (trabajar en ramas y mezclarlas, trabajar en ramas y abrir nuevos PRs, trabajar y hacer pushes directos a `main`). Sin embargo, se recomienda que no mezclen ni cierren el PR que se abre automáticamente al iniciar el proyecto para mantener ese canal de comunicación entre uds y con la cátedra abierto.

Por razones de seguridad, se recomienda que hagan pushes de sus cambios al repositorio remoto regularmente, de manera de tener una copia remota actualizada a partir de donde continuar si surgiera algún problema con la local. Cada push/PR que abran va a disparar el proceso de CI (integración continua) que compila, testea y evalúa el proyecto (en efecto se dispara el proceso de entrega), pero esto no es problema ya que para la corrección del proyecto sólo se considerará la última versión que hayan pusheado a la rama `main` a la fecha de entrega.




## Información adicional

Recuerden que los detalles no presentados en este README están en las definiciones de interfaces y clases provistas, y que las cuestiones dinámicas relativas al comportamiento del sistema también pueden analizarlas a partir de los casos de prueba y sus resultados esperados.

Ante cualquier otra cuestión no definida por estos documentos, consulten con la cátedra.