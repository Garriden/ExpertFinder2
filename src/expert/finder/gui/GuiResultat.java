/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expert.finder.gui;


import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NEW
 */
public class GuiResultat extends javax.swing.JFrame {

    /**
     * Creates new form GuiResultat
     */
    
    private final ControladorPresentacio controladorPresentacio;
    private int numeroConsulta;
    private boolean ultimaExecutada;
    
    // 0 <= i < resultat.size()
    public GuiResultat(ControladorPresentacio controladorPresentacio, int i, boolean ultimaExecutada) {
        this.numeroConsulta = i;
        this.ultimaExecutada = ultimaExecutada;
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        inicializar_text_descripcio();
        inicializar_taula_resultat();
    }
    
    
   /* public GuiResultat(ControladorPresentacio controladorPresentacio,int i, ArrayList<string> resultarModificat){
        this.numeroConsulta = i;
        this.ultimaExecutada = false;
        this.modificaResultat = true;
        initComponents();
        this.setLocationRelativeTo(null);
        this.controladorPresentacio = controladorPresentacio;
        inicializar_text_descripcio();
        inicializar_taula_resultat();
    } */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableResultat = new javax.swing.JTable();
        tornar = new javax.swing.JButton();
        consultaUmbral = new javax.swing.JButton();
        TextDescripcio = new javax.swing.JTextField();
        guardar = new javax.swing.JButton();
        modificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Resultado");

        jLabel2.setText("Descripción consulta");

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

        tornar.setText("Tornar");
        tornar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tornarActionPerformed(evt);
            }
        });

        consultaUmbral.setText("Consulta Umbral");
        consultaUmbral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                consultaUmbralActionPerformed(evt);
            }
        });

        guardar.setText("Guardar Resultat");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        modificar.setText("Modificar Resultat");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tornar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(guardar)
                                .addGap(39, 39, 39)
                                .addComponent(consultaUmbral))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(TextDescripcio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(modificar)
                        .addGap(36, 36, 36))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(TextDescripcio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(modificar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(consultaUmbral)
                    .addComponent(tornar)
                    .addComponent(guardar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tornarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tornarActionPerformed
        // TODO add your handling code here:
        if(ultimaExecutada) this.controladorPresentacio.eliminar_ultima_consulta_executada();
        GuiGestionConsulta menu = new GuiGestionConsulta(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_tornarActionPerformed

    private void consultaUmbralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consultaUmbralActionPerformed
        // TODO add your handling code here:
        ArrayList<String> consultes = this.controladorPresentacio.get_consultes();
        String tipo = consultes.get(this.numeroConsulta);
        String tipus = tipo.substring(0, tipo.indexOf('|'));
        System.out.println(tipus);
        if("Tipo I".equals(tipus)){
            System.out.println("entra");
            String grau = TableResultat.getModel().getValueAt(TableResultat.getSelectedRow(), 1).toString();
            System.out.println(grau);
            double grauR = Double.parseDouble(grau);
            System.out.print(grauR);
            NuevaConsulta menu = new NuevaConsulta(this.controladorPresentacio, this.numeroConsulta, grauR);
            menu.setVisible(true);
            this.dispose();
        }
        //else no se puede hacer de otro tipo
    }//GEN-LAST:event_consultaUmbralActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
        GuiGestionConsulta menu = new GuiGestionConsulta(this.controladorPresentacio);
        menu.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_guardarActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO add your handling code here:
        ModificarResultat menu = new ModificarResultat(this.controladorPresentacio, numeroConsulta);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_modificarActionPerformed

    public void inicializar_text_descripcio(){
        ArrayList<String> consultes = this.controladorPresentacio.get_consultes();
        String descripcio = consultes.get(numeroConsulta);
        descripcio = descripcio.substring(descripcio.indexOf('|')+1, descripcio.indexOf("|", descripcio.indexOf('|')+1));
        TextDescripcio.setText(descripcio);
    }
    
    
    public void inicializar_taula_resultat(){
        ArrayList<String> resultat = this.controladorPresentacio.get_resultat(numeroConsulta);
        String column_names[]= {"Nom Node","Grau Rellevancia"};
        DefaultTableModel table_model=new DefaultTableModel(column_names, 0);
        
        
        for(int j = 0; j < resultat.size(); ++j){
            String nodeGrau = resultat.get(j);
            String nomNode = nodeGrau.substring(nodeGrau.indexOf('|')+1, nodeGrau.indexOf("|", nodeGrau.indexOf('|')+1));
            String grau = nodeGrau.substring(nodeGrau.lastIndexOf('|')+1, nodeGrau.length());
            table_model.addRow(new Object [] {nomNode, grau}); 
        }
        
        TableResultat.setModel(table_model);
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableResultat;
    private javax.swing.JTextField TextDescripcio;
    private javax.swing.JButton consultaUmbral;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificar;
    private javax.swing.JButton tornar;
    // End of variables declaration//GEN-END:variables
}
