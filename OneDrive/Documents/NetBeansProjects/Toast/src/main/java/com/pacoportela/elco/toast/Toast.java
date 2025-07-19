package com.pacoportela.elco.toast;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author Paco Portela Henche
 * @date 18/07/25 
 * Esta clase muestra un mensaje corto en la pantalla. Utiliza la
 * funcionalidad de la clase Timer para ejecutar tareas en unos tiempos
 * especificados.
 */
public class Toast {

    // La ventana donde se visualizará el mensaje.
    JDialog dialogo;

    /**
     * Constructor de la clase Toast.
     * 
     * @param aviso El texto que se mostrará en el mensaje.
     * @param salir Un flag que indica si queremos salir de programa después de
     * mostrar el mensaje.
     */
    public Toast(String aviso, boolean salir) {
        crearTimer(aviso, salir);
    }

    /**
     * Método que crea el Timer que se encargará de mostrar el mensaje.
     *
     * @param aviso El texto que se mostrará en el mensaje.
     * @param salir Un flag que indica si queremos salir del programa después de
     * mostrar el mensaje.
     */
    private void crearTimer(String aviso, boolean salir) {
        Timer timer = new Timer();

        /*
        Esta tarea se encarga de crear el Dialogo donde se visualizará el 
        mensaje y lo colocará en el centro de la pantalla con fondo amarillo 
        y el texto en rojo.
         */
        TimerTask mostrarAviso = new TimerTask() {
            @Override
            public void run() {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                int ancho = (int) screenSize.getWidth();
                int alto = (int) screenSize.getHeight();
                dialogo = new JDialog();
                dialogo.setUndecorated(true);
                dialogo.setModalityType(Dialog.ModalityType.MODELESS);
                JTextField tf = new JTextField(aviso);
                tf.setEditable(false);
                Font fuente = new Font("Arial", Font.BOLD, 16);
                tf.setFont(fuente);
                tf.setForeground(Color.RED);
                tf.setBackground(Color.YELLOW);
                FontMetrics fm = tf.getFontMetrics(tf.getFont());
                int anchoTexto = fm.stringWidth(tf.getText());
                tf.setPreferredSize
                    (new Dimension(anchoTexto + 10, fm.getHeight() + 10));
                dialogo.add(tf);
                dialogo.pack();
                dialogo.setLocation
                    (ancho / 2 - anchoTexto, alto / 2 - fm.getHeight());
                dialogo.setVisible(true);
            }
        };

        /*
        Esta tarea se encarga de eliminar el Dialogo.
         */
        TimerTask esperar = new TimerTask() {
            @Override
            public void run() {
                dialogo.dispose();
            }
        };

        /*
        Esta tarea se encarga de salir del programa. Lo hacemos aquí porque 
        si lo hiciesemos en el programa desde el que llamamos al Toast 
        no da tiempo a visualizar el mensaje antes de que la máquina virtual 
        cierre el programa.
         */
        TimerTask salirPrograma = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };

        /*
        Programamos las tres tareas que creamos antes.
         */
        timer.schedule(mostrarAviso, 500);
        timer.schedule(esperar, 4000);
        /*
        Esta se ejecuta sólo en el caso de que queramos salir del programa.
         */
        if (salir) {
            timer.schedule(salirPrograma, 2000);
        }
    }
}
