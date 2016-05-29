/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import javax.swing.JOptionPane;

/**
 *
 * @author marc.garrido.casas
 */
public class ModificarNodeAplicacio extends javax.swing.JFrame {    
      private final ControladorPresentacio controladorPresentacio;
      private int posicio;
      private String tipusNode;

    public ModificarNodeAplicacio(ControladorPresentacio controladorPresentacio, int posicio, String nom, String tipusNode) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        jTextField1.setText(nom);
        this.posicio = posicio;
        this.tipusNode = tipusNode;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        EnrereButton = new javax.swing.JButton();
        ModificarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Introduir nom nou:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        EnrereButton.setText("Enrere");
        EnrereButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrereButtonActionPerformed(evt);
            }
        });

        ModificarButton.setText("Modificar");
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
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(EnrereButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 332, Short.MAX_VALUE)
                                .addComponent(ModificarButton))
                            .addComponent(jTextField1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EnrereButton)
                    .addComponent(ModificarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void EnrereButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrereButtonActionPerformed
        ConsultarNode menu = new ConsultarNode(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_EnrereButtonActionPerformed

    private void ModificarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarButtonActionPerformed
        try {
            String nomNou = jTextField1.getText();
            GestioGraf menu = new GestioGraf(this.controladorPresentacio);
            boolean existeix = controladorPresentacio.existeix_node(nomNou,tipusNode);
            if (existeix) {
                 int opcio = JOptionPane.showConfirmDialog(
                     this,
                     "Aquest node està repetit, el vols afegir igualment?",
                     "Node repetit !!!",
                     JOptionPane.YES_NO_OPTION);
                 if (opcio == 0) {
                     controladorPresentacio.modificar_nom_node(this.posicio,nomNou,this.tipusNode); 
                     JOptionPane.showMessageDialog(this, "S'ha modificat el node");
                     menu.setVisible(true);
                     this.dispose(); 
                 }
                 else {
                     JOptionPane.showMessageDialog(this, "NO s'ha modificat el node");
                 } 
            }
            else {
                controladorPresentacio.modificar_nom_node(this.posicio,nomNou,this.tipusNode); 
                ConsultarNode m = new ConsultarNode(this.controladorPresentacio);
                JOptionPane.showMessageDialog(this, "S'ha modificat el node");
                m.setVisible(true);
                this.dispose();
            }
        }
        catch(IllegalArgumentException ex) {           
            JOptionPane.showMessageDialog(this, ex.getMessage());  
        }
    }//GEN-LAST:event_ModificarButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EnrereButton;
    private javax.swing.JButton ModificarButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables


}
