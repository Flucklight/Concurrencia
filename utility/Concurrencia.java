package utility;

import java.io.*;
import java.util.ArrayList;

public class Concurrencia {
    private ArrayList<Variable> conjuntoVariables;
    private File codigo1;
    private File codigo2;
    private BufferedReader c1;
    private BufferedReader c2;

    public Concurrencia() throws IOException {
        conjuntoVariables = new ArrayList();
        codigo1 = new File(System.getProperty("user.dir") + "/src/items/Suma.java");
        codigo2 = new File(System.getProperty("user.dir") + "/src/items/Resta.java");
        c1 = new BufferedReader(new FileReader(codigo1));
        c2 = new BufferedReader(new FileReader(codigo2));
        concurrencua();
    }

    public static void main(String[] args) {
        try {
            Concurrencia c = new Concurrencia();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void concurrencua() throws IOException {
        boolean p1 = true;
        boolean p2 = true;
        String linea1 = c1.readLine();
        String linea2 = c2.readLine();
        while (p1 || p2) {
            declaracion(linea1);
            declaracion(linea2);
            asignacion(linea1);
            asignacion(linea2);
            lectura(linea1);
            lectura(linea2);
            for (Variable variable : conjuntoVariables) {
                if (variable.isEscritura() && variable.isLectura()) {
                    System.out.println("Error de Concurrencia -> " +
                            "Un programa escribe la variable " + variable.getEtiqueta() +
                            " y otro la lee");
                }
            }
            reinicio();
            if ((linea1 = c1.readLine()) == null) {
                p1 = false;
            }
            if ((linea2 = c2.readLine()) == null) {
                p2 = false;
            }
        }
    }

    public void declaracion(String s) {
        String[] c;
        s = s.trim();
        c = s.split(" ");
        for (int i = 0; i < c.length; i++) {
            if (c[i].equals("int") || s.contains("float") || s.contains("double")) {
                s = c[i + 1];
                if (conjuntoVariables.isEmpty()){
                    conjuntoVariables.add(new Variable(s));
                } else {
                    for (int j = 0; j <= conjuntoVariables.size(); j++) {
                        if (j == conjuntoVariables.size()) {
                            conjuntoVariables.add(new Variable(s));
                            break;
                        } else if (conjuntoVariables.get(j).getEtiqueta().equals(s)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void asignacion(String s) {
        String[] c;
        s = s.trim();
        c = s.split(" ");
        for (int i = 0; i < c.length; i++) {
            if (c[i].equals("=")) {
                s = c[i - 1];
                for (Variable variable: conjuntoVariables) {
                    if (variable.getEtiqueta().equals(s)) {
                        if (variable.isEscritura()) {
                            System.out.println("Error de Concurrencia -> " +
                                    "Ambos programas escriben la variable " +
                                    variable.getEtiqueta() +
                                    " al mismo tiempo");
                            break;
                        } else {
                            variable.setEscritura(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void lectura(String s) {
        String[] c;
        s = s.trim();
        c = s.split(" ");
        for (int i = 0; i < c.length; i++) {
            for (Variable variable: conjuntoVariables) {
                if (c[i].equals(variable.getEtiqueta())) {
                    for (int j = i; j >= 0; j--) {
                        if (c[j].equals("=") || c[j].equals("System.out.println(")) {
                            variable.setLectura(true);
                        }
                    }
                }
            }
        }
    }

    public void reinicio() {
        for (Variable variable : conjuntoVariables) {
            variable.reinicio();
        }
    }

    public void AnalisisLexico() {
        // Aguilizara el programa haciendo mas rapido cada proceso
        // sobre todo la lectura.
    }
}
