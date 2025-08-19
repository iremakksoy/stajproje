# Java 17 base image kullan
FROM openjdk:17-jdk-slim

# Çalışma dizinini belirle
WORKDIR /app

# Maven ile build edilmiş JAR dosyasını kopyala
COPY target/stajproje-0.0.1-SNAPSHOT.jar app.jar

# Port 8080'i expose et
EXPOSE 8080

# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
