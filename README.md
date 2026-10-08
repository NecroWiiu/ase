# BoatCraft Builder — Fabric 1.20.1

Sistema de construcción por piezas para Minecraft Java 1.20.1, inspirado en el flujo de construcción de Build a Boat for Treasure, pero con una interfaz y assets propios y sin incluir su mapa.

## Incluido

- Builder Tool con menú minimalista.
- 18 bloques iniciales de construcción; el sistema usa IDs de bloque de Minecraft para poder ampliar la lista sin cambiar la mecánica.
- Escala por píxel: `1/16` de bloque por clic, entre `1/16` y `16x`.
- Ejes X/Y/Z para escala.
- Trowel Tool con rotación de `22.5°` por eje.
- Eraser Tool.
- Anchor Tool para anclar/desanclar.
- Physics Tool para liberar piezas y aplicar una pequeña fuerza inicial.
- Física sencilla de gravedad, inercia, rozamiento y rebote.
- Transparency Tool con pasos del 10% de opacidad.
- Persistencia NBT de bloque, escala, rotación, opacidad y anclaje.
- Sincronización del bloque seleccionado mediante Fabric networking.
- Proyecto listo para GitHub + CI.

## Controles

### Builder Tool

- Click derecho o `B`: abre el menú.
- Pulsa un bloque en el menú.
- Click derecho en una cara de un bloque vanilla: coloca una pieza.

### Scale Tool

- Click derecho: `+1/16` en el eje activo.
- Click izquierdo: `-1/16`.
- Shift + click: cambia entre X, Y y Z.

### Trowel Tool

- Click derecho: `+22.5°`.
- Click izquierdo: `-22.5°`.
- Shift + click derecho: cambia el eje de rotación.

### Erase / Anchor / Physics / Transparency

- Click derecho sobre una pieza para aplicar la acción.
- Physics: click derecho libera y empuja; click izquierdo vuelve a anclar.
- Transparency: click derecho reduce opacidad; click izquierdo la recupera.

## Instalación

1. Usa Fabric Loader para Minecraft 1.20.1.
2. Instala Fabric API.
3. Compila este proyecto con `gradle build` o usa GitHub Actions.
4. Coloca el JAR generado de `build/libs` en `mods`.

## Fabulously Optimized

El mod evita dependencias de renderizado externas y está planteado para Fabric 1.20.1, por lo que está diseñado para convivir con un pack de optimización basado en Fabric como Fabulously Optimized. La compatibilidad final depende de la versión exacta del pack y de otros mods.

## Desarrollo / decompilación

Usa Java 17.

```bash
./gradlew genSources
./gradlew runClient
./gradlew build
```

Loom es el encargado del entorno de desarrollo y de los sources/decompilación de Minecraft.

## Arquitectura

Las piezas personalizables se guardan como `BuildBlockEntity` en lugar de sustituir bloques vanilla. Esto permite escala sub-bloque, rotación, opacidad y física en una misma pieza.

La física de esta primera versión es intencionalmente ligera; un sistema de cuerpos rígidos completo requeriría una capa adicional de detección de contactos y resolución de colisiones.

## Obtener las herramientas rápidamente

En un mundo creativo puedes usar:

```mcfunction
/give @s boatcraft:builder_tool
/give @s boatcraft:erase_tool
/give @s boatcraft:scale_tool
/give @s boatcraft:trowel_tool
/give @s boatcraft:anchor_tool
/give @s boatcraft:transparency_tool
/give @s boatcraft:physics_tool
```
