/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopwn_gui;

import java.net.*;
import java.io.*;
import com.google.gson.*;
import java.awt.Color;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author user
 */
public class NewJob extends javax.swing.JFrame {

    /**
     * Creates new form NewJob
     */
    public NewJob() {
        initComponents();
        // Check if we're connected to autopwn instance
        CheckConnection();
        // Populate tool list
        PopulateTools();
    }

    public boolean PopulateTools() {
        String sURL = "http://127.0.0.1:5000/tools"; //just a string
        JsonArray tools;

        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            tools = rootobj.get("result").getAsJsonArray();
            for (int i = 0; i < tools.size(); i++) {
                JsonObject row = tools.get(i).getAsJsonObject();
                comboboxTools.addItem(row.get("name").toString());
            }
        } catch (Exception exc) {
            //test
            System.err.println("error");
        }

        return true;
    }
    
    public boolean CheckConnection() {
        String sURL = "http://127.0.0.1:5000/ping"; //just a string
        String message;
        
        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
            message = rootobj.get("message").getAsString();
            if (message.equals("pong")) {
                labelConnectionStatus.setForeground(Color.green);
                labelConnectionStatus.setText("Connected!");
            }
        } catch (Exception exc) {
            //test
        }

        return true;
    }
    
    public int SaveJob() {
        String sURL = "http://127.0.0.1:5000/jobs"; //just a string
        int job_id = 0;
        
        // Connect to the URL using java's native library
        try {
            String urlParameters = "tool=" + comboboxTools.getSelectedIndex() +
                    "&target=" + textfieldTarget.getText() +
                    "&target_name=" + textfieldTargetName.getText() +
                    "&protocol=" + textfieldProtocol.getText() +
                    "&port_number=" + formattedtextfieldPortNumber.getText() +
                    "&user=" + textfieldUser.getText() +
                    "&password=" + textfieldPassword.getText() +
                    "&user_file=" + textfieldUserFile.getText() +
                    "&password_file=" + textfieldPasswordFile.getText();
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            URL url = new URL(sURL);

            HttpURLConnection conn= (HttpURLConnection) url.openConnection();  
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) conn.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
            job_id = rootobj.get("id").getAsInt();
            System.out.println("Job id: " + job_id);
        } catch (Exception exc) {
            //test
        }

        return job_id;
    }

    public boolean RunJob(int job_id) {
        String sURL = "http://127.0.0.1:5000/jobs/execute"; //just a string
        
        // Connect to the URL using java's native library
        try {
            String urlParameters = "id=" + job_id;
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            URL url = new URL(sURL);

            HttpURLConnection conn= (HttpURLConnection) url.openConnection();  
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }

            // Convert to a JSON object to print data
            /*JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) conn.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
            job_id = rootobj.get("id").getAsInt();
            System.out.println("Job id: " + job_id);*/
        } catch (Exception exc) {
            //test
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textfieldTargetName = new javax.swing.JTextField();
        textfieldTarget = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        textfieldPasswordFile = new javax.swing.JTextField();
        textfieldUserFile = new javax.swing.JTextField();
        textfieldProtocol = new javax.swing.JTextField();
        textfieldURL = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        comboboxTools = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        buttonRun = new javax.swing.JButton();
        labelConnectionStatus = new javax.swing.JLabel();
        formattedtextfieldPortNumber = new javax.swing.JFormattedTextField();
        textfieldUser = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        textfieldPassword = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textfieldTargetName.setText("jTextField1");

        textfieldTarget.setText("jTextField2");

        jLabel2.setText("target_name");

        jLabel5.setText("target");

        jLabel6.setText("port_number");

        jLabel7.setText("password_file");

        textfieldPasswordFile.setText("jTextField4");

        textfieldUserFile.setText("jTextField5");

        textfieldProtocol.setText("jTextField6");

        textfieldURL.setText("jTextField7");

        jLabel8.setText("Autopwn GUI v0.1.0");

        comboboxTools.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        jLabel9.setText("Tool / Assessment");

        buttonRun.setText("Run");
        buttonRun.setToolTipText("");
        buttonRun.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonRunMouseClicked(evt);
            }
        });

        labelConnectionStatus.setForeground(new java.awt.Color(204, 51, 0));
        labelConnectionStatus.setText("Not Connected!");
        labelConnectionStatus.setName("labelConnectionStatus"); // NOI18N

        formattedtextfieldPortNumber.setText("jFormattedTextField1");

        textfieldUser.setText("jTextField5");

        jLabel12.setText("password");

        textfieldPassword.setText("jTextField4");

        jLabel13.setText("user");

        jLabel14.setText("user_file");

        jLabel15.setText("protocol");

        jLabel16.setText("url");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelConnectionStatus))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9))
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textfieldUserFile)
                            .addComponent(textfieldUser)
                            .addComponent(textfieldPassword)
                            .addComponent(textfieldPasswordFile)
                            .addComponent(textfieldTargetName)
                            .addComponent(textfieldTarget)
                            .addComponent(comboboxTools, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(formattedtextfieldPortNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                            .addComponent(textfieldProtocol)
                            .addComponent(textfieldURL))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelConnectionStatus))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textfieldTargetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(textfieldTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(formattedtextfieldPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldPasswordFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(textfieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(textfieldUserFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(textfieldProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(textfieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRun)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunMouseClicked
        // Run job
        int job_id = SaveJob();
        RunJob(job_id);
    }//GEN-LAST:event_buttonRunMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJob().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRun;
    private javax.swing.JComboBox<String> comboboxTools;
    private javax.swing.JFormattedTextField formattedtextfieldPortNumber;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelConnectionStatus;
    private javax.swing.JTextField textfieldPassword;
    private javax.swing.JTextField textfieldPasswordFile;
    private javax.swing.JTextField textfieldProtocol;
    private javax.swing.JTextField textfieldTarget;
    private javax.swing.JTextField textfieldTargetName;
    private javax.swing.JTextField textfieldURL;
    private javax.swing.JTextField textfieldUser;
    private javax.swing.JTextField textfieldUserFile;
    // End of variables declaration//GEN-END:variables
}
