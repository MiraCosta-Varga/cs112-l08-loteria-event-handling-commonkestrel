package cs112.lab08;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    //CONSTANTS

    //array of LoteriaCards to use for game:
    private static final LoteriaCard[] LOTERIA_CARDS = {
        new LoteriaCard("Las matematicas", "1.png", 1),
        new LoteriaCard("Las ciencias", "2.png", 2),
        new LoteriaCard("La Tecnología", "8.png", 8),
        new LoteriaCard("La ingeniería", "9.png", 9),
    };

    // INSTANCE VARIABLES
    ImageView cardImageView;
    Text messageLabel;
    Button drawCardButton;
    ProgressBar cardProgress;
    int cardIndex = -1;
    int[] remainingCards = new int[LOTERIA_CARDS.length];;
    Random rand = new Random();

    @Override
    public void start(Stage stage) throws IOException {
        for (int i = 0; i < LOTERIA_CARDS.length; i++) {
            remainingCards[i] = i;
        }

        VBox layout = new VBox();

        Label titleLabel = new Label("EChALE STEM Loteria");
        titleLabel.setStyle("-fx-font: 20 arial;");

        cardImageView = new ImageView(loadLogo());
        cardImageView.fitWidthProperty().bind(new ReadOnlyDoubleWrapper(275.0));
        cardImageView.setPreserveRatio(true);
        cardImageView.setSmooth(true);

        messageLabel = new Text("");
        messageLabel.setWrappingWidth(300.0);
        messageLabel.setTextAlignment(TextAlignment.CENTER);

        drawCardButton = new Button();
        drawCardButton.setText("Draw Card");
        drawCardButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 10px;");
        drawCardButton.setOnAction(e -> {
            cardIndex++;
            cardProgress.setProgress(((double) cardIndex + 1) / LOTERIA_CARDS.length);

            if (cardIndex >= LOTERIA_CARDS.length) {
                cardImageView.setImage(loadLogo());
                drawCardButton.setDisable(true);
                cardProgress.setStyle("-fx-accent: red");
                messageLabel.setText("GAME OVER. No more cards! Exit and run program again to reset ^_^");
            } else {
                int cardsLeft = LOTERIA_CARDS.length - cardIndex;
                int nextCard = rand.nextInt(cardsLeft);
                LoteriaCard currentCard = LOTERIA_CARDS[remainingCards[nextCard]];
                remainingCards[nextCard] = remainingCards[cardsLeft-1];

                messageLabel.setText(currentCard.getCardName());
                cardImageView.setImage(currentCard.getImage());
            }
        });

        cardProgress = new ProgressBar();
        cardProgress.setProgress(0.0);
        cardProgress.setPadding(new Insets(20.0));
        cardProgress.setMinWidth(200.0);

        layout.getChildren().addAll(titleLabel, cardImageView, messageLabel, drawCardButton, cardProgress);
        layout.setAlignment(Pos.BASELINE_CENTER);

        Scene scene = new Scene(layout, 350, 500);
        stage.setScene(scene);
        stage.setTitle("EChALE STEM");
        stage.show();
    }

    private Image loadLogo() {
        FileInputStream input = null;
		try {
			input = new FileInputStream("./src/main/resources/images/0.png");
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.err.println("ERROR: could not open file.");
			System.exit(0);
		}
		return new Image(input);
    }

    public static void main(String[] args) {
        launch(args);
    }
}