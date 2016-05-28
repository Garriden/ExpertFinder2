/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

/**
 *
 * @author adri
 */
public class AfegirConsulta extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;

    public AfegirConsulta(ControladorPresentacio controladorPresentacio) {
        initComponents();
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

        bConsultaTipoI = new javax.swing.JButton();
        bConsultaTipoII = new javax.swing.JButton();
        bConsultaTipoIII = new javax.swing.JButton();
        BotonEnrere = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bConsultaTipoI.setText("Afegir Consulta Tipus I");
        bConsultaTipoI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsultaTipoIActionPerformed(evt);
            }
        });

        bConsultaTipoII.setText("Afegir Consulta Tipus II");
        bConsultaTipoII.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsultaTipoIIActionPerformed(evt);
            }
        });

        bConsultaTipoIII.setText("Afegir Consulta Tipus III");
        bConsultaTipoIII.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsultaTipoIIIActionPerformed(evt);
            }
        });

        BotonEnrere.setText("Enrere");
        BotonEnrere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnrereActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BotonEnrere)
                    .addComponent(bConsultaTipoIII)
                    .addComponent(bConsultaTipoII)
                    .addComponent(bConsultaTipoI))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(bConsultaTipoI)
                .addGap(18, 18, 18)
                .addComponent(bConsultaTipoII)
                .addGap(18, 18, 18)
                .addComponent(bConsultaTipoIII)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(BotonEnrere)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonEnrereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnrereActionPerformed
        // TODO add your handling code here:
        new GestioConsulta(this.controladorPresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BotonEnrereActionPerformed

    private void bConsultaTipoIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsultaTipoIActionPerformed
        // TODO add your handling code here:
        new AfegirConsultaTipusI(this.controladorPresentacio).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bConsultaTipoIActionPerformed

    private void bConsultaTipoIIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsultaTipoIIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bConsultaTipoIIActionPerformed

    private void bConsultaTipoIIIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsultaTipoIIIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bConsultaTipoIIIActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonEnrere;
    private javax.swing.JButton bConsultaTipoI;
    private javax.swing.JButton bConsultaTipoII;
    private javax.swing.JButton bConsultaTipoIII;
    // End of variables declaration//GEN-END:variables
}