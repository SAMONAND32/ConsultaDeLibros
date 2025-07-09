# 📚 Desafío: Consultas de Libros desde Consola

Este proyecto es un desafío orientado a practicar el uso de la consola en Java, cumpliendo con los cinco puntos básicos solicitados.

El programa realiza consultas a la API pública [Gutendex](https://gutendex.com/) para buscar libros por título, como por ejemplo: *Don Quijote*, *Niebla*, *Moby Dick*, entre otros.

---

## 🔍 Funcionalidades principales

Al ejecutar el programa, se despliega un menú con ocho (5) opciones. A continuación se describen:

### 1. Buscar libro por título  
- Consulta si el libro ingresado existe en la API.  
- Si se encuentra, verifica si ya está en la base de datos local; de no estar, lo agrega.  

### 2. Listar libros registrados  
- Muestra todos los libros almacenados en el repositorio local.  

### 3. Listar autores registrados  
- Muestra los autores que se han registrado en la base de datos.  

### 4. Listar autores vivos en un año determinado  
- Solicita un año (por ejemplo: 1800, 1900, etc.).  
- Muestra los autores que estaban vivos en ese momento.  
- Si no hay datos de nacimiento o muerte, se usa `-1` o `5000` como valores por defecto.

### 5. Listar libros por idioma  
- Permite seleccionar un idioma desde un submenú.  
- Muestra los libros correspondientes.  
- Si el idioma no está identificado, aparece como `"desconocido"`.

> ✅ Estas cinco opciones cubren los requisitos mínimos del desafío.

### 0. Salir  
- Finaliza la ejecución del programa.

---

## 🗂️ Información almacenada por libro

Además del título, se almacenan los siguientes datos:

- Nombre(s) del autor o autores  
- Idioma(s) del libro  
- Año de nacimiento y fallecimiento del autor  
