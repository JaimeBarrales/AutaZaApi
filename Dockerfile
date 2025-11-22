# =========================
# ETAPA 1: BUILD CON MAVEN
# =========================
FROM maven:3.9-eclipse-temurin-17 AS build

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiamos solo el pom primero (para aprovechar caché de dependencias)
COPY pom.xml .

# Descargamos dependencias (sin compilar aún)
RUN mvn -q -e -B dependency:go-offline

# Ahora copiamos el código fuente
COPY src ./src

# Compilamos y generamos el JAR (saltando tests para que sea más rápido)
RUN mvn -q -e -B clean package -DskipTests


# =========================
# ETAPA 2: IMAGEN FINAL (RUN)
# =========================
FROM eclipse-temurin:17-jre

# Carpeta de trabajo
WORKDIR /app

# Copiamos el JAR generado desde la etapa de build
# (usamos wildcard para no depender del nombre exacto)
COPY --from=build /app/target/*.jar app.jar

# Perfil que quieres usar (prod para que lea application-prod.properties)
ENV SPRING_PROFILES_ACTIVE=prod

# Puerto donde corre tu Spring Boot
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java","-jar","app.jar"]
