# SIGEP
Proyecto de fin de grado, Desarrollo de Aplicaciones Multiplataforma

---

Este proyecto consiste en una aplicación para gestionar alumnos en prácticas, agilizando las gestiones y unificando en una plataforma las diversas actividades o interacciones que tienen los tutores con sus alumnos.

## Instalación

### Preparación

1. Actualizar el sistema:
    ```bash
    sudo apt update
    sudo apt upgrade -y
    ```

2. Instalar JDK:
    ```bash
    sudo apt install openjdk-21-jdk -y
    ```
    Comprobar que el JDK se ha instalado correctamente:
    ```bash
    java -version
    ```

3. Instalar Maven:
    ```bash
    sudo apt install maven -y    
    ```
    Comprobar que Maven se ha instalado correctamente:
    ```bash
    mvn -v
    ```

4. Instalar Git:
    ```bash
    sudo apt install git -y
    ```
    Comprobar que Git se ha instalado correctamente:
    ```bash
    git --version
    ```

5. Instalar MySQL Server:
    ```bash
    sudo apt install mysql-server -y
    ```
    Comprobar que el servicio esté activo:
    ```bash
    sudo systemctl status mysql
    ```

### Configuración

1. Clonar el repositorio del proyecto:
    ```bash
    git clone https://github.com/AdrianSVHappy/sigep.git
    cd sigep
    ```

2. Inicializar la base de datos mediante el script:
    ```bash
    sudo mysql < scripts/crear.sql
    ```

### Arranque

1. Compilar el proyecto usando Maven:
    ```bash
    sudo mvn clean install -DskipTests
    ```
    (Ignoramos los tests al usar `-DskipTests`)

2. Comprobar la IP del servidor:
    ```bash
    ip a
    ```
    (Se recomienda que la IP del servidor sea estática)

3. Iniciar la aplicación:
    ```bash
    sudo java -jar target/sigep-v1.jar
    ```

Ya se puede acceder a la aplicación mediante este enlace:  
http://{ip}:80

---

Autor: Adrián Suárez Valdayo  
[LinkedIn](https://www.linkedin.com/in/adri%C3%A1n-su%C3%A1rez-valdayo-4583a726a/) | [GitHub](https://github.com/AdrianSVHappy)

