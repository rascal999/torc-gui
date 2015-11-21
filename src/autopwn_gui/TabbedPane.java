/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autopwn_gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class TabbedPane extends javax.swing.JFrame {

    /**
     * Creates new form TabbedPane
     */
    public TabbedPane() {
        initComponents();
        // Check if we're connected to autopwn instance
        CheckConnection();
        // Populate tool list
        PopulateTools();
        // Populate assessment list
        PopulateAssessments();
        // Populate tool jobs
        PopulateToolJobs();
        // Populate assessment jobs
        PopulateAssessmentJobs();
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

    public boolean PopulateAssessments() {
        String sURL = "http://127.0.0.1:5000/assessments"; //just a string
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
    
    public boolean PopulateToolJobs() {
        String sURL = "http://127.0.0.1:5000/tools/jobs"; //just a string
        String target_name, target, id, return_code;
        JsonArray tools;
        int k;

        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setUseCaches(false);
            request.connect();
            request.disconnect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            tools = rootobj.get("result").getAsJsonArray();
            System.out.println(tools);
            DefaultTableModel model = (DefaultTableModel) TableToolJobs.getModel();
            // Remove rows
            if (model.getRowCount() > 0) {
                for (int i = model.getRowCount() - 1; i > -1; i--) {
                    model.removeRow(i);
                }
            }

            for (int i = 0; i < tools.size(); i++) {
                JsonObject row = tools.get(i).getAsJsonObject();
                id = row.get("id").toString();
                target_name = row.get("target_name").toString();
                target = row.get("target").toString();
                return_code = row.get("return_code").toString();
                model.addRow(new Object[]{i});
                TableToolJobs.setValueAt(id, i, 0);
                TableToolJobs.setValueAt(target_name, i, 1);
                TableToolJobs.setValueAt(target, i, 2);
                TableToolJobs.setValueAt(return_code, i, 3);
            }
        } catch (Exception exc) {
            //test
            System.err.println("error" + exc);
        }

        return true;
    }
    
    public boolean PopulateAssessmentJobs() {
        String sURL = "http://127.0.0.1:5000/assessments/jobs"; //just a string
        String target_name, target, id, return_code;
        JsonArray tools;
        int k;

        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setUseCaches(false);
            request.connect();
            request.disconnect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            tools = rootobj.get("result").getAsJsonArray();
            System.out.println(tools);
            DefaultTableModel model = (DefaultTableModel) TableAssessmentJobs.getModel();
            // Remove rows
            if (model.getRowCount() > 0) {
                for (int i = model.getRowCount() - 1; i > -1; i--) {
                    model.removeRow(i);
                }
            }

            for (int i = 0; i < tools.size(); i++) {
                JsonObject row = tools.get(i).getAsJsonObject();
                id = row.get("id").toString();
                target_name = row.get("target_name").toString();
                target = row.get("target").toString();
                return_code = row.get("return_code").toString();
                model.addRow(new Object[]{i});
                TableAssessmentJobs.setValueAt(id, i, 0);
                TableAssessmentJobs.setValueAt(target_name, i, 1);
                TableAssessmentJobs.setValueAt(target, i, 2);
                TableAssessmentJobs.setValueAt(return_code, i, 3);
            }
        } catch (Exception exc) {
            //test
            System.err.println("error" + exc);
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

    public int SaveAssessmentJob() {
        String sURL = "http://127.0.0.1:5000/assessments/jobs"; //just a string
        int job_id = 0;
        
        // Connect to the URL using java's native library
        try {
            // TODO Fix
            String urlParameters = "assessment=" + 1 +
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
            conn.disconnect();
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
    
    public int SaveToolJob() {
        String sURL = "http://127.0.0.1:5000/tools/jobs"; //just a string
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
            conn.disconnect();
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

    public int RunAssessmentJob(int job_id) {
        String sURL = "http://127.0.0.1:5000/assessments/jobs/execute"; //just a string

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
            conn.disconnect();
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) conn.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
            job_id = rootobj.get("id").getAsInt();
            System.out.println("Job id: " + job_id);
        } catch (Exception exc) {
            //test
        }

        return 1;
    }

    public int RunToolJob(int job_id) {
        String sURL = "http://127.0.0.1:5000/tools/jobs/execute"; //just a string

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
            conn.disconnect();
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) conn.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
            job_id = rootobj.get("id").getAsInt();
            System.out.println("Job id: " + job_id);
        } catch (Exception exc) {
            //test
        }

        return 1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabbedAutopwn = new javax.swing.JTabbedPane();
        PanelNewJob = new javax.swing.JPanel();
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
        PanelJobs = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableToolJobs = new javax.swing.JTable();
        ButtonAssessmentExport = new javax.swing.JButton();
        ButtonRefreshTable = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableAssessmentJobs = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ButtonToolExport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textfieldTarget.setToolTipText("");

        jLabel2.setText("target_name");

        jLabel5.setText("target");

        jLabel6.setText("port_number");

        jLabel7.setText("password_file");

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

        jLabel12.setText("password");

        jLabel13.setText("user");

        jLabel14.setText("user_file");

        jLabel15.setText("protocol");

        jLabel16.setText("url");

        javax.swing.GroupLayout PanelNewJobLayout = new javax.swing.GroupLayout(PanelNewJob);
        PanelNewJob.setLayout(PanelNewJobLayout);
        PanelNewJobLayout.setHorizontalGroup(
            PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelNewJobLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonRun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelNewJobLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelConnectionStatus))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelNewJobLayout.createSequentialGroup()
                        .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textfieldUserFile)
                            .addComponent(textfieldUser)
                            .addComponent(textfieldPassword)
                            .addComponent(textfieldPasswordFile)
                            .addComponent(textfieldTargetName)
                            .addComponent(textfieldTarget)
                            .addComponent(comboboxTools, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(formattedtextfieldPortNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                            .addComponent(textfieldProtocol)
                            .addComponent(textfieldURL))))
                .addContainerGap())
        );
        PanelNewJobLayout.setVerticalGroup(
            PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelNewJobLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelConnectionStatus))
                .addGap(20, 20, 20)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textfieldTargetName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(formattedtextfieldPortNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textfieldPasswordFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(textfieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(textfieldUserFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(textfieldProtocol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanelNewJobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(textfieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRun)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        TabbedAutopwn.addTab("New", PanelNewJob);

        TableToolJobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Target", "Return Code"
            }
        ));
        jScrollPane1.setViewportView(TableToolJobs);

        ButtonAssessmentExport.setText("Export");
        ButtonAssessmentExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonAssessmentExportMouseClicked(evt);
            }
        });

        ButtonRefreshTable.setText("Refresh tables");
        ButtonRefreshTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonRefreshTableMouseClicked(evt);
            }
        });

        TableAssessmentJobs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Target", "Return Code"
            }
        ));
        jScrollPane2.setViewportView(TableAssessmentJobs);

        jLabel1.setText("Assessment Jobs");

        jLabel3.setText("Tool Jobs");

        ButtonToolExport.setText("Export");
        ButtonToolExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonToolExportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelJobsLayout = new javax.swing.GroupLayout(PanelJobs);
        PanelJobs.setLayout(PanelJobsLayout);
        PanelJobsLayout.setHorizontalGroup(
            PanelJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelJobsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelJobsLayout.createSequentialGroup()
                        .addGroup(PanelJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ButtonRefreshTable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonToolExport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                            .addComponent(ButtonAssessmentExport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(PanelJobsLayout.createSequentialGroup()
                        .addGroup(PanelJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        PanelJobsLayout.setVerticalGroup(
            PanelJobsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelJobsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonToolExport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonAssessmentExport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonRefreshTable)
                .addGap(21, 21, 21))
        );

        TabbedAutopwn.addTab("Jobs", PanelJobs);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedAutopwn)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabbedAutopwn)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRunMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonRunMouseClicked
        // Run job
        int job_id = SaveAssessmentJob();
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        RunAssessmentJob(job_id);
    }//GEN-LAST:event_buttonRunMouseClicked

    private void ButtonToolExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonToolExportMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        String job_id = TableToolJobs.getValueAt(TableToolJobs.getSelectedRow(), 0).toString();
        System.out.println(job_id);
        String sURL = "http://127.0.0.1:5000/tools/exports/" + job_id; //just a string
        String message;
        InputStream fileContent;
        byte[] buffer = new byte[4096];
        int n = -1;

        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            fileContent = request.getInputStream();

            // parent component of the dialog
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                OutputStream output = new FileOutputStream(fileToSave);
                while ((n = fileContent.read(buffer)) != -1)
                {
                    output.write(buffer, 0, n);
                }
                output.close();
            }
        } catch (Exception anexc) {
            // something
        }
    }//GEN-LAST:event_ButtonToolExportMouseClicked

    private void ButtonRefreshTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonRefreshTableMouseClicked
        // TODO add your handling code here:
        // Populate tool jobs
        PopulateToolJobs();
        // Populate assessment jobs
        PopulateAssessmentJobs();
    }//GEN-LAST:event_ButtonRefreshTableMouseClicked

    private void ButtonAssessmentExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonAssessmentExportMouseClicked
        // TODO add your handling code here:
        String job_id = TableAssessmentJobs.getValueAt(TableAssessmentJobs.getSelectedRow(), 0).toString();
        System.out.println(job_id);
        String sURL = "http://127.0.0.1:5000/assessments/exports/" + job_id; //just a string
        String message;
        InputStream fileContent;
        byte[] buffer = new byte[4096];
        int n = -1;

        // Connect to the URL using java's native library
        try {
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            fileContent = request.getInputStream();

            // parent component of the dialog
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                OutputStream output = new FileOutputStream(fileToSave);
                while ((n = fileContent.read(buffer)) != -1)
                {
                    output.write(buffer, 0, n);
                }
                output.close();
            }
        } catch (Exception anexc) {
            // something
        }
    }//GEN-LAST:event_ButtonAssessmentExportMouseClicked

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
            java.util.logging.Logger.getLogger(TabbedPane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TabbedPane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TabbedPane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TabbedPane.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TabbedPane().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonAssessmentExport;
    private javax.swing.JButton ButtonRefreshTable;
    private javax.swing.JButton ButtonToolExport;
    private javax.swing.JPanel PanelJobs;
    private javax.swing.JPanel PanelNewJob;
    private javax.swing.JTabbedPane TabbedAutopwn;
    private javax.swing.JTable TableAssessmentJobs;
    private javax.swing.JTable TableToolJobs;
    private javax.swing.JButton buttonRun;
    private javax.swing.JComboBox<String> comboboxTools;
    private javax.swing.JFormattedTextField formattedtextfieldPortNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
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
