package Codigo;

/**
 * @author Ana Fernanda Arriaga Morales y Azael Lobos Gomez
 */
public class Inicio_Gramatica {

    private static String[] split;
    private static int len;

    public static void main(String args[]) {
        TipoDato tipoDato = new TipoDato();

        String salida = "<VARIABLE> -> <DECLARACION> ;\n";
        String aux = "";

        //String str = "int x = 10;"; // int x;
        //String str = "int _x1;";
        String str = "int x_1 = 3";
        //String str = "float pi = 3.141592";
        //String str = "double pi = 3.141592653589793238462643383279";
        //
        System.out.println("Entrada: " + str);
        split = str.split(" "); //5
        len = split.length; //
        if (len >= 2) {
            String td = split[0];
            if (tipoDato.validarTipoDato(td) != null) {
                aux = (tipoDato.validarTipoDato(td));
                salida += aux + "\n";
                String restoredString = restablecerEntrada();
                if (restoredString.contains("=")) {
                    String id = restoredString.substring(0, restoredString.indexOf('='));
                    String aux2 = Identificador.validarId(id, aux);
                    //salida += aux2;
                    if (aux2 != null) {
                        //Continua con la validacion de la asignacion
                        salida += aux2;
                        String asignacion[] = restoredString.split("=");
                        if (asignacion.length == 2) {
                            aux2 = (aux2.split("\n")[aux2.split("\n").length - 1]);
                            aux2 = tipoDato.validarAsignacion(asignacion[1].replace(";", ""), aux2);
                            if (aux2 == null) {
                                salida = "Error en la expresion evaluada.\n Asigacion no valida";
                            } else {
                                salida += aux2;
                            }
                        }
                    } else {
                        salida = "Error en la expresion evaluada.\nID no valido";
                    }
                } else {
                    String id = split[1];
                    String aux2 = Identificador.validarId(id, aux);
                    salida += aux2;
                    aux2 = (aux2.split("\n")[aux2.split("\n").length - 1]);
                    aux2 = tipoDato.validarAsignacion("", aux2, td);
                    salida += aux2;

                }
            } else {
                salida = "Error en la expresion evaluada.\nTipo de dato no valido.";

            }//Agregar else

        }
        System.out.println(salida);
    }

    private static String restablecerEntrada() {
        String restoredString = "";
        for (int i = 1; i < len; i++) {
            restoredString += split[i];
        }
        return restoredString;
    }
}
