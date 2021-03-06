
package expert.finder.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NEW
 */
public class ConsultarResultados extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;
    
    public ConsultarResultados(ControladorPresentacio controladorPresentacio) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        inicializar_taula_descripcio_resultats();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableResultat = new javax.swing.JTable();
        tornar = new javax.swing.JButton();
        veure = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Consultar Resultados");

        TableResultat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripción Resultado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableResultat);

        tornar.setText("Tornar");
        tornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tornarActionPerformed(evt);
            }
        });

        veure.setText("Veure");
        veure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veureActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tornar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(veure)
                        .addGap(43, 43, 43))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tornar)
                    .addComponent(veure))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tornarActionPerformed
        // TODO add your handling code here:
        GuiGestionConsulta menu = new GuiGestionConsulta(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarActionPerformed

    private void veureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veureActionPerformed
        // TODO add your handling code here:
        int i = TableResultat.getSelectedRow();
        if(i == -1) JOptionPane.showMessageDialog(this, "No s´ha seleccionat cap resultat.");
        else{
            GuiResultat menu = new GuiResultat(this.controladorPresentacio,i,false);
            menu.setVisible(true);
            this.dispose();
        }
        
    }//GEN-LAST:event_veureActionPerformed

    
    public void inicializar_taula_descripcio_resultats(){
        String column_names[]= {"Descripción Resultados"};
        DefaultTableModel table_model=new DefaultTableModel(column_names, 0);
        ArrayList<String> consultas = this.controladorPresentacio.get_consultes();
        for(int i = 0; i < this.controladorPresentacio.get_nombre_consultes(); ++i){
            String cons = consultas.get(i);
            String descripcio = cons.substring(cons.indexOf('|')+1, cons.indexOf("|", cons.indexOf('|')+1));
            table_model.addRow(new Object [] {descripcio}); 
        }
        
        TableResultat.setModel(table_model);
    }
    
    
    
    
    
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableResultat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton tornar;
    private javax.swing.JButton veure;
    // End of variables declaration//GEN-END:variables
}
