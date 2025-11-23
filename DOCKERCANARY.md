# 🚀 Guía de Demostración: Despliegue Canary Local

Esta guía te llevará paso a paso para demostrar el flujo de despliegue **Canary** en tu máquina local usando Docker.

---

## 🛠️ 1. Preparación Inicial (Estado Base)

**Objetivo:** Mostrar que tenemos una infraestructura redundante donde ambas versiones son idénticas y funcionan correctamente.

> **⚠️ IMPORTANTE:** Asegúrate de que tu código **NO** tenga el endpoint `/version` todavía.

Ejecuta estos comandos en tu terminal:

```bash
# 1. Construir imagen base (Versión 1)
docker build -t serviciudad:latest .
docker tag serviciudad:latest serviciudad:stable

# 2. Limpiar contenedores viejos
docker rm -f serviciudad-stable serviciudad-canary

# 3. Iniciar AMBAS versiones con la misma imagen
# 🟦 Estable (Puerto 8080)
docker run -d --name serviciudad-stable -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev serviciudad:stable

# 🟨 Canary (Puerto 8081) - Inicialmente es igual a la estable
docker run -d --name serviciudad-canary -p 8081:8080 -e SPRING_PROFILES_ACTIVE=dev serviciudad:stable
```

### ✅ Prueba de Paridad
Abre estas dos URLs en tu navegador. Ambas deben devolver **exactamente el mismo JSON**:

*   **🟦 Estable:** `http://localhost:8080/api/v1/clientes/0001234567/deuda-consolidada`
*   **🟨 Canary:** `http://localhost:8081/api/v1/clientes/0001234567/deuda-consolidada`

> 🗣️ **Narrativa:** *"Aquí vemos que tenemos dos entornos paralelos idénticos respondiendo correctamente."*

---

## 💻 2. Introducir el Cambio (Nueva Funcionalidad)

**Objetivo:** Simular el desarrollo de una nueva característica.

Modifica el archivo `src/.../DeudaController.java` y agrega este endpoint:

```java
@GetMapping("/version")
public String getVersion() {
    return "¡Hola! Soy la Versión CANARY (Nueva) 🐥";
}
```

---

## 🚀 3. Desplegar SOLO en Canary

**Objetivo:** Actualizar **solo** el entorno de pruebas (8081) sin afectar a los usuarios del entorno estable (8080).

```bash
# 1. Construir la nueva imagen (Versión 2)
docker build -t serviciudad:latest .

# 2. Reiniciar SOLO el contenedor Canary
docker rm -f serviciudad-canary
docker run -d --name serviciudad-canary -p 8081:8080 -e SPRING_PROFILES_ACTIVE=dev serviciudad:latest
```

---

## 🔍 4. La Demostración (El momento "Wow")

**Objetivo:** Mostrar que los entornos ahora son diferentes.

### 1. 🟦 Versión Estable (8080) - Usuarios Reales
*   Prueba el endpoint nuevo: `http://localhost:8080/api/v1/clientes/version`
*   **Resultado:** ❌ **Error 404** (No existe).
*   *Nota: El servicio sigue funcionando normal para lo demás.*

### 2. 🟨 Versión Canary (8081) - Pruebas
*   Prueba el endpoint nuevo: `http://localhost:8081/api/v1/clientes/version`
*   **Resultado:** ✅ **"¡Hola! Soy la Versión CANARY (Nueva) 🐥"**.

> 🗣️ **Narrativa:** *"Como ven, hemos desplegado una nueva funcionalidad en el puerto 8081 sin interrumpir ni modificar el servicio estable en el puerto 8080."*

---

## 🏆 5. Promoción a Estable (Finalizar)

**Objetivo:** Si la prueba fue exitosa, "aprobamos" el cambio y actualizamos la versión estable.

```bash
# 1. Actualizar la etiqueta 'stable' para que apunte a la nueva versión
docker tag serviciudad:latest serviciudad:stable

# 2. Actualizar el contenedor estable
docker rm -f serviciudad-stable
docker run -d --name serviciudad-stable -p 8080:8080 -e SPRING_PROFILES_ACTIVE=dev serviciudad:stable
```

### ✅ Verificación Final
Abre `http://localhost:8080/api/v1/clientes/version`
👉 Ahora la versión estable también dice: **"¡Hola! Soy la Versión CANARY (Nueva) 🐥"**.

---
**🎉 ¡Demostración Completada!**
