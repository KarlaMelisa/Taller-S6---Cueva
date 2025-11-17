import java.util.List;

public class BusquedaLineal implements Busqueda{

    @Override
    public Producto buscarPorId(List<Producto> productos, int id){
        for(Producto producto : productos){
            if(producto.getId() == id) return producto;
        }
        return null;
    }

    @Override
    public Producto buscarPorNombre(List<Producto> productos, String nombre){
        if (nombre == null) return null;
        for(Producto producto : productos){
            if(producto.getNombre().equals(nombre)) return producto;
        }
        return null;
    }
}
