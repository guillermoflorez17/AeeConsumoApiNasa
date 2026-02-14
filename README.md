# MiApiNasa - APOD Random

Aplicación Android desarrollada en Java que consume la API pública de la NASA (APOD - Astronomy Picture of the Day).

La app no muestra siempre la imagen del día actual, sino que genera una fecha aleatoria dentro de los últimos 10 años y muestra la imagen correspondiente a esa fecha.

---

## Funcionalidades

- Conexión a la API pública de la NASA.
- Generación automática de fecha aleatoria (últimos 10 años).
- Muestra en pantalla:
  - Imagen
  - Título
  - Fecha
  - Explicación
- Manejo de peticiones HTTP.
- Procesamiento de respuesta en formato JSON.
- Carga de imagen desde URL usando Glide.

---

## Tecnologías usadas

- Java
- Android Studio
- HttpURLConnection
- JSON (org.json)
- Glide (para cargar imágenes desde URL)

---

## API utilizada

NASA APOD API  
https://api.nasa.gov/planetary/apod

---

## Cómo ejecutar el proyecto

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en emulador o dispositivo físico.
4. Es necesario tener conexión a Internet.

---

## Autor

Cecilia Rus
