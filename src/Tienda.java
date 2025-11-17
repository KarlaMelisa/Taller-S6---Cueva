import java.util.ArrayList;
import java.util.List;

public class Tienda {
    private List<Producto> catalogo;
    private Busqueda busqueda;

    public Tienda(Busqueda busqueda){
        this.catalogo = new ArrayList<Producto>();
        this.busqueda = busqueda;
    }

    public List<Producto> todos(){
        return catalogo;
    }

    public void agregar(Producto p){
        catalogo.add(p);
    }

    public boolean editar(int id, Producto nuevo){
        for (int i = 0; i < catalogo.size(); i++){
            if (catalogo.get(i).getId() == id){
                catalogo.set(i, nuevo);
                return true;
            }
        }
        return false;
    }

    public boolean registrarVentaId(int id, int mes, int cantidad){
        Producto producto = busqueda.buscarPorId(catalogo, id);
        if (producto == null) return false;
        try {
            producto.registrarVenta(mes - 1, cantidad);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarPrecioId(int id, double nuevoPrecio){
        Producto producto = busqueda.buscarPorId(catalogo, id);
        if (producto == null) return false;
        producto.setPrecio(nuevoPrecio);
        return true;
    }

    public Producto buscarPorId(int id){
        return busqueda.buscarPorId(catalogo, id);
    }

    public Producto buscarPorNombre(String nombre){
        return busqueda.buscarPorNombre(catalogo, nombre);
    }

    public int sumaVentasRecursiva(int index){
        if (index >= catalogo.size()) return 0;
        return catalogo.get(index).getVentasTotales() + sumaVentasRecursiva(index + 1);
    }

    public int conteoProductosRecursivo(int index){
        if (index >= catalogo.size()) return 0;
        return 1 + conteoProductosRecursivo(index + 1);
    }
}
