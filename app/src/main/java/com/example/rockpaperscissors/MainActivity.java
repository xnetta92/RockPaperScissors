package com.example.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonRock;

    private Button buttonPaper;

    private Button buttonScissors;

    private TextView npcTextView;

    private ImageView npcImage;

    private TextView yourTextView;

    private ImageView yourImage;

    private TextView resultTextView;

    private int yourScore = 0;
    private int opponentScore = 0;

    public void init() {
        yourTextView = findViewById(R.id.yourTextView);
        yourImage = findViewById(R.id.yourImage);
        npcTextView = findViewById(R.id.npcTextView);
        npcImage = findViewById(R.id.npcImage);
        resultTextView = findViewById(R.id.resultTextView);
        buttonRock = findViewById(R.id.buttonRock);
        buttonPaper = findViewById(R.id.buttonPaper);
        buttonScissors = findViewById(R.id.buttonScissors);
        Random random = new Random();
        yourImage = findViewById(R.id.yourImage);
        npcImage = findViewById(R.id.npcImage);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        buttonRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourImage.setImageResource(R.drawable.rock);
                opponentRandomImage();
                playRound("rock");
            }
        });

        buttonPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourImage.setImageResource(R.drawable.paper);
                opponentRandomImage();
                playRound("paper");
            }
        });

        buttonScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourImage.setImageResource(R.drawable.scissors);
                opponentRandomImage();
                playRound("scissors");
            }
        });
    }

    public void opponentRandomImage() {
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        String npcChoice = choices[random.nextInt(choices.length)];
        int drawableId = getResources().getIdentifier(npcChoice, "drawable", getPackageName());
        npcImage.setImageResource(drawableId);
    }
    public void playRound(String playerChoice) {
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        String opponentChoice = choices[random.nextInt(choices.length)];

        updateChoiceImage(yourImage, playerChoice);
        updateChoiceImage(npcImage, opponentChoice);

        String result = determineWinner(playerChoice, opponentChoice);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        if (result.equals("You win!")) {
            yourScore++;
        } else if (result.equals("You lose!")) {
            opponentScore++;
        }

        updateResultText();

        if (yourScore == 3 || opponentScore == 3) {
            gameOverDialog();
        }
    }

   public void updateChoiceImage(ImageView imageView, String choice) {
        int drawableId = getResources().getIdentifier(choice, "drawable", getPackageName());
        imageView.setImageResource(drawableId);
    }

    public String determineWinner(String playerChoice, String opponentChoice) {
        if (playerChoice.equals(opponentChoice)) {
            return "It's a draw!";
        } else if (
                (playerChoice.equals("rock") && opponentChoice.equals("scissors")) ||
                        (playerChoice.equals("paper") && opponentChoice.equals("rock")) ||
                        (playerChoice.equals("scissors") && opponentChoice.equals("paper"))
        ) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }

    public void updateResultText() {
        resultTextView.setText("Result: your " +yourScore + " opponent " + opponentScore);
    }



    public void gameOverDialog() {
        String message = (yourScore == 3) ? "Congratulations! You win!" : "Sorry, you lose!";
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(message + "\nDo you want to play again?")
                .setPositiveButton("Yes", (dialog, which) -> resetGame())
                .setNegativeButton("No", (dialog, which) -> finish())
                .setCancelable(false)
                .create()
                .show();
            resetGame();
    }
    public void resetGame () {
        yourScore = 0;
        opponentScore = 0;
        updateResultText();
    }


}


