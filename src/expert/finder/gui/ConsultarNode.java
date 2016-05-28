/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Phenom
 */
public class ConsultarNode extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;

    /**
     * Creates new form ConsultarNode
     */
    public ConsultarNode(ControladorPresentacio controladorPresentacio) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        inicialitzar_taula();
    }

    private void inicialitzar_taula() {
        String column_names[]= {"Id","Nom"};
        DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
        String tipusNode = comboTipo.getSelectedItem().toString();
        ArrayList<String> nodes = controladorPresentacio.get_nodes(tipusNode);        
        for(int i = 0; i < nodes.size(); ++i){
            String node = nodes.get(i);
            String id = node.substring(0, node.indexOf('|'));
            String nom = node.substring(node.indexOf('|')+1, node.length());            
            table_model.addRow(new Object [] {id, nom});
        }
        
        jTable1.setModel(table_model);
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
        bEnrere = new javax.swing.JButton();
        comboTipo = new javax.swing.JComboBox<>();
        EliminarButton = new javax.swing.JButton();
        ModificarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        bEnrere.setText("Enrere");
        bEnrere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEnrereActionPerformed(evt);
            }
        });

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Paper", "Conferencia", "Terme", "Autor" }));
        comboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoActionPerformed(evt);
            }
        });

        EliminarButton.setText("Eliminar node");
        EliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarButtonActionPerformed(evt);
            }
        });

        ModificarButton.setText("Modificar node");
        ModificarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(comboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bEnrere)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(EliminarButton)
                        .addGap(40, 40, 40)
                        .addComponent(ModificarButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bEnrere)
                    .addComponent(EliminarButton)
                    .addComponent(ModificarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bEnrereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEnrereActionPerformed
        // TODO add your handling code here:
        GestionGraf gestioGraf = new GestionGraf(this.controladorPresentacio);
        gestioGraf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bEnrereActionPerformed

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoActionPerformed
        // TODO add your handling code here:
        inicialitzar_taula();
    }//GEN-LAST:event_comboTipoActionPerformed

    private void EliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarButtonActionPerformed
       int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            controladorPresentacio.eliminar_node(fila, comboTipo.getSelectedItem().toString());
            ((DefaultTableModel)jTable1.getModel()).removeRow(fila);            
            JOptionPane.showMessageDialog(this, "S'ha eliminat el node"); 
        }        
        
    }//GEN-LAST:event_EliminarButtonActionPerformed

    private void ModificarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarButtonActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            String tipusNode = comboTipo.getSelectedItem().toString();
            String nom = (jTable1.getModel().getValueAt(fila,1)).toString();
            
            ModificarNodeAplicacio menu = new ModificarNodeAplicacio(this.controladorPresentacio, fila, nom, tipusNode);
            menu.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_ModificarButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EliminarButton;
    private javax.swing.JButton ModificarButton;
    private javax.swing.JButton bEnrere;
    private javax.swing.JComboBox<String> comboTipo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
