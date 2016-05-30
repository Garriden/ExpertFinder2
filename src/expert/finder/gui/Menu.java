/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import javax.swing.*;
import java.io.IOException;

/**
 *
 * @author marc.garrido.casas
 */
public class Menu extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;

    public Menu(ControladorPresentacio controladorPresentacio) {
        initComponents();
        if (!controladorPresentacio.graf_inicialitzat()) {
            BotonImpExpConsultes.setEnabled(false);
            BotonGestioCamins.setEnabled(false);
            BotonGestioConsultes.setEnabled(false);
            BotonGestioGraf.setEnabled(false);
            BotonImpExpCami.setEnabled(false);
        }
        else {
            BotonImpExpConsultes.setEnabled(true);
            BotonGestioCamins.setEnabled(true);
            BotonGestioConsultes.setEnabled(true);
            BotonGestioGraf.setEnabled(true);
            BotonImpExpCami.setEnabled(true);
        }
        this.controladorPresentacio = controladorPresentacio;
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

        BotonImpExpGraf = new javax.swing.JButton();
        BotonImpExpCami = new javax.swing.JButton();
        BotonImpExpConsultes = new javax.swing.JButton();
        BotonGestioGraf = new javax.swing.JButton();
        BotonGestioCamins = new javax.swing.JButton();
        BotonGestioConsultes = new javax.swing.JButton();
        BotonSortir = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonImpExpGraf.setText("IMP/EXP Graf");
        BotonImpExpGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImpExpGrafActionPerformed(evt);
            }
        });

        BotonImpExpCami.setText("IMP/EXP Cami");
        BotonImpExpCami.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImpExpCamiActionPerformed(evt);
            }
        });

        BotonImpExpConsultes.setText("IMP Consultes");
        BotonImpExpConsultes.setToolTipText("");
        BotonImpExpConsultes.setActionCommand("");
        BotonImpExpConsultes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImpExpConsultesActionPerformed(evt);
            }
        });

        BotonGestioGraf.setText("Gestio Graf");
        BotonGestioGraf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGestioGrafActionPerformed(evt);
            }
        });

        BotonGestioCamins.setText("Gestio Camins");
        BotonGestioCamins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGestioCaminsActionPerformed(evt);
            }
        });

        BotonGestioConsultes.setText("Gestio Consultes");
        BotonGestioConsultes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGestioConsultesActionPerformed(evt);
            }
        });

        BotonSortir.setText("Sortir");
        BotonSortir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonSortirActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonImpExpGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonGestioGraf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonImpExpCami, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(BotonGestioCamins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BotonGestioConsultes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotonImpExpConsultes, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BotonSortir)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(112, 112, 112))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonImpExpCami, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(BotonImpExpGraf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BotonImpExpConsultes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonGestioCamins, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(BotonGestioConsultes, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(BotonGestioGraf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addComponent(BotonSortir)
                .addContainerGap())
        );

        BotonImpExpCami.getAccessibleContext().setAccessibleName("IMP/EXP Camí");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonImpExpGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonImpExpGrafActionPerformed
        // TODO add your handling code here:
        ImportarExportarGraf importar = new ImportarExportarGraf(this.controladorPresentacio);
        importar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonImpExpGrafActionPerformed

    private void BotonImpExpCamiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonImpExpCamiActionPerformed
        ImportarExportarCami c = new ImportarExportarCami(this.controladorPresentacio);
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonImpExpCamiActionPerformed

    private void BotonSortirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonSortirActionPerformed
        this.dispose();        
    }//GEN-LAST:event_BotonSortirActionPerformed

    private void BotonGestioCaminsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGestioCaminsActionPerformed
        GestioCami gc = new GestioCami(this.controladorPresentacio);
        gc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonGestioCaminsActionPerformed

    private void BotonImpExpConsultesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonImpExpConsultesActionPerformed
        // TODO add your handling code here:
        try {
            this.controladorPresentacio.importar_consultes();
            JOptionPane.showMessageDialog(this,
                    "S'han importat correctament les consultes");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error al exportar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_BotonImpExpConsultesActionPerformed

    private void BotonGestioGrafActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGestioGrafActionPerformed
        // TODO add your handling code here:
        GestionGraf gestionGrafo = new GestionGraf(this.controladorPresentacio);        
        gestionGrafo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonGestioGrafActionPerformed

    private void BotonGestioConsultesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonGestioConsultesActionPerformed
        // TODO add your handling code here:
        GuiGestionConsulta menu = new GuiGestionConsulta(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonGestioConsultesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonGestioCamins;
    private javax.swing.JButton BotonGestioConsultes;
    private javax.swing.JButton BotonGestioGraf;
    private javax.swing.JButton BotonImpExpCami;
    private javax.swing.JButton BotonImpExpConsultes;
    private javax.swing.JButton BotonImpExpGraf;
    private javax.swing.JButton BotonSortir;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
