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
 * @author Admin
 */
public class ConsultarRelacio extends javax.swing.JFrame {
    private final ControladorPresentacio controladorPresentacio;

    public ConsultarRelacio(ControladorPresentacio controladorPresentacio) {
        initComponents();
        this.controladorPresentacio = controladorPresentacio;        
        this.setLocationRelativeTo(null);
        inicialitzar_taula_paper();
    }
    
    private void inicialitzar_taula_desti() {
        if (taulaPaper.getSelectedRow() != -1) {
            String column_names[]= {"Id","Nom","Relacionat"};
            DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
            ArrayList<String> relacions = controladorPresentacio.consultar_relacio(taulaPaper.getSelectedRow(), true);        
            for(int i = 0; i < relacions.size(); ++i){
                String relacio = relacions.get(i);
                int posicioTab1 = relacio.indexOf('|');
                int posicioTab2 = relacio.indexOf('|',posicioTab1+1);
                String id = relacio.substring(0, posicioTab1);
                String nom = relacio.substring(posicioTab1+1, posicioTab2);
                String relacionat = relacio.substring(posicioTab2+1);
                table_model.addRow(new Object [] {id, nom, relacionat});
            }

            taulaDades.setModel(table_model);
        }
    }
    
    private void inicialitzar_taula_paper() {
        String column_names[]= {"Id","Nom"};
        DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
        String tipusNode = "Paper";
        ArrayList<String> nodes = controladorPresentacio.get_nodes(tipusNode);        
        for(int i = 0; i < nodes.size(); ++i){
            String node = nodes.get(i);
            String id = node.substring(0, node.indexOf('|'));
            String nom = node.substring(node.indexOf('|')+1, node.length());            
            table_model.addRow(new Object [] {id, nom});
        }
        
        taulaPaper.setModel(table_model);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EnrereButton = new javax.swing.JButton();
        EliminarButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taulaPaper = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taulaDades = new javax.swing.JTable();
        bCercar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EnrereButton.setText("Enrere");
        EnrereButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrereButtonActionPerformed(evt);
            }
        });

        EliminarButton.setText("Eliminar");
        EliminarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarButtonActionPerformed(evt);
            }
        });

        taulaPaper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(taulaPaper);

        jLabel1.setText("Taula PAPER:");

        jLabel2.setText("Tipus node destí:");

        taulaDades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(taulaDades);

        bCercar.setText("Cercar");
        bCercar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCercarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EnrereButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(EliminarButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 12, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bCercar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(bCercar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EnrereButton)
                    .addComponent(EliminarButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EliminarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarButtonActionPerformed
        if (taulaPaper.getSelectedRow() != 1 && taulaDades.getSelectedRow() != -1) {
            try {
                int idOrigen = taulaPaper.getSelectedRow();
                int idDesti = taulaDades.getSelectedRow();
                ArrayList<String> relacions = controladorPresentacio.consultar_relacio(taulaPaper.getSelectedRow(), true); 
                String relacio = relacions.get(idDesti);
                int posicioTab1 = relacio.indexOf('|');
                int posicioTab2 = relacio.indexOf('|',posicioTab1+1);
                String id = relacio.substring(0, posicioTab1);
                String tipusNodeDesti = relacio.substring(posicioTab2+1);
                controladorPresentacio.eliminar_relacio(idOrigen, Integer.parseInt(id), tipusNodeDesti); 
                inicialitzar_taula_desti();    
                JOptionPane.showMessageDialog(this, "S'ha eliminat la relació"); 
            } 
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());                
            }
        } 
    }//GEN-LAST:event_EliminarButtonActionPerformed

    private void EnrereButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrereButtonActionPerformed
        GestioGraf menu = new GestioGraf(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_EnrereButtonActionPerformed

    private void bCercarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCercarActionPerformed

        inicialitzar_taula_desti();
    }//GEN-LAST:event_bCercarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EliminarButton;
    private javax.swing.JButton EnrereButton;
    private javax.swing.JButton bCercar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable taulaDades;
    private javax.swing.JTable taulaPaper;
    // End of variables declaration//GEN-END:variables
}
