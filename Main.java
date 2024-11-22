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

    public Main() {
        HashMap<String, ArrayList<String>> categorizedImages = loadImagesFromDirectory(
                "Resources/TelecasterParts"
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

        // Set offsets for positioning of images
        int xOffset = 200; // Initial horizontal position
        int yOffset = 50;  // Initial vertical position

        // Layer indices
        int bodyLayer = 0;        // Body at the bottom
        int pickguardLayer = 1;   // Pickguard above body
        int pickupLayer = 2;      // Pickups above pickguard and body
        int bridgeLayer = 3;      // Optional, for bridges (if needed)

        try {
            // Grouped image data (Body, Pickguard, etc.)
            ArrayList<ArrayList<String>> imageGroups = new ArrayList<>();
            imageGroups.add(bodyImages);       // Body
            imageGroups.add(pickguardImages);  // Pickguard
            imageGroups.add(bridgeImages);     // Bridge (if applicable)
            imageGroups.add(pickupImages);     // Pickups

            // Grouped label containers
            ArrayList<ArrayList<JLabel>> labelGroups = new ArrayList<>();
            labelGroups.add(bodyLabels);
            labelGroups.add(pickguardLabels);
            labelGroups.add(bridgeLabels);
            labelGroups.add(pickupLabels);

            for (int group = 0; group < imageGroups.size(); group++) {
                ArrayList<String> images = imageGroups.get(group);
                ArrayList<JLabel> labels = labelGroups.get(group);

                // Manually assign layers for each group
                int layer = 0; // Default layer value
                if (group == 0) {
                    layer = bodyLayer; // Body
                } else if (group == 1) {
                    layer = pickguardLayer; // Pickguard
                } else if (group == 2) {
                    layer = pickupLayer; // Pickup
                } else if (group == 3) {
                    layer = bridgeLayer; // Bridge
                }

                for (String imageName : images) {
                    String imagePath = "Resources/TelecasterParts/" + imageName;
                    File imgFile = new File(imagePath);

                    if (imgFile.exists()) {
                        try {
                            BufferedImage image = ImageIO.read(imgFile);
                            if (image != null) {
                                // Check if image has transparency (PNG with alpha channel)
                                if (image.getColorModel().hasAlpha()) {
                                    // Scale image while maintaining transparency
                                    int newWidth = image.getWidth() / SCALING_FACTOR;
                                    int newHeight = image.getHeight() / SCALING_FACTOR;
                                    Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                                    // Create label and set bounds (same xOffset, yOffset for stacking)
                                    JLabel label = new JLabel(imageIcon);
                                    label.setBounds(xOffset, yOffset, newWidth, newHeight);
                                    label.setOpaque(false); // Set label to not have background

                                    // Add label to layered pane with the layer parameter
                                    layeredPane.add(label, new Integer(layer));
                                    labels.add(label);

                                    // Debugging: Log successful addition
                                    System.out.println("Added image to layer " + layer + ": " + imageName);

                                } else {
                                    System.out.println("Image does not have transparency: " + imageName);
                                }
                            } else {
                                System.out.println("Failed to load image (not a valid image): " + imageName);
                            }
                        } catch (IOException e) {
                            System.out.println("Error loading image: " + imageName);
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("File not found: " + imagePath);
                    }
                }
            }

            // Revalidate and repaint the layered pane after all images have been added
            layeredPane.revalidate();
            layeredPane.repaint();

        } catch (Exception e) {
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

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory not found or invalid: " + directoryPath);
            return categorizedImages;
        }

        File[] files = dir.listFiles((d, name) -> {
            String lowerCaseName = name.toLowerCase();
            return lowerCaseName.endsWith(".png") || lowerCaseName.endsWith(".jpg");
        });

        if (files == null || files.length == 0) {
            System.out.println("No image files found in directory: " + directoryPath);
            return categorizedImages;
        }

        // Categorizing files based on their names
        for (File file : files) {
            String fileName = file.getName().toLowerCase(); // Case-insensitive matching
            if (fileName.contains("body")) {
                categorizedImages.get("Body").add(file.getName());
            } else if (fileName.contains("pickguard")) {
                categorizedImages.get("Pickguard").add(file.getName());
            } else if (fileName.contains("bridge")) {
                categorizedImages.get("Bridge").add(file.getName());
            } else if (fileName.contains("pickup")) {
                categorizedImages.get("Pickup").add(file.getName());
            } else {
                System.out.println("Uncategorized file: " + file.getName());
            }
        }

        return categorizedImages;
    }

    private JPanel createHorizontalControlPanelSection(String label, ArrayList<String> imageGroup, ArrayList<JLabel> imageLabels) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
        JButton headerButton = new JButton(label);
        sectionPanel.add(headerButton, BorderLayout.NORTH);

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < imageGroup.size(); i++) {
            JRadioButton radioButton = new JRadioButton(imageGroup.get(i));
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
