import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color; // Import for Color
import javafx.geometry.Pos;  // Import for Pos

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private static final int SCALING_FACTOR = 2;

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
            }
        }
    }

    private VBox createControlPanelSection(String label, ArrayList<ImageView> imageViews, ArrayList<String> imagePaths) {
        VBox sectionBox = new VBox();
        TitledPane titledPane = new TitledPane();
        titledPane.setText(label);

        ToggleGroup toggleGroup = new ToggleGroup();
        VBox radioButtonBox = new VBox();
        radioButtonBox.setSpacing(10); // Add some spacing between buttons

        for (int i = 0; i < imagePaths.size(); i++) {
            String fileName = imagePaths.get(i);
            ImageView imageView = imageViews.get(i);

            // Create an ImageView to be used as a radio button icon
            Image image = new Image("file:Resources/TelecasterParts/" + fileName);
            if (image.isError()) {
                System.out.println("Error loading image: " + fileName);
            }

            ImageView iconImageView = new ImageView(image);
            iconImageView.setFitWidth(100);  // Increase size for the image
            iconImageView.setFitHeight(100); // Increase size for the image
            iconImageView.setPreserveRatio(true); // Maintain aspect ratio

            // Create a Circle to wrap the image
            Circle circle = new Circle(50);  // Increase the circle size
            circle.setFill(Color.WHITE);  // Set the circle background color
            circle.setStroke(Color.BLACK);  // Set a black stroke around the circle
            circle.setStrokeWidth(2);

            // Position the image inside the circle
            iconImageView.setClip(circle);  // Clip the image with the circle shape

            // Create a radio button with this image icon inside a circle
            RadioButton radioButton = new RadioButton();
            radioButton.setGraphic(iconImageView);  // Set the circle with image as graphic for the radio button

            // Add text to the button for reference
            Label labelText = new Label("Image " + (i + 1));  // Display text like "Image 1"
            radioButton.setText(labelText.getText()); // Add text to the radio button

            // Use HBox to arrange image and text horizontally
            HBox radioButtonContent = new HBox(10);  // Set spacing between image and text
            radioButtonContent.setAlignment(Pos.CENTER_LEFT); // Align image and text to the left
            radioButtonContent.getChildren().addAll(iconImageView, labelText); // Stack image and text horizontally
            radioButton.setGraphic(radioButtonContent);

            // Add ActionListener to toggle visibility of the images
            int index = i; // Keep track of the index for visibility control
            radioButton.setOnAction(e -> {
                for (int j = 0; j < imageViews.size(); j++) {
                    imageViews.get(j).setVisible(j == index);
                    System.out.println("Image " + j + " visibility: " + (j == index));
                }
            });

            // Make sure the radio buttons are visible and have some space between them
            radioButton.setVisible(true);  // Ensure radio button is visible
            radioButtonBox.getChildren().add(radioButton);
        }

        titledPane.setContent(radioButtonBox);
        titledPane.setCollapsible(true);
        titledPane.setExpanded(false); // Collapse by default
        sectionBox.getChildren().add(titledPane);

        return sectionBox;
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
                String fileName = file.getName().toLowerCase();
                if (fileName.contains("body")) {
                    categorizedImages.get("Body").add(file.getName());
                } else if (fileName.contains("pickguard")) {
                    categorizedImages.get("Pickguard").add(file.getName());
                } else if (fileName.contains("bridge")) {
                    categorizedImages.get("Bridge").add(file.getName());
                } else if (fileName.contains("pickup")) {
                    categorizedImages.get("Pickup").add(file.getName());
                }
            }
        }

        return categorizedImages;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
