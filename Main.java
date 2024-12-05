import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private static final int SCALING_FACTOR = 2;
    private static final double BUTTON_WIDTH = 150;  // Set a fixed width for all buttons

    private ArrayList<ImageView> bodyImages = new ArrayList<>();
    private ArrayList<ImageView> pickguardImages = new ArrayList<>();
    private ArrayList<ImageView> bridgeImages = new ArrayList<>();
    private ArrayList<ImageView> pickupImages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Load categorized images from the directory
        HashMap<String, ArrayList<String>> categorizedImages = loadImagesFromDirectory("Resources/TelecasterParts");

        ArrayList<String> bodyImagePaths = categorizedImages.getOrDefault("Body", new ArrayList<>());
        ArrayList<String> pickguardImagePaths = categorizedImages.getOrDefault("Pickguard", new ArrayList<>());
        ArrayList<String> bridgeImagePaths = categorizedImages.getOrDefault("Bridge", new ArrayList<>());
        ArrayList<String> pickupImagePaths = categorizedImages.getOrDefault("Pickup", new ArrayList<>());

        // Layout for the main window
        BorderPane root = new BorderPane();

        // Create a container to center the images on the left side
        StackPane imagePane = new StackPane(); // Holds the images
        imagePane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;"); // Add border

        root.setCenter(imagePane);

        VBox controlPanel = new VBox();
        controlPanel.setPrefWidth(300);
        root.setRight(controlPanel);

        // Populate image arrays and add images to the pane
        populateImages(bodyImagePaths, bodyImages, imagePane);
        populateImages(pickguardImagePaths, pickguardImages, imagePane);
        populateImages(bridgeImagePaths, bridgeImages, imagePane);
        populateImages(pickupImagePaths, pickupImages, imagePane);

        // Add control panel sections
        controlPanel.getChildren().add(createControlPanelSection("Body", bodyImages, bodyImagePaths));
        controlPanel.getChildren().add(createControlPanelSection("Pickguard", pickguardImages, pickguardImagePaths));
        controlPanel.getChildren().add(createControlPanelSection("Bridge", bridgeImages, bridgeImagePaths));
        controlPanel.getChildren().add(createControlPanelSection("Pickups", pickupImages, pickupImagePaths));

        // Set up scene and display
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("style.css"); // Add CSS for styling
        primaryStage.setScene(scene);
        primaryStage.setTitle("Guitar Customizer");
        primaryStage.show();
    }


    private void populateImages(ArrayList<String> imagePaths, ArrayList<ImageView> imageViews, StackPane imagePane) {
        for (String imagePath : imagePaths) {
            File imgFile = new File("Resources/TelecasterParts/" + imagePath);
            if (imgFile.exists()) {
                Image image = new Image(imgFile.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(image.getWidth() / SCALING_FACTOR);
                imageView.setFitHeight(image.getHeight() / SCALING_FACTOR);
                imageView.setVisible(false); // Hide initially
                imageViews.add(imageView);
                imagePane.getChildren().add(imageView);
            } else {
                System.out.println("Image not found: " + imagePath); // Log missing images
            }
        }
    }

    private VBox createControlPanelSection(String label, ArrayList<ImageView> imageViews, ArrayList<String> imagePaths) {
        VBox sectionBox = new VBox();
        TitledPane titledPane = new TitledPane();
        titledPane.setText(label);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10); // Add some spacing between buttons

        // Create a ScrollPane for the VBox inside the TitledPane to make it scrollable
        ScrollPane scrollableButtonBox = new ScrollPane(buttonBox);
        scrollableButtonBox.setFitToHeight(true);  // Ensure it fits the height of the control panel

        for (int i = 0; i < imagePaths.size(); i++) {
            String fileName = imagePaths.get(i);
            ImageView imageView = imageViews.get(i);

            // Extract the description part from the file name (before ".png" or ".jpg")
            String description = extractDescriptionFromFileName(fileName);

            // Create an ImageView to be used as the button icon
            Image image = new Image("file:Resources/TelecasterParts/" + fileName);
            if (image.isError()) {
                System.out.println("Error loading image: " + fileName);
            }

            // If the section is "Bridge", increase the image size
            double iconWidth = label.equals("Bridge") ? 120 : 80; // Increase image width for "Bridge"
            double iconHeight = label.equals("Bridge") ? 120 : 80; // Increase image height for "Bridge"

            ImageView iconImageView = new ImageView(image);
            iconImageView.setFitWidth(iconWidth);  // Set size based on section
            iconImageView.setFitHeight(iconHeight); // Set size based on section
            iconImageView.setPreserveRatio(true); // Maintain aspect ratio

            // Create a button with this image icon beside the description text
            Button button = new Button(description);  // Button with description text
            button.setGraphic(iconImageView);  // Set the image as the graphic for the button
            button.setContentDisplay(ContentDisplay.LEFT); // Place the image on the left side of the text

            // Add ActionListener to handle the visibility of the images
            int index = i; // Keep track of the index for visibility control
            button.setOnAction(e -> {
                for (int j = 0; j < imageViews.size(); j++) {
                    imageViews.get(j).setVisible(j == index);
                }
            });

            // Style the button to have no background and uniform width
            button.setStyle("-fx-background-color: transparent; -fx-font-size: 14px; -fx-alignment: center-left;");

            // Add the button to the VBox
            buttonBox.getChildren().add(button);
        }

        titledPane.setContent(scrollableButtonBox); // Set the ScrollPane as the content of the TitledPane
        titledPane.setCollapsible(true);

        // Make sure "Body" and "Pickguard" sections are expanded by default
        if (label.equals("Body") || label.equals("Pickguard")) {
            titledPane.setExpanded(true);
        } else {
            titledPane.setExpanded(false); // Keep other sections collapsed by default
        }

        sectionBox.getChildren().add(titledPane);

        return sectionBox;
    }




    // Method to extract description from file name (removes "Body - ", "Pickguard - ", etc.)
    private String extractDescriptionFromFileName(String fileName) {
        String[] parts = fileName.split(" - ");  // Split the string by the " - " delimiter
        if (parts.length > 1) {
            return parts[1].substring(0, parts[1].lastIndexOf('.'));  // Return description part without extension
        }
        return fileName;  // In case the file name does not match the expected pattern
    }

    private HashMap<String, ArrayList<String>> loadImagesFromDirectory(String directoryPath) {
        HashMap<String, ArrayList<String>> categorizedImages = new HashMap<>();
        categorizedImages.put("Body", new ArrayList<>());
        categorizedImages.put("Pickguard", new ArrayList<>());
        categorizedImages.put("Bridge", new ArrayList<>());
        categorizedImages.put("Pickup", new ArrayList<>());

        File dir = new File(directoryPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory not found: " + directoryPath);
            return categorizedImages;
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

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

        return categorizedImages;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
