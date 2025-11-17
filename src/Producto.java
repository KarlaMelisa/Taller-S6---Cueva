public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int[] ventasMensuales = new int[3];
    public Producto(int id, String nombre, double precio) {
        this.id=id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void registrarVenta(int mesIndex, int cantidad) throws Exception{
        if (cantidad < 0) {
            throw new Exception("Cantidad debe ser positiva.");
        }
        ventasMensuales[mesIndex] += cantidad;
    }

    public int getVentasMes(int mesIndex) throws Exception{
        if (mesIndex < 0 || mesIndex >= ventasMensuales.length) {
            throw new Exception("Mes debe ser 1, 2 o 3.");
        }
        return ventasMensuales[mesIndex];
    }

    public int getVentasTotales() {
        int total = 0;
        for (int v: ventasMensuales) {
            total += v;
        }
        return total;
    }
    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + '}';
    }
}
