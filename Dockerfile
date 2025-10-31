# ---------- ETAPA 1: construir el .jar con Gradle ----------
FROM gradle:jdk24 AS build
WORKDIR /app

# copiamos todo el proyecto
COPY . .

# nos aseguramos de que el wrapper tenga permiso de ejecución (problema del "Permission denied")
RUN chmod +x gradlew

# compilamos y generamos el .jar ejecutable de Spring Boot
# -x test para no correr tests en Render Free
RUN ./gradlew clean bootJar -x test

# ---------- ETAPA 2: imagen final ligera para correr la app ----------
FROM eclipse-temurin:24-jdk
# eclipse-temurin es la distro OpenJDK mantenida por Adoptium; tiene builds oficiales hasta Java 24,
# que es lo que tú estás usando (Java 24.0.2 en tus logs). :contentReference[oaicite:5]{index=5}

WORKDIR /app

# copiamos el jar construido (el wildcard *.jar evita que tengas que adivinar el nombre exacto)
COPY --from=build /app/build/libs/*.jar app.jar

# EXPONEMOS el puerto interno donde escucha Spring Boot.
# Tu app local levanta en 8081 (lo vimos en el log: "Tomcat initialized with port 8081").
EXPOSE 8081

# Render necesita que tu contenedor escuche en algún puerto accesible en 0.0.0.0.
# Render intenta autodetectarlo, pero también puedes ayudarle poniendo la env var PORT.
# Por defecto Render usa PORT=10000, pero tú puedes decirle PORT=8081 en la configuración del servicio. :contentReference[oaicite:6]{index=6}
ENV PORT=8081

# Comando final para arrancar Spring Boot
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
