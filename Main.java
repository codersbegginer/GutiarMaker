import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JFrame {

    private static final int SCALING_FACTOR = 2; // 4 means 25% of original size

    private ArrayList<JLabel> bodyLabels = new ArrayList<>();
    private ArrayList<JLabel> pickguardLabels = new ArrayList<>();
    private ArrayList<JLabel> bridgeLabels = new ArrayList<>();
    private ArrayList<JLabel> pickupLabels = new ArrayList<>();
    private JLabel electronicsPlateLabel = null; // Store the electronics plate label here

    public Main() {
        HashMap<String, ArrayList<String>> categorizedImages = loadImagesFromDirectory(
                "E:/JAVA PROJS 1st Sem/DSA/Case Study/src/Resources/TelecasterParts"
        );

        ArrayList<String> bodyImages = categorizedImages.getOrDefault("Body", new ArrayList<>());
        ArrayList<String> pickguardImages = categorizedImages.getOrDefault("Pickguard", new ArrayList<>());
        ArrayList<String> bridgeImages = categorizedImages.getOrDefault("Bridge", new ArrayList<>());
        ArrayList<String> pickupImages = categorizedImages.getOrDefault("Pickup", new ArrayList<>());

        setTitle("Stacked Images Display with Layer Toggle");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1920, 1080));
        add(layeredPane, BorderLayout.CENTER);

        int layer = 0;
        int xOffset = 200;
        int yOffset = 50;

        try {
            ArrayList<ArrayList<String>> imageGroups = new ArrayList<>();
            imageGroups.add(bodyImages);
            imageGroups.add(pickguardImages);
            imageGroups.add(bridgeImages);
            imageGroups.add(pickupImages);

            ArrayList<ArrayList<JLabel>> labelGroups = new ArrayList<>();
            labelGroups.add(bodyLabels);
            labelGroups.add(pickguardLabels);
            labelGroups.add(bridgeLabels);
            labelGroups.add(pickupLabels);

            for (int group = 0; group < imageGroups.size(); group++) {
                ArrayList<String> images = imageGroups.get(group);
                ArrayList<JLabel> labels = labelGroups.get(group);

                for (String imageName : images) {
                    String imagePath = "E:/JAVA PROJS 1st Sem/DSA/Case Study/src/Resources/TelecasterParts/" + imageName;
                    File imgFile = new File(imagePath);

                    if (imgFile.exists()) {
                        BufferedImage image = ImageIO.read(imgFile);
                        if (image != null) {
                            int originalWidth = image.getWidth();
                            int originalHeight = image.getHeight();

                            int newWidth = originalWidth / SCALING_FACTOR;
                            int newHeight = originalHeight / SCALING_FACTOR;

                            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                            BufferedImage scaledBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                            Graphics2D g2d = scaledBufferedImage.createGraphics();
                            g2d.drawImage(scaledImage, 0, 0, null);
                            g2d.dispose();

                            ImageIcon imageIcon = new ImageIcon(scaledBufferedImage);
                            JLabel label = new JLabel(imageIcon);

                            label.setBounds(xOffset, yOffset, imageIcon.getIconWidth(), imageIcon.getIconHeight());
                            layeredPane.add(label, new Integer(layer));
                            labels.add(label);
                            layer++;
                        } else {
                            System.out.println("Failed to load image (not a valid image): " + imageName);
                        }
                    } else {
                        System.out.println("File not found: " + imagePath);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        controlPanel.setPreferredSize(new Dimension(400, 1080));
        add(controlPanel, BorderLayout.EAST);

        controlPanel.add(createHorizontalControlPanelSection("Body", bodyImages, bodyLabels), gbc);
        controlPanel.add(createHorizontalControlPanelSection("Pickguard", pickguardImages, pickguardLabels), gbc);
        controlPanel.add(createHorizontalControlPanelSection("Bridge", bridgeImages, bridgeLabels), gbc);
        controlPanel.add(createHorizontalControlPanelSection("Pickups", pickupImages, pickupLabels), gbc);

        setPreferredSize(new Dimension(1920, 1080));
        pack();
    }

    private HashMap<String, ArrayList<String>> loadImagesFromDirectory(String directoryPath) {
        HashMap<String, ArrayList<String>> categorizedImages = new HashMap<>();
        categorizedImages.put("Body", new ArrayList<>());
        categorizedImages.put("Pickguard", new ArrayList<>());
        categorizedImages.put("Bridge", new ArrayList<>());
        categorizedImages.put("Pickup", new ArrayList<>());

        File dir = new File(directoryPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".png") || name.endsWith(".jpg"));
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (fileName.contains("Body")) {
                        categorizedImages.get("Body").add(fileName);
                    } else if (fileName.contains("Pickguard")) {
                        categorizedImages.get("Pickguard").add(fileName);
                    } else if (fileName.contains("Bridge")) {
                        categorizedImages.get("Bridge").add(fileName);
                    } else if (fileName.contains("Pickup")) {
                        categorizedImages.get("Pickup").add(fileName);
                    }
                }
            }
        }
        return categorizedImages;
    }

    private JPanel createHorizontalControlPanelSection(String label, ArrayList<String> imageGroup, ArrayList<JLabel> imageLabels) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));

        JButton headerButton = new JButton(label);
        headerButton.setFont(new Font("Arial", Font.BOLD, 16));
        headerButton.setBackground(new Color(255, 253, 208));
        headerButton.setOpaque(true);
        headerButton.setBorderPainted(false);
        headerButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension buttonSize = new Dimension(400, headerButton.getPreferredSize().height);
        headerButton.setPreferredSize(buttonSize);
        headerButton.setMaximumSize(buttonSize);

        sectionPanel.add(headerButton);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setPreferredSize(new Dimension(400, 100));
        radioPanel.setVisible(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < imageGroup.size(); i++) {
            JRadioButton radioButton = new JRadioButton();
            int index = i;
            radioButton.addActionListener(e -> {
                for (int j = 0; j < imageLabels.size(); j++) {
                    imageLabels.get(j).setVisible(j == index);
                }
            });

            radioPanel.add(radioButton);
            buttonGroup.add(radioButton);
        }

        sectionPanel.add(radioPanel);

        headerButton.addActionListener(e -> {
            boolean isVisible = radioPanel.isVisible();
            radioPanel.setVisible(!isVisible);
        });

        return sectionPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
