import java.util.List;

public interface Busqueda {
    Producto buscarPorId(List<Producto> productos, int id);
    Producto buscarPorNombre(List<Producto> productos, String nombre);
}
