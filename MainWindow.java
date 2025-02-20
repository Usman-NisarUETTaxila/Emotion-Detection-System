import javax.swing.*;
import ai.onnxruntime.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    public static void main(String[] args) throws Exception {
    	
    	// Session for model 
    	OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession session = env.createSession("C:\\Users\\Nisar\\IdeaProjects\\DataAnalysis\\src\\main\\java\\org\\example\\E_2.onnx", new OrtSession.SessionOptions());
            	
    	
        // Create the main frame
        JFrame frame = new JFrame("Emotion Detection System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLayout(new BorderLayout());

        // Title Panel (Black background with white text and spacing)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JLabel titleLabel = new JLabel("Emotion Detection System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        titlePanel.add(titleLabel);

        frame.add(titlePanel, BorderLayout.NORTH);

        // Main Panel with Gradient Background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.DARK_GRAY, getWidth(), getHeight(), Color.LIGHT_GRAY);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Left Panel - Displayed Posts (Modern Styled Panel)
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(new Color(255, 69, 58)); // Vibrant Red
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel leftLabel = new JLabel("Displayed Posts", SwingConstants.CENTER);
        leftLabel.setFont(new Font("Rubik", Font.BOLD, 20));
        leftLabel.setForeground(Color.WHITE);
        leftPanel.add(leftLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Rubik", Font.PLAIN, 20));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        JScrollPane scrollPane = new JScrollPane(textArea);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(leftPanel);

        // Right Panel - Input Section (Modern Styled Panel)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(50, 205, 50)); // Bright Green
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel postLabel = new JLabel("Enter Post:");
        postLabel.setFont(new Font("Rubik", Font.BOLD, 18));
        postLabel.setForeground(Color.BLACK);
        rightPanel.add(postLabel, gbc);

        gbc.gridy++;
        JTextField postField = new JTextField();
        postField.setFont(new Font("Rubik", Font.PLAIN, 16));
        postField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        postField.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(postField, gbc);

        gbc.gridy++;
        JLabel nameLabel = new JLabel("Posted By:");
        nameLabel.setFont(new Font("Rubik", Font.BOLD, 18));
        nameLabel.setForeground(Color.BLACK);
        rightPanel.add(nameLabel, gbc);

        gbc.gridy++;
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Rubik", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        nameField.setPreferredSize(new Dimension(250, 35));
        rightPanel.add(nameField, gbc);

        gbc.gridy++;
        JButton submitButton = new JButton("Post");
        submitButton.setFont(new Font("Rubik", Font.BOLD, 18));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        submitButton.setPreferredSize(new Dimension(120, 40));

        // Hover Effect
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(Color.WHITE);
                submitButton.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(Color.BLACK);
                submitButton.setForeground(Color.WHITE);
            }
        });

        // ActionListener for Posting
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String postText = postField.getText();
                String postedBy = nameField.getText();
                
                // Model Output 
                String ml_output = "";
                
                try {
                	
                String modelInputName = session.getInputNames().iterator().next(); 

                
                Map<String, OnnxTensor> inputs = new HashMap<>();
                inputs.put(modelInputName, OnnxTensor.createTensor(env, new String[]{postText}));

               
                OrtSession.Result results = session.run(inputs);

                
                OnnxValue resultValue = results.get(0);
                Object rawOutput = resultValue.getValue();

                float prediction = -1;

                if (rawOutput instanceof float[][]) {
                    float[][] output = (float[][]) rawOutput;
                    prediction = output[0][0];
                } else if (rawOutput instanceof float[]) {
                    float[] output = (float[]) rawOutput;
                    prediction = output[0];
                } else if (rawOutput instanceof long[]) {
                    long[] output = (long[]) rawOutput;
                    prediction = output[0];
                } else {
                    System.out.println("Unexpected output type: " + rawOutput.getClass().getName());
                }

                if (prediction == 0)
                   ml_output = "Sad";
                else if(prediction == 1)
                   ml_output = "Happy";
                else if (prediction == 2)
                   ml_output = "in love";
                else if (prediction == 3)
                   ml_output = "Angry";
                else if (prediction == 4)
                   ml_output = "Scared";
                else if (prediction == 5)
                   ml_output = "Surprised";
                else if (prediction == -1)
                    ml_output = "Not Classified";
                
                } catch (Exception e1) {
                	e1.printStackTrace();
                }
                
                if (!postText.isEmpty() && !postedBy.isEmpty()) {
                    textArea.append("" + postedBy + " is: " + ml_output + "\n");
                    postField.setText("");  // Clear post input
                    nameField.setText("");  // Clear name input
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter both post and name!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rightPanel.add(submitButton, gbc);
        mainPanel.add(rightPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    
}
}
