# Usa una imagen base compatible con Java 21
FROM eclipse-temurin:21-jdk-alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR de la aplicación al contenedor
COPY target/tu-aplicacion.jar /app/tu-aplicacion.jar

# Expone el puerto que tu aplicación va a usar
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/tu-aplicacion.jar"]
