/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;


import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NEW
 */
public class ModificarResultat extends javax.swing.JFrame {

    private final ControladorPresentacio controladorPresentacio;
    private int numeroConsulta;
    private ArrayList<String> modificacio;
    boolean modificat = false;
    
    /**
     * Creates new form ModificarResultat
     */
    public ModificarResultat(ControladorPresentacio controladorPresentacio, int numeroConsulta) {
        
        this.numeroConsulta = numeroConsulta;
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        this.modificacio = this.controladorPresentacio.get_resultat(numeroConsulta);
        inicializar_taula_resultat();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TableResultat = new javax.swing.JTable();
        afegir = new javax.swing.JButton();
        tornar = new javax.swing.JButton();
        modificaCasella = new javax.swing.JButton();
        novaCasella = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Modificar Resultat");

        TableResultat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Node", "Grau Rellevancia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableResultat);

        afegir.setText("Afegir");
        afegir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afegirActionPerformed(evt);
            }
        });

        tornar.setText("Tornar");
        tornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tornarActionPerformed(evt);
            }
        });

        modificaCasella.setText("Modifica Casella");
        modificaCasella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificaCasellaActionPerformed(evt);
            }
        });

        novaCasella.setToolTipText("");
        novaCasella.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novaCasellaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(afegir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tornar)
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(novaCasella, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(modificaCasella)
                        .addGap(61, 61, 61))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modificaCasella)
                    .addComponent(novaCasella, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afegir)
                    .addComponent(tornar))
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tornarActionPerformed
        // TODO add your handling code here:
        GuiResultat menu = new GuiResultat(this.controladorPresentacio, numeroConsulta, false);
        menu.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_tornarActionPerformed

    private void afegirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afegirActionPerformed
        // TODO add your handling code here:
        ArrayList<String> resultat = new ArrayList<String> ();
        int mida = this.modificacio.size();
        for(int i = 0; i < mida; ++i){
            
            String linea = this.modificacio.get(i);
            System.out.println(linea);
            int primeraBarra = linea.indexOf("|");
            int segonaBarra = linea.indexOf("|", primeraBarra + 1);
            int terceraBarra = linea.indexOf("|", segonaBarra + 1);
            
            String id = linea.substring(0, primeraBarra);

            String tipus = linea.substring(segonaBarra+1, terceraBarra);
            
            String novalinea = id + "|" + TableResultat.getModel().getValueAt(i, 0).toString() 
                                + "|" + tipus + "|" + TableResultat.getModel().getValueAt(i, 1).toString();
            System.out.println(novalinea);

            resultat.add(novalinea);
            
            
        }
        this.controladorPresentacio.afegir_resultat_modificat(resultat,numeroConsulta);
        GuiResultat menu = new GuiResultat(this.controladorPresentacio, numeroConsulta, false);
        menu.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_afegirActionPerformed

    private void modificaCasellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificaCasellaActionPerformed
        // TODO add your handling code here:
        if(TableResultat.getSelectedRow() != -1 && novaCasella.getText() != ""){
            int i = TableResultat.getSelectedRow();
            int j = TableResultat.getSelectedColumn();
            String nou = novaCasella.getText();
            TableResultat.getModel().setValueAt(nou, i, j);
            novaCasella.setText("");
        }
        
    }//GEN-LAST:event_modificaCasellaActionPerformed

    private void novaCasellaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novaCasellaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_novaCasellaActionPerformed


    public void inicializar_taula_resultat(){

        String column_names[]= {"Nom Node","Grau Rellevancia"};
        DefaultTableModel table_model=new DefaultTableModel(column_names, 0);
        
        
        for(int j = 0; j < modificacio.size(); ++j){
            String nodeGrau = modificacio.get(j);
            String nomNode = nodeGrau.substring(nodeGrau.indexOf('|')+1, nodeGrau.indexOf("|", nodeGrau.indexOf('|')+1));
            String grau = nodeGrau.substring(nodeGrau.lastIndexOf('|')+1, nodeGrau.length());
            table_model.addRow(new Object [] {nomNode, grau}); 
        }
        
        TableResultat.setModel(table_model);
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableResultat;
    private javax.swing.JButton afegir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificaCasella;
    private javax.swing.JTextField novaCasella;
    private javax.swing.JButton tornar;
    // End of variables declaration//GEN-END:variables
}
