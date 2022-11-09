package Codigo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Ana Fernanda Arriaga Morales y Azael Lobos Gomez
 */
public class Identificador {
    private static String salida = "";
    
    public static boolean isValidIdentifier(String identifier) {
        String regex = "^([a-zA-Z_$][a-zA-Z\\d_$]*)$";
        Pattern p = Pattern.compile(regex);
        if (identifier == null) {
            return false;
        }
        Matcher m = p.matcher(identifier);
        return m.matches();
    }
    public static String validarId(String in, String resto_salida) {
        String out = "";
        String aux = "";
        if (in.contains(";")) {
            in = corregirString(in); // hola;
        }
        int len = in.length();
        if (validarCaracter(in.charAt(0))){
            if (len == 1){
                if (esLetra(in.charAt(0))){
                    out += resto_salida + "\n";
                    aux = resto_salida.replace("<ID>", "<LETRA><RESTO_ID>");
                    out += aux + "\n";
                    aux = aux.replace("<LETRA>", in.charAt(0) + "");
                    out += aux + "\n";
                }else if (in.charAt(0) == '_'){
                    out += resto_salida + "\n";
                    aux = resto_salida.replace("<ID>", "_<RESTO_ID>");
                    out += aux + "\n";
                }
                aux = aux.replace("<RESTO_ID> ", "");
                out += aux + "\n";
            }else if (len >= 2) {
                if (!validarCaracter(in.charAt(0))) {
                    return null;
                }
                aux = resto_salida;
                if (esLetra(in.charAt(0))){
                    aux = aux.replace("<ID>", "<LETRA><RESTO_ID>");
                    out += aux + "\n";
                    aux = aux.replace("<LETRA>", in.charAt(0) + "");
                    out += aux + "\n";
                }else if (in.charAt(0) == '_') {
                    aux = aux.replace("<ID>", "_<RESTO_ID>");
                    out += aux + "\n";
                }
                int i = 1;
                while (i < len) {
                    char e = in.charAt(i);
                    if (e == '_') {
                        aux = aux.replace("<RESTO_ID>", "_<RESTO_ID>");
                        out += aux + "\n";
                    } else if (esNumero(e)) {
                        aux = aux.replace("<RESTO_ID>", "<DIGITOS><RESTO_ID>");
                        out += aux + "\n";
                        aux = aux.replace("<DIGITOS>", e + "");
                        out += aux + "\n";
                    } else if (esLetra(e)) {
                        aux = aux.replace("<RESTO_ID>", "<LETRA><RESTO_ID>");
                        out += aux + "\n";
                        aux = aux.replace("<LETRA>", e + "");
                        out += aux + "\n";
                    }
                    i++;
                }
                aux = aux.replace("<RESTO_ID>", "");
                out += aux + "\n";
            }
        }else { 
            return null;
        }
        return out;
    }
    private static boolean esLetra(char c){
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
            return true;
        }
        return false;
    }
    public static boolean esNumero(char c){
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }
    private static boolean validarCaracter(char c){
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122) || c == '_') {
            return true;
        }
        return false;
    }
    private static String corregirString(String in) {
        return (in.substring(0, in.length() - 1)).toString();
    }
}
