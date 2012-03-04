/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clases;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabiola
 */
public class Rutas {
    public final static String RAIZ = "/";
    private static int archivos = 0;
    private static int carpetas = 0;
    private static File[] raices = File.listRoots();
    private static double tamaño = 0;
    private static long tam = 0;
    private static String ARCHIVO = "0";
    private static String CARPETA = "1";
    private static String[] ruta = new String[2];
    private static String resultado = "";


    public static void setContadoresEnCero(){
        archivos = 0;
        carpetas = 0;
        tamaño = 0;
        tam = 0;
    }

    public static int getCantidadArchivos() {
        return archivos;
    }

    public static int getCantidadCarpetas() {
        return carpetas;
    }

    /**
     * @return the tamaño
     */
    public static double getTamaño(int unidadDeMedida) {
        switch(unidadDeMedida){
            case UnidadDeMedida.BYTE: {
                return tamaño;
            }
            case UnidadDeMedida.KILOBYTE: {
                return tamaño / 1024;
            }
            case UnidadDeMedida.MEGABYTE: {
                return (tamaño / 1024) / 1024;
            }
            case UnidadDeMedida.GIGABYTE: {
                return ((tamaño / 1024) / 1024)/1024;
            }
        }
        return 0;
    }

    /**
     * @param aResultado the resultado to set
     */
    public static void setResultado(String aResultado) {
        resultado = aResultado;

    }

    /**
     * @return the tam
     */
    public static long getTam() {
        return tam;
    }

    /**
     * @return the resultado
     */
    public static String getResultado() {
        return resultado;
    }
    private int carp, arch;


    public static String imprimirTodo(File arc){
        try{
            for(int i=0;i<arc.listFiles().length; i++){
                tamaño += arc.listFiles()[i].length();
                tam += arc.listFiles()[i].length();
                ruta = getNombre(arc.listFiles()[i]);

                if(!ruta[0].equalsIgnoreCase("error")){
                    int cant = Integer.parseInt(ruta[2]);

                    for(int j=0;j<cant;j++){
                        if(j == cant -1){
                            setResultado(getResultado() + "|____");
                            System.out.print("|____");
                        }else{
                            setResultado(getResultado() + "    ");
                            System.out.print("    ");
                        }
                    }
                    setResultado(getResultado() +  ruta[1] + "\n");
                    System.out.print( ruta[1] + "\n");
                    if(arc.listFiles()[i].isDirectory()){
                        carpetas++;
                        imprimirTodo(arc.listFiles()[i]);
                    }else{
                        archivos++;
                    }
                }else{
                    setResultado("");
                }

            }
            return getResultado();
        }catch(NullPointerException e){
            Mensajes.mensajeError("Ha ocurrido un Error:"+e.getCause());
//            System.exit(0);
        }catch(Exception e){
            Mensajes.mensajeError("Ha ocurrido un Error"+e.getMessage());
//            System.exit(0);
        }finally{
            return getResultado();
        }
        
    }

     

    public static String imprimirSoloCarpetas(File arc){
        try{
            for(int i=0;i<arc.listFiles().length; i++){
                if(arc.listFiles()[i].isDirectory()){
                    tamaño += arc.listFiles()[i].length();
                    tam += arc.listFiles()[i].length();
                    carpetas++;



                    ruta = getNombre(arc.listFiles()[i]);
                    if(!ruta[0].equalsIgnoreCase("error")){
                        int cant = Integer.parseInt(ruta[2]);

                        for(int j=0;j<cant;j++){
                            if(j == cant -1){
                                setResultado(getResultado() + "|____");
                                System.out.print("|____");
                            }else{
                                setResultado(getResultado() + "    ");
                                System.out.print("    ");
                            }
                        }

                        setResultado(getResultado() +  ruta[1] + "\n");
                        System.out.print( ruta[1] + "\n");
                        imprimirSoloCarpetas(arc.listFiles()[i]);
                    }else{
                        setResultado("");
                    }

                }
            }
            return getResultado();
        }catch(NullPointerException e){
            Mensajes.mensajeError("Ha ocurrido un Error:"+e.getCause());
//            System.exit(0);
        }catch(Exception e){
            Mensajes.mensajeError("Ha ocurrido un Error: "+e.getMessage());
//            System.exit(0);
        }finally{
            return getResultado();
        }
    }

    public static String[] getRaices(){
        String[] roots = new String[raices.length];
        for(int i=0;i<raices.length;i++){
            roots[i] = raices[i].getPath();
        }

        return roots;
    }

    private static String[] getNombre(File archivo){//para que me retorne solamente el nombre de la carpeta o archivo, sin la ruta entera
        String[] r = new String[3];
        try {
            //para que me retorne solamente el nombre de la carpeta o archivo, sin la ruta entera
            String[] cantidadDeCarpetas = archivo.getParent().split((!P.SO.equalsIgnoreCase("Linux")?"\\\\":"/"));

            
            r[1] = archivo.getCanonicalPath();
            r[2] = Integer.toString(cantidadDeCarpetas.length);
            if(archivo.isDirectory()){
                r[0] = CARPETA;
            }else{
                r[0] = ARCHIVO;
            }
            for(int i=r[1].length()-1;i>=0;i--){
                if(r[1].charAt(i) == File.separator.charAt(0)){
                    r[1] = r[1].substring(i+1);
                    break;
                }
            }
            return r;
        } catch (Exception ex) {
            Mensajes.mensajeError("Ha Ocurrido un Error");
            r[0] = "error";
            return r;
        }
    }
}
