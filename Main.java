import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    private static final int SCALING_FACTOR = 2;
    private static final double BUTTON_WIDTH = 200;  // Set a fixed width for all buttons

    private ArrayList<ImageView> bodyImages = new ArrayList<>();
    private ArrayList<ImageView> pickguardImages = new ArrayList<>();
    private ArrayList<ImageView> bridgeImages = new ArrayList<>();
    private ArrayList<ImageView> pickupImages = new ArrayList<>();
    private ArrayList<ImageView> neckWoodImages = new ArrayList<>();
    private ArrayList<ImageView> fretboardImages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        // Load categorized images from the directory
        HashMap<String, ArrayList<String>> categorizedImages = loadImagesFromDirectory("Resources/TelecasterParts");

        ArrayList<String> bodyImagePaths = categorizedImages.getOrDefault("Body", new ArrayList<>());
        ArrayList<String> pickguardImagePaths = categorizedImages.getOrDefault("Pickguard", new ArrayList<>());
        ArrayList<String> bridgeImagePaths = categorizedImages.getOrDefault("Bridge", new ArrayList<>());
        ArrayList<String> pickupImagePaths = categorizedImages.getOrDefault("Pickup", new ArrayList<>());

        // Load neck-related parts (NeckWood and Fretboard)
        HashMap<String, ArrayList<String>> categorizedNeckImages = loadImagesFromDirectory("Resources/NeckParts");
        ArrayList<String> neckWoodPaths = categorizedNeckImages.getOrDefault("NeckWood", new ArrayList<>());
        ArrayList<String> fretboardPaths = categorizedNeckImages.getOrDefault("Fretboard", new ArrayList<>());

        // Layout for the main window
        BorderPane root = new BorderPane();

        // Create a container for the left side (body part and control panel)
        VBox bodyContainer = new VBox(20); // Spacing between body image and control panel
        bodyContainer.setAlignment(Pos.CENTER);
        bodyContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);

        StackPane bodyImagePane = new StackPane(); // Holds the body images
        bodyImagePane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");
        bodyImagePane.setMinWidth(300);  // Allow resizing to minimum width
        bodyImagePane.setMaxWidth(Double.MAX_VALUE);  // Allow resizing to maximum width

        VBox bodyControlPanel = new VBox(10); // Control panel for body parts
        bodyControlPanel.setAlignment(Pos.TOP_CENTER); // Align control panel at the top
        bodyControlPanel.setMinWidth(300);
        bodyControlPanel.setMaxWidth(Double.MAX_VALUE);

        // Set up body section (image and control panel)
        bodyContainer.getChildren().addAll(bodyImagePane, bodyControlPanel);

        // Create a container for the neck part (image and control panel)
        VBox neckContainer = new VBox(); // Spacing between neck image and control panel
        neckContainer.setAlignment(Pos.CENTER);
        neckContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);

        StackPane neckImagePane = new StackPane(); // Holds the neck images
        neckImagePane.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");
        neckImagePane.setMinWidth(300);
        neckImagePane.setMaxWidth(Double.MAX_VALUE);

        VBox neckControlPanel = new VBox(); // Control panel for neck parts
        neckControlPanel.setAlignment(Pos.TOP_CENTER); // Align control panel at the top
        neckControlPanel.setMinWidth(300);
        neckControlPanel.setMaxWidth(Double.MAX_VALUE);

        // Set up neck section (image and control panel)
        neckContainer.getChildren().addAll(neckImagePane, neckControlPanel);

        // Create the main container (HBox) to place bodyContainer and neckContainer side by side
        HBox mainContainer = new HBox(); // 20px spacing between body and neck sections
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainContainer.setMaxWidth(Double.MAX_VALUE);  // Allow resizing to max width

        // Create a container for both control panels (bodyControlPanel and neckControlPanel)
        HBox controlPanelContainer = new HBox();
        controlPanelContainer.setAlignment(Pos.CENTER);
        controlPanelContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        controlPanelContainer.setMaxWidth(Double.MAX_VALUE);
        controlPanelContainer.setMinWidth(300); // Set a minimum width for the control panel container

        controlPanelContainer.getChildren().addAll(bodyControlPanel, neckControlPanel);

        // Create a container to hold both image panes side by side
        HBox imageContainer = new HBox(); // 20px spacing between body and neck image panes
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPrefWidth(Region.USE_COMPUTED_SIZE);
        imageContainer.setMaxWidth(Double.MAX_VALUE);
        imageContainer.getChildren().addAll(bodyImagePane, neckImagePane);

        // Add both the containers for images and control panels into the main container
        mainContainer.getChildren().addAll(imageContainer, controlPanelContainer);

        root.setCenter(mainContainer); // Set HBox as the center layout of the BorderPane

        // Populate body images and add them to the body image pane
        populateImages(bodyImagePaths, bodyImages, bodyImagePane);
        populateImages(pickguardImagePaths, pickguardImages, bodyImagePane);
        populateImages(bridgeImagePaths, bridgeImages, bodyImagePane);
        populateImages(pickupImagePaths, pickupImages, bodyImagePane);

        // Add control panel sections for body parts
        bodyControlPanel.getChildren().add(createBodyControlPanelSection("Body", bodyImages, bodyImagePaths));
        bodyControlPanel.getChildren().add(createBodyControlPanelSection("Pickguard", pickguardImages, pickguardImagePaths));
        bodyControlPanel.getChildren().add(createBodyControlPanelSection("Bridge", bridgeImages, bridgeImagePaths));
        bodyControlPanel.getChildren().add(createBodyControlPanelSection("Pickups", pickupImages, pickupImagePaths));

        // Populate neck images and add them to the neck image pane
        populateNeckImages(neckWoodPaths, neckWoodImages, neckImagePane);
        populateNeckImages(fretboardPaths, fretboardImages, neckImagePane);

        // Add control panel sections for neck parts
        neckControlPanel.getChildren().add(createNeckControlPanelSection("Neck Wood", neckWoodImages, neckWoodPaths));
        neckControlPanel.getChildren().add(createNeckControlPanelSection("Fretboard", fretboardImages, fretboardPaths));

        // Set up scene and display
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("style.css"); // Add CSS for styling
        primaryStage.setScene(scene);
        primaryStage.setTitle("Guitar Customizer");

        // Set the stage to maximized (windowed fullscreen)
        primaryStage.setMaximized(true);

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
    private void populateNeckImages(ArrayList<String> imagePaths, ArrayList<ImageView> imageViews, StackPane neckImagePane) {
        for (String imagePath : imagePaths) {
            File imgFile = new File("Resources/NeckParts/" + imagePath); // Correct path for neck parts
            if (imgFile.exists()) {
                Image image = new Image(imgFile.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(image.getWidth() / SCALING_FACTOR);
                imageView.setFitHeight(image.getHeight() / SCALING_FACTOR);
                imageView.setVisible(false); // Initially hidden
                imageViews.add(imageView);
                neckImagePane.getChildren().add(imageView);
            } else {
                System.out.println("Image not found: " + imagePath); // Log missing images
            }
        }
    }

    private VBox createBodyControlPanelSection(String label, ArrayList<ImageView> imageViews, ArrayList<String> imagePaths) {
        VBox sectionBox = new VBox();
        TitledPane titledPane = new TitledPane();
        titledPane.setText(label);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10); // Add some spacing between buttons

        // Create a ScrollPane for the VBox inside the TitledPane to make it scrollable
        ScrollPane scrollableButtonBox = new ScrollPane(buttonBox);
        scrollableButtonBox.setFitToHeight(true); // Ensure it fits the height of the control panel

        for (int i = 0; i < imagePaths.size(); i++) {
            String fileName = imagePaths.get(i);
            ImageView imageView = imageViews.get(i);

            String description = extractDescriptionFromFileName(fileName);

            Image image = new Image("file:Resources/TelecasterParts/" + fileName);
            if (image.isError()) {
                System.out.println("Error loading image: " + fileName);
            }

            double iconWidth = 80; // Default width for all sections
            double iconHeight = 80; // Default height for all sections

            ImageView iconImageView = new ImageView(image);
            iconImageView.setFitWidth(iconWidth);
            iconImageView.setFitHeight(iconHeight);
            iconImageView.setPreserveRatio(true);

            if (label.equals("Pickguard"))  {
                double zoomFactor = 2; // Define the zoom level
                double viewportWidth = image.getWidth() / zoomFactor; // Calculate the viewport width
                double viewportHeight = image.getHeight() / zoomFactor; // Calculate the viewport height
                double viewportX = (image.getWidth() - viewportWidth) / 2; // Center horizontally
                double viewportY = image.getHeight() - viewportHeight; // Focus on the bottom part

                // Apply a viewport to the ImageView
                iconImageView.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            }
            // Apply zoom for "Pickups" and "Bridge"
            if (label.equals("Pickups") || label.equals("Bridge"))  {
                double zoomFactor = 3; // Define the zoom level
                double viewportWidth = image.getWidth() / zoomFactor; // Calculate the viewport width
                double viewportHeight = image.getHeight() / zoomFactor; // Calculate the viewport height
                double viewportX = (image.getWidth() - viewportWidth) / 2; // Center horizontally
                double viewportY = image.getHeight() - viewportHeight; // Focus on the bottom part

                // Apply a viewport to the ImageView
                iconImageView.setViewport(new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight));
            }

            Button button = new Button(description);
            button.setGraphic(iconImageView);
            button.setContentDisplay(ContentDisplay.LEFT);

            button.setStyle("-fx-background-color: #ede5dc; -fx-font-size: 14px; -fx-alignment: center-left;");

            int index = i;
            button.setOnAction(e -> {
                for (int j = 0; j < imageViews.size(); j++) {
                    imageViews.get(j).setVisible(j == index);
                }
            });

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


    private VBox createNeckControlPanelSection(String label, ArrayList<ImageView> imageViews, ArrayList<String> imagePaths) {
        VBox sectionBox = new VBox();
        TitledPane titledPane = new TitledPane();
        titledPane.setText(label);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10); // Add some spacing between buttons

        // Create a ScrollPane for the VBox inside the TitledPane to make it scrollable
        ScrollPane scrollableButtonBox = new ScrollPane(buttonBox);
        scrollableButtonBox.setFitToHeight(true); // Ensure it fits the height of the control panel

        // Create buttons for each neck part and add them to the button box
        for (int i = 0; i < imagePaths.size(); i++) {
            String fileName = imagePaths.get(i);
            ImageView imageView = imageViews.get(i);

            String description = extractDescriptionFromFileName(fileName);

            Image image = new Image("file:Resources/NeckParts/" + fileName);
            if (image.isError()) {
                System.out.println("Error loading image: " + fileName);
            }

            double iconWidth = 80; // Default width for all sections
            double iconHeight = 80; // Default height for all sections

            ImageView iconImageView = new ImageView(image);
            iconImageView.setFitWidth(iconWidth);
            iconImageView.setFitHeight(iconHeight);
            iconImageView.setPreserveRatio(true);

            Button button = new Button(description);
            button.setGraphic(iconImageView);
            button.setContentDisplay(ContentDisplay.LEFT);

            button.setStyle("-fx-background-color: #ede5dc; -fx-font-size: 14px; -fx-alignment: center-left;");

            int index = i;
            button.setOnAction(e -> {
                for (int j = 0; j < imageViews.size(); j++) {
                    imageViews.get(j).setVisible(j == index);
                }
            });

            buttonBox.getChildren().add(button);
        }

        titledPane.setContent(scrollableButtonBox); // Set the ScrollPane as the content of the TitledPane
        titledPane.setCollapsible(true);

        // Make sure neck-related sections are expanded by default
        titledPane.setExpanded(true);

        sectionBox.getChildren().add(titledPane);

        return sectionBox;
    }

    // Method to extract description from file name (removes "Body - ", "Pickguard - ", etc.)
    private String extractDescriptionFromFileName(String fileName) {
        return fileName.replaceAll(".*? - ", "").replace(".png", "").replace(".jpg", "");
    }

    private HashMap<String, ArrayList<String>> loadImagesFromDirectory(String directoryPath) {
        HashMap<String, ArrayList<String>> categorizedImages = new HashMap<>();
        File dir = new File(directoryPath);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                String fileName = file.getName();
                if (fileName.endsWith(".png") || fileName.endsWith(".jpg")) {
                    String category = getCategoryFromFileName(fileName);
                    categorizedImages.computeIfAbsent(category, k -> new ArrayList<>()).add(fileName);
                }
            }
        }
        return categorizedImages;
    }

    private String getCategoryFromFileName(String fileName) {
        if (fileName.contains("Body")) return "Body";
        if (fileName.contains("Pickguard")) return "Pickguard";
        if (fileName.contains("Bridge")) return "Bridge";
        if (fileName.contains("Pickup")) return "Pickup";
        if (fileName.contains("Neck Wood")) return "NeckWood";
        if (fileName.contains("Fretboard")) return "Fretboard";
        return "Other";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
