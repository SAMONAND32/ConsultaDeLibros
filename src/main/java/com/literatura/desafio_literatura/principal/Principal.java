package com.literatura.desafio_literatura.principal;


import com.literatura.desafio_literatura.model.Autor;
import com.literatura.desafio_literatura.model.Datos;
import com.literatura.desafio_literatura.model.Libro;

import com.literatura.desafio_literatura.repository.AutorRepository;
import com.literatura.desafio_literatura.repository.LibroRepository;
import com.literatura.desafio_literatura.service.ConsumoAPI;
import com.literatura.desafio_literatura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    private static final String raya = "--------------------------------------------";

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository)  {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private final String mensaje = "\n******* No se encontraron resultados *******";

    public Principal() {}

    public void mostrarMenu() {
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    
                    *** Elija una de las siguientes alternativas: ***
                    
                    1.- Buscar libro por título
                    2.- Listar libros registrados
                    3.- Listar autores registrados
                    4.- Listar autores vivos en un determinado año
                    5.- Listar libros por idioma(s)
                    
                    0.- Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        buscarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Gracias por su preferencia. Nos vemos pronto...\n");
                        break;
                    default:
                        System.out.println("Opción incorrecta...");
                }
            } catch(InputMismatchException e) {
                System.out.println("Entrada inválida. Debes ingresar un número.\n");
                teclado.nextLine();
            }
        }
    }

    private void buscarLibroPorTitulo() {
        Datos datos = getDatosLibro();

        if (datos != null) {
            Libro libro = new Libro(datos.resultados().get(0));

            if (libroRepository.findByTituloContainingIgnoreCase(libro.getTitulo()).isPresent()) {
                System.out.println("\n*********** Este libro ya está en nuestra base de datos *********");
            } else {
                libro = libroRepository.save(libro);
                System.out.println("\n********* Se ha agregado un nuevo libro a nuestra base de datos *******");
                System.out.println("Su título es= " + libro.getTitulo());
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.println("\n******* Ingrese el nombre del libro que desea buscar ******");
        var tituloLibro = teclado.nextLine();
        tituloLibro = tituloLibro.replace(" ", "%20");
        System.out.println("Titulo= " + tituloLibro);
        var json = consumoApi.obtenerDatos(URL_BASE + tituloLibro);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        if (datos.resultados().isEmpty() || datos.resultados().get(0).titulo() == null) {
            System.out.println("El título buscado no está en la api...");
            return null;
        } else {
            System.out.println("Se ha encontrado una coincidencia en la api...\n");
        }

        return datos;
    }


    private void buscarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();

        if(!libros.isEmpty()) {
            System.out.println("\n******** Lista de libros en la base de datos **********");
            for(Libro libro : libros) {
                System.out.println("Titulo= " + libro.getTitulo());
                System.out.println("Autor(es)= " + libro.getAutores());
                System.out.println("Idioma(s)= " + libro.getIdiomas());
                System.out.println("Descargas=" + libro.getDescargas());
                System.out.println(raya);
            }
        } else {
            System.out.println(mensaje);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        System.out.println("\n****************** Lista de Autores en la base de datos ****************");

        if(!autores.isEmpty()) {
            for(Autor autor : autores) {
                System.out.println("Nombre= " + autor.getNombre());
                System.out.println("Fecha de Nacimiento= " + autor.getNacimiento());
                System.out.println("Fecha de muerte= " + autor.getFallecimiento());
                System.out.println("Libros= " + autor.getLibros().getTitulo());
                System.out.println(raya);
            }
        } else {
            System.out.println(mensaje);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("\n*********** Escriba el año que desea investigar ***********");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = autorRepository.findForYear(fecha);

        System.out.println("\n***** Autores vivos en " + fecha + " *******\n");

        if(!autores.isEmpty()) {
            for(Autor autor : autores) {
                System.out.println("Nombre= " + autor.getNombre());
                System.out.println("Fecha de Nacimiento= " + autor.getNacimiento());
                System.out.println("Fecha de muerte= " + autor.getFallecimiento());
                System.out.println("Libros= " + autor.getLibros().getTitulo());
                System.out.println(raya);
            }
        } else {
            System.out.println(mensaje);
        }
    }

    private void listarLibrosPorIdioma() {
        var seleccion = -1;
        var idiomaABuscar = "nd";
        while(seleccion !=0) {
            var menuIdioma = """
                    **** Ingrese el idioma a buscar ****
                    1.- Español
                    2.- Inglés
                    3.- Francés
                    4.- Italiano
                    
                    0.- Salir de la búsqueda
                    ************************************
                    """;

            System.out.println(menuIdioma);

            try {
                seleccion = teclado.nextInt();
                teclado.nextLine();

                switch (seleccion) {
                    case 1:
                        idiomaABuscar = "es";
                        break;
                    case 2:
                        idiomaABuscar = "en";
                        break;
                    case 3:
                        idiomaABuscar = "fr";
                        break;
                    case 4:
                        idiomaABuscar = "it";
                        break;
                    case 0:
                        System.out.println("Saliendo de la búsqueda por idiomas...\n");
                        break;
                    default:
                        System.out.println("Selección no aceptada...\n");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un valor\n");
                teclado.nextLine();
            }

            List<Libro> libros = libroRepository.buscarPorIdioma(idiomaABuscar);

            if (!libros.isEmpty()) {
                for (Libro libro : libros) {
                    System.out.println("\n");
                    System.out.println("Título= " + libro.getTitulo());
                    System.out.println("Autor= " + libro.getAutores());
                    System.out.println("Idioma(s)= " + libro.getIdiomas());
                    System.out.println("Descargas= " + libro.getDescargas());
                }
            } else {
                System.out.println(mensaje);
            }
        }
    }
}

