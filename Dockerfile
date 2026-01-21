#FROM maven:3.9.6-eclipse-temurin-21 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests && \
#    rm -f target/*-sources.jar target/*-javadoc.jar
#
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=build /app/target/app.jar app.jar
## Aseguramos permisos de lectura
#USER root
#RUN chmod 644 app.jar
## AÑADE ESTA LÍNEA PARA DEPURAR:
#RUN ls -la /app
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
# Etapa 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copiamos solo el pom.xml primero para aprovechar la caché de Docker de las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 2. Copiamos el código fuente y compilamos
COPY src ./src
# Añadimos -Dkotlin.compiler.execution.strategy="out-process" para evitar problemas con el demonio
RUN mvn clean package -DskipTests -Dkotlin.compiler.execution.strategy="out-process"

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app

# Instalamos curl para poder diagnosticar la red
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Usamos un comodín que solo busque en target para evitar el .jar.original
COPY --from=build /app/target/app.jar app.jar

# Verificamos que el archivo existe ANTES de correr la app (esto fallará la build si no está)
RUN test -f app.jar

# Cambiamos a root momentáneamente para asegurar permisos
USER root
RUN chmod 755 /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]