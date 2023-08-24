package com.example.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    int [][] matrix = {{5,5,5},{5,5,5},{5,5,5}};
    Boolean game_on = Boolean.valueOf("true");

    final int[] whose_turn = {1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builder = new AlertDialog.Builder(this);

        ImageButton reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    public void gameActivity(View view){

        view.setEnabled(false);

        TextView player_chance = findViewById(R.id.textView);
        TextView title = findViewById(R.id.textView2);


        if (whose_turn[0] == 1){
            change_turn(view, whose_turn[0]);
            Update_position(String.valueOf(view.getId()), whose_turn[0]);
            whose_turn[0]--;
            player_chance.setText(R.string.o_its_your_turn);
            player_chance.setTypeface(null, Typeface.BOLD);
            player_chance.setTextColor(getResources().getColor(R.color.blue));
            title.setTextColor(getResources().getColor(R.color.blue));
            check_game_status(whose_turn[0]+1);

        }
        else{
            check_game_status(whose_turn[0]);
            change_turn(view, whose_turn[0]);
            Update_position(String.valueOf(view.getId()), whose_turn[0]);
            whose_turn[0]++;
            player_chance.setText(R.string.x_its_your_turn);
            player_chance.setTypeface(null, Typeface.BOLD);
            player_chance.setTextColor(getResources().getColor(R.color.red));
            title.setTextColor(getResources().getColor(R.color.red));
            check_game_status(whose_turn[0]-1);
        }
    }

    public void change_turn(View v, int turn){
        Button button = (Button) v;
        if (turn == 1) {
        button.setText("X");
        button.setTextSize(30);
        button.setTypeface(null , Typeface.BOLD);
        button.setTextColor(getResources().getColor(R.color.red));
        }
        else{
            button.setText("0");
            button.setTextSize(30);
            button.setTypeface(null, Typeface.BOLD);
            button.setTextColor(getResources().getColor(R.color.blue));
        }
    }

    public void Update_position(String s, int turn){
       switch (s){
           case "2131231224":
               matrix [2][1] = turn;
               break;
           case "2131231225":
               matrix [2][0] = turn;
               break;
           case "2131231226":
               matrix [2][2] = turn;
               break;
           case "2131231227":
               matrix [1][1] = turn;
               break;
           case "2131231228":
               matrix [1][0] = turn;
               break;
           case "2131231229":
               matrix [1][2] = turn;
               break;
           case "2131231230":
               matrix [0][1] = turn;
               break;
           case "2131231231":
               matrix [0][0] = turn;
               break;
           case "2131231232":
               matrix [0][2] = turn;
               break;
           default:
               break;
       }
    }

    public void check_game_status(int winner){

        for (int i=0;i<3;i++) {
            if (matrix[i][0] == winner && matrix[i][1] == winner && matrix[i][2] == winner) {
                game_on = false;
            } else if (matrix[0][i] == winner && matrix[1][i] == winner && matrix[2][i] == winner) {
                game_on = false;
            }
        }
        if (matrix[0][0] == winner && matrix[1][1] == winner && matrix[2][2] == winner) {
            game_on = false;
        } else if (matrix[0][2] == winner && matrix[1][1] == winner && matrix[2][0] == winner) {
            game_on = false;
        }
        if (!game_on){
            display_winner(winner);
        }
    }

    public void display_winner(int winner){
        TextView player_chance = findViewById(R.id.textView);

        if (winner == 0) {
            builder.setTitle("O is the winner !!!");
        }
        else{
            builder.setTitle("X is the winner !!!");
        }
        builder.setMessage("Do you want to play again ?")
                .setCancelable(false)
                .setNegativeButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setPositiveButton("Quit", (dialog, id) -> {
                    //  Action for 'NO' Button
                    finish();
                });
        AlertDialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        dialog.setOnShowListener(dialogInterface -> {
            Button playButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            Button quitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            playButton.setTextColor(getResources().getColor(R.color.black)); // Change to your desired color
            quitButton.setTextColor(getResources().getColor(R.color.black));   // Change to your desired color
        });
        dialog.show();

    }
}