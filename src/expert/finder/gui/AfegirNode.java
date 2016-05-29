/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import javax.swing.JOptionPane;


/**
 *
 * @author Admin
 */
public class AfegirNode extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;

    public AfegirNode(ControladorPresentacio controladorPresentacio) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        Enrere = new javax.swing.JButton();
        Afegir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nom node:");

        jLabel3.setText("Tipus node:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTOR", "PAPER", "CONFERENCIA", "TERME" }));

        Enrere.setText("Enrere");
        Enrere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrereActionPerformed(evt);
            }
        });

        Afegir.setText("Afegir");
        Afegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AfegirActionPerformed(evt);
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
                        .addComponent(Enrere)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 258, Short.MAX_VALUE)
                        .addComponent(Afegir))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Enrere)
                    .addComponent(Afegir))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void EnrereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrereActionPerformed
        GestioGraf menu = new GestioGraf(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_EnrereActionPerformed

    private void AfegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AfegirActionPerformed
       String nom = jTextField1.getText();
       String tipusNode = jComboBox1.getSelectedItem().toString();
       try {
           GestioGraf menu = new GestioGraf(this.controladorPresentacio);
           boolean existeix = controladorPresentacio.existeix_node(nom,tipusNode);
           if (existeix) {
                int opcio = JOptionPane.showConfirmDialog(
                    this,
                    "Aquest node està repetit, el vols afegir igualment?",
                    "Node repetit !!!",
                    JOptionPane.YES_NO_OPTION);
                if (opcio == 0) {
                    controladorPresentacio.afegir_node(nom,tipusNode);
                    JOptionPane.showMessageDialog(this, "S'ha afegit el node");
                    menu.setVisible(true);
                    this.dispose(); 
                }
                else {
                    JOptionPane.showMessageDialog(this, "NO s'ha afegit el node");
                    menu.setVisible(true);
                    this.dispose(); 
                } 
           }
           else {
               controladorPresentacio.afegir_node(nom,tipusNode);
               JOptionPane.showMessageDialog(this, "S'ha afegit el node");
               menu.setVisible(true);
               this.dispose();
           }
       } catch(IllegalArgumentException ex) {           
            JOptionPane.showMessageDialog(this, ex.getMessage());  
       }
           
    }//GEN-LAST:event_AfegirActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Afegir;
    private javax.swing.JButton Enrere;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
   
}
