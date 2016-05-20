/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import javax.swing.JFrame;

/**
 *
 * @author adri
 */
public class GestioCami extends javax.swing.JFrame {
    private final ControladorPresentacio controladorpresentacio;

    /**
     * Creates new form GestioCami
     */
    public GestioCami( ControladorPresentacio controladorpresentacio) {
        initComponents();
        this.controladorpresentacio = controladorpresentacio;
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BotonAfegirCami = new javax.swing.JButton();
        BotonModificarCami = new javax.swing.JButton();
        BotonElimianrCami = new javax.swing.JButton();
        BotonConsultarCamins = new javax.swing.JButton();
        BotonEnrere = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonAfegirCami.setText("Afegir Cami");
        BotonAfegirCami.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAfegirCamiActionPerformed(evt);
            }
        });

        BotonModificarCami.setText("Modificar Cami");
        BotonModificarCami.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarCamiActionPerformed(evt);
            }
        });

        BotonElimianrCami.setText("Eliminar Cami");
        BotonElimianrCami.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonElimianrCamiActionPerformed(evt);
            }
        });

        BotonConsultarCamins.setText("Consultar Camins");
        BotonConsultarCamins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonConsultarCaminsActionPerformed(evt);
            }
        });

        BotonEnrere.setText("Enrere");
        BotonEnrere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnrereActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BotonEnrere)
                                .addGap(0, 387, Short.MAX_VALUE))
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addGap(102, 102, 102)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotonConsultarCamins, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(BotonAfegirCami, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotonModificarCami, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(BotonElimianrCami, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(67, 67, 67))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotonAfegirCami, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonModificarCami, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonConsultarCamins, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonElimianrCami, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(BotonEnrere)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonAfegirCamiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAfegirCamiActionPerformed
        AfegirCami afegirCami = new AfegirCami(this.controladorpresentacio);
        afegirCami.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonAfegirCamiActionPerformed

    private void BotonModificarCamiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarCamiActionPerformed
        ModificarCami nlt = new ModificarCami(controladorpresentacio);
        nlt.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonModificarCamiActionPerformed

    private void BotonEnrereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnrereActionPerformed
        // TODO add your handling code here:
        new Menu(controladorpresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonEnrereActionPerformed

    private void BotonElimianrCamiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonElimianrCamiActionPerformed
        // TODO add your handling code here:
        new EliminarCami(controladorpresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonElimianrCamiActionPerformed

    private void BotonConsultarCaminsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonConsultarCaminsActionPerformed
        // TODO add your handling code here:
        new ConsultarCami(controladorpresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonConsultarCaminsActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAfegirCami;
    private javax.swing.JButton BotonConsultarCamins;
    private javax.swing.JButton BotonElimianrCami;
    private javax.swing.JButton BotonEnrere;
    private javax.swing.JButton BotonModificarCami;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
