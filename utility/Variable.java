package utility;

public class Variable {
    private String etiqueta;
    private boolean lectura;
    private boolean escritura;

    public Variable(String etiqueta) {
        this.etiqueta = etiqueta;
        lectura = false;
        escritura = false;
    }

    public void reinicio() {
        lectura = false;
        escritura = false;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public boolean isLectura() {
        return lectura;
    }

    public void setLectura(boolean lectura) {
        this.lectura = lectura;
    }

    public boolean isEscritura() {
        return escritura;
    }

    public void setEscritura(boolean escritura) {
        this.escritura = escritura;
    }
}
