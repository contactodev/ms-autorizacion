# ms-contacto-autorizacion
microservicio de autorizacion de peticiones
Tecnologia: Java con SpringBoot, Hibernate



# Despliegue en AKS (Azure Kubernetes Service)

Este documento describe los pasos necesarios para desplegar una aplicación en Azure Kubernetes Service (AKS) utilizando un archivo de despliegue en formato YAML.

## Paso 1: Generar el archivo JAR ejecutable

Antes de comenzar con el despliegue en AKS, asegúrate de generar el archivo JAR ejecutable de tu aplicación Java utilizando Gradle:

```bash
./gradlew bootJar
```

Esto generará un archivo JAR en la ruta build/libs.

## Paso 2: Compilar la imagen Docker

Una vez generado el archivo JAR, procede a compilar la imagen Docker que utilizará AKS para desplegar tu aplicación:

```bash
docker build -t nombre-de-usuario/nombre-de-la-imagen:tag .
```

Reemplaza nombre-de-usuario, nombre-de-la-imagen y tag con los nombres y etiquetas adecuadas para tu imagen Docker.

## Paso 3: Subir la imagen Docker a Docker Hub

Sube la imagen Docker recién creada a Docker Hub (u otro registro de contenedores que utilices):


```bash
docker push nombre-de-usuario/nombre-de-la-imagen:tag
```

## Paso 4: Desplegar en AKS

Asegúrate de haber iniciado sesión en Azure utilizando az login.

Accede al cluster de AKS en el que deseas desplegar la aplicación.

Sube el archivo de despliegue YAML ubicado en la carpeta k8s de tu proyecto a AKS. Asegúrate de actualizar la versión de la imagen en este archivo YAML con la nueva etiqueta que subiste a Docker Hub.

Aplica el archivo de despliegue YAML en AKS:

```bash
kubectl apply -f archivo-de-despliegue.yaml
```

## Paso 5: Verificar los logs

Después de desplegar la aplicación, verifica los logs para asegurarte de que todo esté funcionando correctamente:

```bash
kubectl logs nombre-del-pod
```

