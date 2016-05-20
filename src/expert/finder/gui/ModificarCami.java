/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author adri
 */
public class ModificarCami extends javax.swing.JFrame {
    private final ControladorPresentacio controladorpresentacio;
    /**
     * Creates new form ModificarCami
     */
    public ModificarCami(ControladorPresentacio controladorpresentacio) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorpresentacio = controladorpresentacio;
        inicialitzar_taula();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        BotonEnrere = new javax.swing.JButton();
        BotonModificar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        BotonEnrere.setText("Enrere");
        BotonEnrere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnrereActionPerformed(evt);
            }
        });

        BotonModificar.setText("Modificar");
        BotonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Expert Finder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BotonEnrere)
                        .addGap(348, 348, 348)
                        .addComponent(BotonModificar)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BotonEnrere)
                    .addComponent(BotonModificar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarActionPerformed
        int fila = jTable1.getSelectedRow();
        if(fila != -1){
            try{
                String cami = jTable1.getModel().getValueAt(fila, 1).toString();
                String descripcio = jTable1.getModel().getValueAt(fila, 2).toString();                
                new ModificarCamiAplicacio(this.controladorpresentacio, cami, descripcio, fila).setVisible(true);
                this.dispose();
            }catch(ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_BotonModificarActionPerformed

    private void BotonEnrereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnrereActionPerformed
        // TODO add your handling code here:
        new GestioCami(controladorpresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonEnrereActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void inicialitzar_taula(){
        String column_names[]= {"Id","Cami","Descripcio", "Clausura"};
        DefaultTableModel table_model=new DefaultTableModel(column_names, 0);
        ArrayList<String> camins = controladorpresentacio.get_camins();
        
        for(int i = 0; i < camins.size(); ++i){
            String camiCodificat = camins.get(i);
            
            String cami = camiCodificat.substring(0, camiCodificat.indexOf('|'));
            String descripcio = camiCodificat.substring(camiCodificat.indexOf('|')+1, camiCodificat.length());
            
            table_model.addRow(new Object [] {i+1, cami, descripcio, "Si"});            
        }
        
        jTable1.setModel(table_model);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonEnrere;
    private javax.swing.JButton BotonModificar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}