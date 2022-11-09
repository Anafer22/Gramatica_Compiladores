package Codigo;

/**
 * @author Azael Lobos Gomez y Ana Fernanda Arriaga Morales
 */
public class TipoDato {
    private String[] tipoDato = new String[4];
    private String produccion = "<DECLARACION> -> TD <ID> <INCIO_TD> <LISTA_TD>;";

    public TipoDato() {
        tipoDato[0] = "int";
        tipoDato[1] = "float";
        tipoDato[2] = "char";
        tipoDato[3] = "double";
    }
    
    public String[] getTipoDato() {
        return this.tipoDato;
    }
    public String validarTipoDato(String in) {
        for (String td : tipoDato) {
            if (td.equals(in)) {
                return (produccion = produccion.replace("TD", td));
            }
        }
        return null;
    }
    public String validarAsignacion(String in, String linea) {
        String out = "";
        String aux = "";
        if (linea.contains("int")) {
            aux = linea.replace("<INCIO_int>", "= <ENTERO_SIGNO>") + "\n";
            out += aux;
            if (!in.contains("-") || !in.contains("+")) {
                if (!esValorNumerico(in)) {
                    return null;
                }
                aux = aux.replace("<ENTERO_SIGNO>", "<SIGNO> <ENTERO_DIGITO>");
                out += aux;
                aux = aux.replace("<SIGNO>", "");
                out += aux;
                aux = aux.replace("<ENTERO_DIGITO>", "<DIGITO> <RESTO>");
                out += aux;
                int len = in.length();
                int i = 0;
                while (i < len) {
                    aux = aux.replace("<DIGITO> ", in.charAt(i) + "");
                    out += aux + "";
                    if (i + 1 < len) {
                        aux = aux.replace("<RESTO>", "<DIGITO> <RESTO>");
                        out += aux + "";
                    }else {
                        aux = aux.replace("<RESTO>", "");
                        out += aux;
                        aux = aux.replace("<LISTA_int>", ""); // aqui se debe hacer otra cosa jaja
                        out += aux;
                    }
                    i++;
                }
            }
        }else if (linea.contains("char")) {
            int len = in.length();
            int replace = in.length() - in.replace("'", "").length(); //Esto es cuantas veces aparece el '.
            if (len != 3 || replace != 2) {
                return null;
            }else {
                if (in.charAt(1) >= 0 && in.charAt(1) <= 255) {
                    aux = linea.replace("<INCIO_char>", "= <CARACTER>") + "\n";
                    out += aux;
                    aux = aux.replace("<CARACTER>", "'<CHR>'");
                    out += aux;
                    aux = aux.replace("<CHR>", in.charAt(1) + "");
                    out += aux;
                    aux = aux.replace("<LISTA_char>", "");
                    out += aux;
                }
            }
        }else if (linea.contains("float")) {
            String entero = in.substring(0, in.indexOf("."));
            String decimal = in.substring(in.indexOf(".") + 1);
            if (!esValorNumerico(in)) {
                return null;
            }
            aux = linea.replace("<INCIO_float>", "= <FLOTANTES>");
            out += aux + "\n";
            aux = aux.replace("<FLOTANTES>", "<ENTERO_SIGNO>.<ENTERO_DIGITO>");
            out += aux + "\n";
            aux = aux.replace("<ENTERO_SIGNO>", "<SIGNO><ENTERO_DIGITO>");
            out += aux + "\n";
            aux = sustituirSigno(entero, aux);
            out += aux + "\n";
            out += sustituirEnteroDigito(entero, aux)[1] + "\n";
            aux = sustituirEnteroDigito(entero, aux)[0];
            out += sustituirDecimal(decimal, aux);
        }else if (linea.contains("double")){
            aux = linea.replace("<INCIO_double>", "= <CIENTIFICA>");
            out += aux + "\n";
            aux = aux.replace("<CIENTIFICA>", "<FLOTANTES><EXPONENCIAL><ENTERO_SIGNO>");
            out += aux + "\n";
            aux = aux.replace("<FLOTANTES>", "<ENTERO_SIGNO>.<ENTERO_DIGITO>");
            out += aux + "\n";
            aux = aux.replace("<ENTERO_SIGNO>", "<SIGNO><ENTERO_DIGITO>");
            out += aux + "\n";
            aux = aux.replace("<EXPONENCIAL>", "E");
            out += aux + "\n";
            out += linea.replace("<INCIO_double>", "= <CIENTIFICA>");
        }
        return out;
    }
    public String validarAsignacion(String in, String linea, String td){
        String out = "";
        String aux = "";
        aux = linea.replace("<INCIO_" + td + ">", "");
        out += aux + "\n";
        aux = aux.replace("<LISTA_" + td + ">", "");
        out += aux + "\n";
        return out;

    }
    private String sustituirSigno(String in, String linea){
        String out = "";
        String aux = "";
        switch (in.charAt(0)){
            case '-':
                aux = linea.replace("<SIGNO>", "-");
                out += aux + "\n";
                break;
            case '+':
                aux = linea.replace("<SIGNO>", "+");
                out += aux + "\n";
                break;
            default:
                aux = linea.replace("<SIGNO>", "");
                out += aux + "\n";
                break;
        }
        return out;
    }
    private String[] sustituirEnteroDigito(String in, String linea){
        String aux = "";
        String out = "";
        if (in.contains("-") || in.contains("+")){
            in = in.substring(1, in.length() - 1);
        }
        aux = linea.replace("<ENTERO_DIGITO>.", "<DIGITO><RESTO>.");
        out += aux + "\n";
        int i = 0;
        while (i < in.length()){
            aux = aux.replace("<DIGITO>", in.charAt(i) + "");
            out += aux + "\n";

            if (i + 1 < in.length()){
                aux = aux.replace("<RESTO>", "<DIGITO><RESTO>");
                out += aux + "\n";
            }else{
                aux = aux.replace("<RESTO>", "");
                out += aux + "\n";
            }
            i++;
        }
        String final_out[] = new String[2];
        final_out[0] = aux;
        final_out[1] = out;
        return final_out;
    }

    private String sustituirDecimal(String in, String linea){
        String aux = "";
        String out = "";
        if (in.contains("-") || in.contains("+")){
            in = in.substring(1, in.length() - 1);
        }
        aux = linea.replace(".<ENTERO_DIGITO>", ".<DIGITO><RESTO>");
        out += aux + "\n";
        int i = 0;
        while (i < in.length()){
            aux = aux.replace("<DIGITO>", in.charAt(i) + "");
            out += aux + "\n";

            if(i + 1 < in.length()) {
                aux = aux.replace("<RESTO>", "<DIGITO><RESTO>");
                out += aux + "\n";
            }else {
                aux = aux.replace("<RESTO>", "");
                out += aux + "\n";
            }
            i++;
        }
        return out;
    }
    private boolean esValorNumerico(String in) {
        try{
            Integer.parseInt(in);
            return true;
        }catch (NumberFormatException excepcion) {
            try {
                Float.parseFloat(in);
                return true;
            }catch (NumberFormatException exception) {
                return false;
            }
        }
    }
}