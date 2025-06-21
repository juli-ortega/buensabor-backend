FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Configurar JAVA_HOME correctamente
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

# Instalar Maven como usuario vscode
ARG INSTALL_MAVEN="true"
ARG MAVEN_VERSION="3.9.6"  # o dejalo vacío si querés la última

RUN if [ "${INSTALL_MAVEN}" = "true" ]; then \
    su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven ${MAVEN_VERSION}"; \
fi

# Copiar código fuente
WORKDIR /app
COPY . .

# Compilar usando el wrapper de Maven como usuario vscode
RUN su vscode -c "cd /app && chmod +x ./mvnw && ./mvnw -DskipTests clean install"
