import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JFrame {

    private static final int SCALING_FACTOR = 2; // 4 means 25% of original size

    private ArrayList<JLabel> bodyLabels = new ArrayList<>();
    private ArrayList<JLabel> pickguardLabels = new ArrayList<>();
    private ArrayList<JLabel> bridgeLabels = new ArrayList<>();
    private ArrayList<JLabel> pickupLabels = new ArrayList<>();
    private JLabel electronicsPlateLabel = null; // Store the electronics plate label here

    public Main() {
        String[] bodyImages = {
                "Blue Matte Finish Body.png", "Red Matte Finish Body.png", "White Matte Finish Body.png",
                "Green Matte Finish Body.png", "Daphne Blue Body.png", "Shell Pink Body.png",
                "Silver Body.png", "Quilted Maple Top.png"
        };
        String[] pickguardImages = {
                "Black Pearl Pickguard.png", "Black Pearloid Hybrid Pickguard.png", "Red Pearloid Pickguard.png",
                "White Pearloid Pickguard.png", "Purple Swirl Resin Pickguard.png", "White Hybrid Pickguard.png",
                "Red Tortoise Pickguard.png", "White Pickguard.png", "Blue Pearl Pickguard.png"
        };
        String[] bridgeImages = {
                "Bridge Humbucker.png", "Vintage Ashtray Bridge.png", "Modern Ashtray Bridge.png",
                "Fixed Bridge.png", "Modern Fixed Bridge.png"
        };
        String[] pickupImages = {
                "Neck Humbucker.png", "Bridge Humbucker.png"
        };

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
            String[][] imageGroups = {bodyImages, pickguardImages, bridgeImages, pickupImages};
            ArrayList<JLabel>[] labelGroups = new ArrayList[] {bodyLabels, pickguardLabels, bridgeLabels, pickupLabels};
            for (int group = 0; group < imageGroups.length; group++) {
                for (int i = 0; i < imageGroups[group].length; i++) {
                    String imageName = imageGroups[group][i];
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
                            labelGroups[group].add(label);
                            layer++;
                        } else {
                            System.out.println("Failed to load image (not a valid image): " + imageName);
                        }
                    } else {
                        System.out.println("File not found: " + imagePath);
                    }
                }
            }

            // Add Electronics Plate image here, always on top
            String electronicsPlateImage = "Electronics Plate.png";  // Add the correct path to the electronics plate image
            String electronicsPlatePath = "E:/JAVA PROJS 1st Sem/DSA/Case Study/src/Resources/TelecasterParts/" + electronicsPlateImage;
            File electronicsPlateFile = new File(electronicsPlatePath);

            if (electronicsPlateFile.exists()) {
                BufferedImage electronicsPlateImageBuffered = ImageIO.read(electronicsPlateFile);
                if (electronicsPlateImageBuffered != null) {
                    int originalWidth = electronicsPlateImageBuffered.getWidth();
                    int originalHeight = electronicsPlateImageBuffered.getHeight();

                    int newWidth = originalWidth / SCALING_FACTOR;
                    int newHeight = originalHeight / SCALING_FACTOR;

                    Image scaledImage = electronicsPlateImageBuffered.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

                    BufferedImage scaledBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledBufferedImage.createGraphics();
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();

                    ImageIcon electronicsPlateIcon = new ImageIcon(scaledBufferedImage);
                    electronicsPlateLabel = new JLabel(electronicsPlateIcon);
                    electronicsPlateLabel.setBounds(xOffset, yOffset, electronicsPlateIcon.getIconWidth(), electronicsPlateIcon.getIconHeight());

                    // Add it to the layered pane with a very high layer to ensure it is always on top
                    layeredPane.add(electronicsPlateLabel, new Integer(1000)); // Use a high value like 1000 to ensure it's on top
                    electronicsPlateLabel.setVisible(true); // Always visible
                    layer++;
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

    private JPanel createHorizontalControlPanelSection(String label, String[] imageGroup, ArrayList<JLabel> imageLabels) {
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
        for (int i = 0; i < imageGroup.length; i++) {
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
