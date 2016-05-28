/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
            ArrayList<String> relacions = controladorPresentacio.consultar_relacio(taulaPaper.getSelectedRow(), false);        
            for(int i = 0; i < relacions.size(); ++i){
                String relacio = relacions.get(i);
                int posicioTab1 = relacio.indexOf('|');
                int posicioTab2 = relacio.indexOf('|',posicioTab1+1);
                String id = relacio.substring(0, posicioTab1);
                String nom = relacio.substring(posicioTab1+1, posicioTab2);
                String tipusNode = relacio.substring(posicioTab2+1);
                table_model.addRow(new Object [] {id, nom, tipusNode});
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

        jScrollPane1 = new javax.swing.JScrollPane();
        taulaPaper = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taulaDades = new javax.swing.JTable();
        EnrereB = new javax.swing.JButton();
        AfegirB = new javax.swing.JButton();
        bBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taulaPaper.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(taulaPaper);

        jLabel1.setText("Nodes amb els cuals no hi ha relació:");

        jLabel2.setText("Taula PAPER:");

        taulaDades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(taulaDades);

        EnrereB.setText("Enrere");
        EnrereB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrereBActionPerformed(evt);
            }
        });

        AfegirB.setText("Afegir relació");
        AfegirB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AfegirBActionPerformed(evt);
            }
        });

        bBuscar.setText("Cerca");
        bBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                        .addComponent(bBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EnrereB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AfegirB)
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(bBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EnrereB)
                    .addComponent(AfegirB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
          
    private void EnrereBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrereBActionPerformed
        GestionGraf menu = new GestionGraf(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_EnrereBActionPerformed

    private void AfegirBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AfegirBActionPerformed
        if (taulaPaper.getSelectedRow() != 1 && taulaDades.getSelectedRow() != -1) {
            int idOrigen = taulaPaper.getSelectedRow();
            int idDesti = taulaDades.getSelectedRow();
            ArrayList<String> relacions = controladorPresentacio.consultar_relacio(taulaPaper.getSelectedRow(), false); 
            String relacio = relacions.get(idDesti);
            int posicioTab1 = relacio.indexOf('|');
            int posicioTab2 = relacio.indexOf('|',posicioTab1+1);
            String id = relacio.substring(0, posicioTab1);
            String tipusNodeDesti = relacio.substring(posicioTab2+1);
            controladorPresentacio.afegir_relacio(idOrigen, Integer.parseInt(id), tipusNodeDesti);
            inicialitzar_taula_desti();   
            JOptionPane.showMessageDialog(this, "S'ha afegit la relació"); 
        }        
    }//GEN-LAST:event_AfegirBActionPerformed

    private void bBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarActionPerformed
        // TODO add your handling code here:
        inicialitzar_taula_desti();
    }//GEN-LAST:event_bBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AfegirB;
    private javax.swing.JButton EnrereB;
    private javax.swing.JButton bBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable taulaDades;
    private javax.swing.JTable taulaPaper;
    // End of variables declaration//GEN-END:variables
}
