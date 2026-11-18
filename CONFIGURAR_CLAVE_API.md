# Configurar la clave de Google Books (Bookshelf)

La app consulta la API de Google Books. Sin clave, Google cobra las peticiones
a una cuota compartida que suele estar agotada (respuesta **HTTP 429 – Quota exceeded**).
Con una clave propia, las peticiones usan la cuota de tu proyecto.

## Pasos en otra computadora
1. Crea una clave gratuita:
   - https://console.cloud.google.com → crea o elige un proyecto.
   - **APIs y servicios → Biblioteca → Books API → Habilitar**.
   - **Credenciales → Crear credenciales → Clave de API**. Restríngela a **Books API**.
2. Abre el archivo `local.properties` en la carpeta raíz del proyecto
   (Android Studio lo crea al abrir el proyecto) y añade una línea:
   ```
   BOOKS_API_KEY=TU_CLAVE_AQUI
   ```
3. En Android Studio: **File → Sync Project with Gradle Files** y ejecuta la app.

## Notas
- `local.properties` está en `.gitignore`: la clave **no** se sube al repositorio
  ni se incluye en el ZIP de entrega.
- Gradle copia la clave en `BuildConfig.BOOKS_API_KEY`, y un interceptor de OkHttp
  la añade a cada petición como `&key=...` (ver `data/AppContainer.kt`).
- Si no hay clave, la app funciona igual, pero puede mostrar la pantalla de error
  cuando la cuota compartida está agotada.
