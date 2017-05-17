package com.example.android.budapestquizapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.color.white;
import static com.example.android.budapestquizapp.R.string.question;
import static com.example.android.budapestquizapp.R.string.results;

/**
 * Created by MátéZoltán on 2017. 03. 25..
 */

public class QuestionActivity extends AppCompatActivity {
    TextView titleTextView; //The "Question 1" heading
    ImageView imageView; //The image above the question
    TextView questionTextView; //The question
    Button answer1Button; //The 1st answer Button
    Button answer2Button; //The 2nd answer Button
    Button answer3Button; //The 3rd answer Button
    Button answer4Button; //The 4th answer Button
    int questionNumber = 0; //The "serial" number of the question
    ArrayList<Integer> images = new ArrayList<>(); //The ArrayList of the images
    ArrayList<String> questions = new ArrayList<>(); //The ArrayList of the questions
    ArrayList<String> answers1 = new ArrayList<>(); //The ArrayList of the answers to question 1
    ArrayList<String> answers2 = new ArrayList<>(); //The ArrayList of the answers to question 2
    ArrayList<String> answers3 = new ArrayList<>(); //The ArrayList of the answers to question 3
    ArrayList<String> answers4 = new ArrayList<>(); //The ArrayList of the answers to question 4
    ArrayList<String> answers5 = new ArrayList<>(); //The ArrayList of the answers to question 5
    ArrayList<String> answers6 = new ArrayList<>(); //The ArrayList of the answers to question 6
    ArrayList<String> answers7 = new ArrayList<>(); //The ArrayList of the answers to question 7
    ArrayList<ArrayList<String>> answers = new ArrayList<>(); //The ArrayList of the answers Arraylists
    ArrayList<String> userAnswers = new ArrayList<>(); //The ArrayList of the answers of the users
    ArrayList<String> rightAnswers = new ArrayList<>(); //The ArrayList of the right answers
    TableLayout table;//The TableLayout of the answer Buttons
    Boolean marked = false;//True if at least one answer is checked, default:false
    Button moreButton;//The New Question/Get the Results/Play Again Button
    LinearLayout sendLayout;//The Send Button
    EditText name;//The EditText for the user's name
    EditText email;//The EditText for the user's e-mail
    CheckBox checkbox;//The CheckBox for accepting the terms
    RadioButton radio1;//The Radio Button that lets the user give his name
    RadioButton radio2;//The Radio Button that does not let the user give his name
    int counter = 0;//Counts the right answers of the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        //Get the right Views
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        imageView = (ImageView) findViewById(R.id.image);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        answer1Button = (Button) findViewById(R.id.answer1);
        answer2Button = (Button) findViewById(R.id.answer2);
        answer3Button = (Button) findViewById(R.id.answer3);
        answer4Button = (Button) findViewById(R.id.answer4);
        table = (TableLayout) findViewById(R.id.table);
        moreButton = (Button) findViewById(R.id.moreButton);
        sendLayout = (LinearLayout) findViewById(R.id.sendLayout);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        //Add the images to the ArrayList of images
        images.add(R.drawable.bazilika);
        images.add(R.drawable.hosoktere);
        images.add(R.drawable.opera);
        images.add(R.drawable.szechenyi);
        images.add(R.drawable.var);
        images.add(R.drawable.lanchid);
        images.add(R.drawable.halaszbastya);
        //Add the questions to the ArrayList of questions
        questions.addAll(Arrays.asList(getResources().getStringArray(R.array.question)));
        //Add the answers to ArrayLists
        answers1.addAll(Arrays.asList(getResources().getStringArray(R.array.answer1)));
        answers2.addAll(Arrays.asList(getResources().getStringArray(R.array.answer2)));
        answers3.addAll(Arrays.asList(getResources().getStringArray(R.array.answer3)));
        answers4.addAll(Arrays.asList(getResources().getStringArray(R.array.answer4)));
        answers5.addAll(Arrays.asList(getResources().getStringArray(R.array.answer5)));
        answers6.addAll(Arrays.asList(getResources().getStringArray(R.array.answer6)));
        answers7.addAll(Arrays.asList(getResources().getStringArray(R.array.answer7)));
        //Add the answer ArrayLists to one ArrayList
        answers.add(answers1);
        answers.add(answers2);
        answers.add(answers3);
        answers.add(answers4);
        answers.add(answers5);
        answers.add(answers6);
        answers.add(answers7);
        //Add the right answers to ArrayList
        rightAnswers.add(getResources().getStringArray(R.array.answer1)[0]);
        rightAnswers.add(getResources().getStringArray(R.array.answer2)[2]);
        rightAnswers.add(getResources().getStringArray(R.array.answer3)[1]);
        rightAnswers.add(getResources().getStringArray(R.array.answer4)[2]);
        rightAnswers.add(getResources().getStringArray(R.array.answer5)[3]);
        rightAnswers.add(getResources().getStringArray(R.array.answer6)[0]);
        rightAnswers.add(getResources().getStringArray(R.array.answer7)[1]);
        //Display the first question
        displayQuestion(questionNumber);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("questionNumber", questionNumber);
        outState.putBoolean("marked", marked);
        outState.putInt("counter", counter);
        outState.putStringArrayList("userAnswers", userAnswers);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        questionNumber = savedInstanceState.getInt("questionNumber");
        marked = savedInstanceState.getBoolean("marked");
        counter = savedInstanceState.getInt("counter");
        userAnswers = savedInstanceState.getStringArrayList("userAnswers");
        displayQuestion(questionNumber);
    }

    /*
     * Displays the actual title, image, question and answers on the screen
     *
     * @param number: the "serial" number of the actual question
     */
    private void displayQuestion(int number) {
        //Displays the actual question title
        String title = getString(question) + " " + (number + 1);
        titleTextView.setText(title);
        //Displays the actual image
        int image = images.get(number);
        imageView.setImageResource(image);
        //Displays the actual question
        String question = questions.get(number);
        questionTextView.setText(question);
        questionTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        //Displays the actual answers on the Buttons
        List actualAnswers = answers.get(number);
        String answer1 = actualAnswers.get(0).toString();
        String answer2 = actualAnswers.get(1).toString();
        String answer3 = actualAnswers.get(2).toString();
        String answer4 = actualAnswers.get(3).toString();
        answer1Button.setText(answer1);
        answer2Button.setText(answer2);
        answer3Button.setText(answer3);
        answer4Button.setText(answer4);
        //Special rules for the last question
        checkLast(questionNumber);
    }

    /*
     * Displays a new question on the screen
     */
    public void newQuestion(View view) {
        //Checks if at least one answer is marked, if not, displays a Toast
        if (!marked) {
            Toast toast = Toast.makeText(this, getString(R.string.toast), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            //Increments the question number by 1
            questionNumber++;
            //Before changing to a new question, it defaults the marked signal
            marked = false;
            //Displays the title, image, question and answers related to the question number
            displayQuestion(questionNumber);
            //Unmarks all the answers
            unmarkAnswer(answer1Button);
            unmarkAnswer(answer2Button);
            unmarkAnswer(answer3Button);
            unmarkAnswer(answer4Button);
        }
    }
    /*
     * Checks if the question is the last or not
     *
     * @param number: the number which should be checked
     */

    private void checkLast(int number) {
        if (number == 6) {
            //On the last question screen changes the text of the New Question button and the function called when tapping it
            moreButton.setText(getString(R.string.getResults));
            moreButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    getResults(v);
                }
            });
        }
    }

    /*
     * If a Button is tapped, changes its status to marked
     *
     * @param button: the Button which is tapped
     */
    private void markAnswer(Button button) {
        //Changes the style of the Button to marked
        button.setBackgroundResource(R.drawable.marked_button);
        button.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        //Adds the answer to the userAnswers ArrayList
        String userAnswer = button.getText().toString();
        userAnswers.add(questionNumber, userAnswer);//Handles if the user taps several times
        //True that there is at least one marked answer
        marked = true;
    }

    /*
     * If a Button is tapped, changes its style to normal
     *
     * @param button: the Button which is tapped
     */
    private void unmarkAnswer(Button button) {
        button.setBackgroundResource(R.drawable.button);
        button.setTextColor(ContextCompat.getColor(this, white));
    }
    /*
     * If answer 1 Button is tapped, changes its status to marked and the status of the others to unmarked
     */

    public void tapAnswer1(View view) {
        markAnswer(answer1Button);
        unmarkAnswer(answer2Button);
        unmarkAnswer(answer3Button);
        unmarkAnswer(answer4Button);
    }

    /*
     * If answer 2 Button is tapped, changes its status to marked and the status of the others to unmarked
     */

    public void tapAnswer2(View view) {
        unmarkAnswer(answer1Button);
        markAnswer(answer2Button);
        unmarkAnswer(answer3Button);
        unmarkAnswer(answer4Button);
    }
    /*
     * If answer 3 Button is tapped, changes its status to marked and the status of the others to unmarked
     */

    public void tapAnswer3(View view) {
        unmarkAnswer(answer1Button);
        unmarkAnswer(answer2Button);
        markAnswer(answer3Button);
        unmarkAnswer(answer4Button);
    }

    /*
     * If answer 4 Button is tapped, changes its status to marked and the status of the others to unmarked
     */
    public void tapAnswer4(View view) {
        unmarkAnswer(answer1Button);
        unmarkAnswer(answer2Button);
        unmarkAnswer(answer3Button);
        markAnswer(answer4Button);
    }

    /*
     * Called if Get the results Button is tapped
     */

    public void getResults(View view) {
        //Displays the "Your Results" title
        String title = getString(results);
        titleTextView.setText(title);
        //Displays the user's results and his points
        String resultMessage = resultMessage(view);
        questionTextView.setText(resultMessage);
        //Hides the answer Buttons
        table.setVisibility(View.GONE);
        //Changes the More Button to "Play Again" Button
        moreButton.setText(getString(R.string.playAgain));
        //Calls the resetAll method if the "Play Again" Button is tapped
        moreButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                resetAll(v);
            }
        });
        sendLayout.setVisibility(View.VISIBLE);
    }

    /*
     * Resets the game
     */

    public void resetAll(View v) {
        //It clears the user answers ArrayList
        userAnswers.clear();
        Activity context = this;
        //Initializes the welcome Activity
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
    /*
     * Counts the points of the user
     *
     * @param points: the starting points
     */

    private int countPoints(int points) {
        for (int i = 0; i < userAnswers.size(); i++) {
            if (userAnswers.get(i).equals(rightAnswers.get(i))) {
                points += 1;
            }

        }
        return points;
    }

    /*
     * Writes the result message
     */
    private String resultMessage(View view) {
        String results = getString(R.string.answers) + "\n\n";
        for (int i = 0; i < userAnswers.size(); i++) {
            results += userAnswers.get(i);
            if (!userAnswers.get(i).equals(rightAnswers.get(i))) {
                results += ", " + getString(R.string.wrongAnswerText) + " " + rightAnswers.get(i);
            } else {
                results += " " + getString(R.string.rightAnswerText);
            }
            results += "\n\n";
        }
        results += getString(R.string.youHave) + " " + countPoints(counter) + " " + getString(R.string.points);
        return results;
    }

    /*
     * Sends the results to the email given by the user
     */

    public void sendResults(View view) {
        String subject = getString(R.string.emailSub);
        String text = getString(R.string.dear) + " " + name.getText() + "\n\n" + resultMessage(view);
        if (checkbox.isChecked()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email.getText())); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.acceptTerms), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /*
     * Hides or shows the Name EditText based on the radio buttons' state
     */

    public void checkRadio(View view) {
        if (radio2.isChecked()) {
            name.setVisibility(View.VISIBLE);
        } else if (radio1.isChecked()) {
            name.setVisibility(View.GONE);
        }
    }

}
