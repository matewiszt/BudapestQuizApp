package com.example.android.budapestquizapp;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.android.budapestquizapp.R.string.answers;
import static com.example.android.budapestquizapp.R.string.question;
import static com.example.android.budapestquizapp.R.string.results;

/**
 * Created by MátéZoltán on 2017. 03. 25..
 */

public class QuestionActivity extends AppCompatActivity {

    //Variables for the layout of a question
    TextView titleTextView; //The "Question 1" heading
    ImageView imageView; //The image above the question
    TextView questionTextView; //The question
    LinearLayout answerContainer;//The container for the answers
    Button moreButton;//The New Question/Get the Results/Play Again Button

    //The answer RadioGroup and the RadioButtons
    RadioGroup answerGroup;
    RadioButton answer1Button; //The 1st answer Button
    RadioButton answer2Button; //The 2nd answer Button
    RadioButton answer3Button; //The 3rd answer Button
    RadioButton answer4Button; //The 4th answer Button

    //The answer checkbox group and the Checkboxes
    LinearLayout checkGroup;
    CheckBox check1Button; //The 1st answer Button
    CheckBox check2Button; //The 2nd answer Button
    CheckBox check3Button; //The 3rd answer Button
    CheckBox check4Button; //The 4th answer Button

    //The answer EditText
    EditText textAnswer;

    //The "serial" number of the question
    int questionNumber = 0;

    //The list of the questions
    ArrayList<Question> questionsArray = new ArrayList<Question>();

    //Counts the right answers of the user
    float counter = 0;

    //Variables for the form
    EditText name;//The EditText for the user's name
    EditText email;//The EditText for the user's e-mail
    CheckBox checkbox;//The CheckBox for accepting the terms
    LinearLayout sendLayout;//The Send Button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Get the title TextView
        titleTextView = (TextView) findViewById(R.id.titleTextView);

        //Get the ImageView
        imageView = (ImageView) findViewById(R.id.image);

        //Get the question TextView
        questionTextView = (TextView) findViewById(R.id.questionTextView);

        //Get the answer container
        answerContainer = (LinearLayout) findViewById(R.id.answerContainer);

        //Get the answer RadioButtons
        answerGroup = (RadioGroup) findViewById(R.id.radioGroup);
        answer1Button = (RadioButton) findViewById(R.id.answer1);
        answer2Button = (RadioButton) findViewById(R.id.answer2);
        answer3Button = (RadioButton) findViewById(R.id.answer3);
        answer4Button = (RadioButton) findViewById(R.id.answer4);

        //Get the answer CheckBoxes
        checkGroup = (LinearLayout) findViewById(R.id.checkGroup);
        check1Button = (CheckBox) findViewById(R.id.check1);
        check2Button = (CheckBox) findViewById(R.id.check2);
        check3Button = (CheckBox) findViewById(R.id.check3);
        check4Button = (CheckBox) findViewById(R.id.check4);

        //Get the answer EditText
        textAnswer = (EditText) findViewById(R.id.textAnswer);

        //Get the More Button
        moreButton = (Button) findViewById(R.id.moreButton);

        //Get the elements of the form
        sendLayout = (LinearLayout) findViewById(R.id.sendLayout);//The whole form
        name = (EditText) findViewById(R.id.name);//Name field
        email = (EditText) findViewById(R.id.email);//E-mail field
        checkbox = (CheckBox) findViewById(R.id.checkbox);//I agree with the terms Checkbox

        //Get the resources
        Resources res = getResources();

        //Get the questions and answers
        String[] allQuestions = res.getStringArray(R.array.question);
        String[] answers1Array = res.getStringArray(R.array.answer1);
        String[] answers2Array = res.getStringArray(R.array.answer2);
        String[] answers3Array = res.getStringArray(R.array.answer3);
        String[] answers4Array = res.getStringArray(R.array.answer4);
        String[] answers5Array = res.getStringArray(R.array.answer5);
        String[] answers6Array = res.getStringArray(R.array.answer6);
        String[] rightAnswers6Array = res.getStringArray(R.array.rightAnswers6);
        String rightAnswer7 = getString(R.string.rightAnswer7);

        //Create the Question objects with the question and answer strings
        questionsArray.add(new Question(Question.RADIO, allQuestions[0], answers1Array, R.drawable.bazilika, answers1Array[2]));
        questionsArray.add(new Question(Question.RADIO, allQuestions[1], answers2Array, R.drawable.hosoktere, answers2Array[1]));
        questionsArray.add(new Question(Question.RADIO, allQuestions[2], answers3Array, R.drawable.szechenyi, answers3Array[2]));
        questionsArray.add(new Question(Question.RADIO, allQuestions[3], answers4Array, R.drawable.lanchid, answers4Array[0]));
        questionsArray.add(new Question(Question.RADIO, allQuestions[4], answers5Array, R.drawable.opera, answers5Array[1]));
        questionsArray.add(new Question(Question.CHECK, allQuestions[5], answers6Array, R.drawable.halaszbastya, rightAnswers6Array));
        questionsArray.add(new Question(Question.TEXT, allQuestions[6], R.drawable.var, rightAnswer7));

        //Display the first question
        displayQuestion(questionNumber);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the question "serial number" and the count of the right answers
        outState.putInt("questionNumber", questionNumber);
        outState.putFloat("counter", counter);

        //Save which radio button is checked and the states of all checkboxes
        outState.putInt("checkedRadio", answerGroup.getCheckedRadioButtonId());
        outState.putBoolean("check1State", check1Button.isChecked());
        outState.putBoolean("check2State", check2Button.isChecked());
        outState.putBoolean("check3State", check3Button.isChecked());
        outState.putBoolean("check4State", check4Button.isChecked());

        //Save the content of EditTexts
        outState.putString("textAnswerContent", textAnswer.getText().toString());
        outState.putString("nameContent", name.getText().toString());
        outState.putString("emailContent", email.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Load the variables saved after screen rotation
        questionNumber = savedInstanceState.getInt("questionNumber");
        counter = savedInstanceState.getFloat("counter");
        int checkedRadio = savedInstanceState.getInt("checkedRadio");
        boolean check1State = savedInstanceState.getBoolean("check1State");
        boolean check2State = savedInstanceState.getBoolean("check2State");
        boolean check3State = savedInstanceState.getBoolean("check3State");
        boolean check4State = savedInstanceState.getBoolean("check4State");
        String textAnswerContent = savedInstanceState.getString("textAnswerContent");
        String nameContent = savedInstanceState.getString("nameContent");
        String emailContent = savedInstanceState.getString("emailContent");

        //If the previous question was not the last, display the actual question
        if (questionNumber < questionsArray.size()) {

            displayQuestion(questionNumber);

        }

        else {

            //If it was the last question get back the results
            getResults();

        }

        //Set the RadioButtons and Checkboxes to the saved states
        answerGroup.check(checkedRadio);
        check1Button.setChecked(check1State);
        check2Button.setChecked(check2State);
        check3Button.setChecked(check3State);
        check4Button.setChecked(check4State);

        //Set the text of the EditTexts to the saved texts
        textAnswer.setText(textAnswerContent);
        name.setText(nameContent);
        email.setText(emailContent);
    }

    /*
     * Displays the actual title, image, question and answers on the screen
     *
     * @param number: the "serial" number of the actual question
     */

    private void displayQuestion(int number) {

        //Display the actual question title
        String title = getString(question) + " " + (number + 1);
        titleTextView.setText(title);

        //Display the actual image
        int image = questionsArray.get(number).getImageResourceID();
        imageView.setImageResource(image);

        //Display the actual question
        String question = questionsArray.get(number).getQuestion();
        questionTextView.setText(question);

        //Get the type of the Question Object
        int type = questionsArray.get(number).getType();

        if (type == Question.RADIO) {

            //Clears the RadioButtons
            answerGroup.clearCheck();

            //If the question type is radio, hide the container of the checkgroup and EditText and display the RadioGroup
            answerGroup.setVisibility(View.VISIBLE);
            textAnswer.setVisibility(View.GONE);
            checkGroup.setVisibility(View.GONE);

            //Get the Array of answers and display it on the RadioButtons
            String[] actualAnswers = questionsArray.get(number).getAnswers();
            answer1Button.setText(actualAnswers[0]);
            answer2Button.setText(actualAnswers[1]);
            answer3Button.setText(actualAnswers[2]);
            answer4Button.setText(actualAnswers[3]);
        }

        else if (type == Question.CHECK) {

            //Uncheck all the Checkboxes
            check1Button.setChecked(false);
            check2Button.setChecked(false);
            check3Button.setChecked(false);
            check4Button.setChecked(false);

            //If the question type is checkbox, hide the RadioGroup and the EditText and display the checkgroup
            answerGroup.setVisibility(View.GONE);
            textAnswer.setVisibility(View.GONE);
            checkGroup.setVisibility(View.VISIBLE);

            //Get the Array of answers and display it on the CheckBoxes
            String[] actualAnswers = questionsArray.get(number).getAnswers();
            check1Button.setText(actualAnswers[0]);
            check2Button.setText(actualAnswers[1]);
            check3Button.setText(actualAnswers[2]);
            check4Button.setText(actualAnswers[3]);
        }


        else if (type == Question.TEXT) {

            //Clears the EditText
            textAnswer.getText().clear();

            //If the question type is EditText, hide the RadioGroup and the checkgroup and display the EditText
            answerGroup.setVisibility(View.GONE);
            checkGroup.setVisibility(View.GONE);
            textAnswer.setVisibility(View.VISIBLE);
        }


        //Check if the question is the last or not
        checkLast(questionNumber);
    }


    /*
     * Displays a new question on the screen
     */

    public void newQuestion(View view) {

        //Check the previous question before updating the screen
        checkAnswers(questionNumber);

        //Increments the question number by 1
        questionNumber++;

        //Displays the title, image, question and answers related to the question number
        displayQuestion(questionNumber);

    }

    /*
     * Checks the actual question and updates the value of the counter
     */

    private void checkAnswers(int number){

        //Get the actual Question Object
        Question actualQuestion = questionsArray.get(number);

        //Get the type of the Object
        int type = actualQuestion.getType();

        if (type == Question.RADIO) {

            //If the question type is radio, find the button that is checked
            RadioButton checkedRadio = (RadioButton) findViewById(answerGroup.getCheckedRadioButtonId());

            //Get the right answer of the Question Object
            String rightAnswer = actualQuestion.getRightAnswer();

            if (checkedRadio == null){

                //Warn the user if he has not marked an answer
                Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
            }

            else {
                if (checkedRadio.getText().toString().equals(rightAnswer)) {

                    //If the checked answer is the right one, increment the counter by 1
                    counter += 1;
                }
            }

        }

        else if (type == Question.CHECK) {

            //If the question type is checkbox, get the list of the checked answers
            ArrayList<String> checked = checkCheckbox();

            //Get the right answers of the Question Object
            String[] rightAnswers = actualQuestion.getRightAnswers();

            if (checked == null){

                //Warn the user if he has not marked an answer
                Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show();
            }

            else {

                //Check if the list of the checked answers contains the elements of the right answers
                for (String answer : rightAnswers) {

                    //If yes, increments the counter by 1
                    if (checked.contains(answer)) {
                        counter += 0.5;
                    }
                }

            }

        }

        else if (type == Question.TEXT) {

            //If the question type is EditText, get the text entered by the user
            String userAnswer = textAnswer.getText().toString();

            //Get the right answer of the Question Object
            String rightAnswer = actualQuestion.getRightAnswer();

            if (userAnswer.equals(rightAnswer)) {

                //If the answer of the user is the right one, increment the counter by 1
                counter += 1;
            }

        }
    }

     /*
     * Returns the list of the checked answers (for CheckBox answers)
     */

    private ArrayList<String> checkCheckbox(){

        ArrayList<String> checkedBoxes = new ArrayList<>();

        if (check1Button.isChecked()){

            checkedBoxes.add(check1Button.getText().toString());

        }
        if (check2Button.isChecked()){

            checkedBoxes.add(check2Button.getText().toString());

        }
        if (check3Button.isChecked()){

            checkedBoxes.add(check3Button.getText().toString());

        }
        if (check4Button.isChecked()){

            checkedBoxes.add(check4Button.getText().toString());

        }

        return checkedBoxes;
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
                    getResults();
                }
            });
        }
    }



    /*
     * Called if Get the results Button is tapped
     */

    public void getResults() {

        if (questionNumber < questionsArray.size()) {

            //Check the last question
            checkAnswers(questionNumber);

        }

        //Increment the question number to handle the screen rotation (if is not incremented, on rotation you got
        // back always the last question and not the results)
        questionNumber++;

        //Hide the answer area
        answerContainer.setVisibility(View.GONE);

        //Displays the "Your Results" title
        String title = getString(results);
        titleTextView.setText(title);

        //Displays the user's results and his points
        String resultMessage = resultMessage();
        questionTextView.setText(resultMessage);

        //Inform the user about his points in a Toast message
        String toastMessage = getString(R.string.youHave) + " " + counter + " " + getString(R.string.points);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

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
        counter = 0;

        //Initializes the welcome Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /*
     * Writes the result message
     */
    private String resultMessage() {

        String results = getString(answers) + "\n\n";

        //Loop through all the Question objects
        for (Question question : questionsArray){

            //Print the question
            results += question.getQuestion() + "\n\n";

            //If there is one answer, print the answer
            if (question.getRightAnswer() != null) {

                results += question.getRightAnswer() + "\n\n";

            }

            else {

                //If there are several answers, print all the answers separated by a comma
                for (String answer : question.getRightAnswers()){

                    results += answer + ", ";

                };

                results += "\n\n";
            }
        }

       return results;
    }

    /*
     * Sends the results to the email given by the user
     */

    public void sendResults() {

        String subject = getString(R.string.emailSub);
        String text = getString(R.string.dear) + " " + name.getText() + "\n\n" + getString(R.string.youHave) + " " + counter + " " + getString(R.string.points);

        //If the the terms are accepted
        if (checkbox.isChecked()) {

            //Create an e-mail intent with the results
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email.getText())); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {

            //Create a warning toast message
            Toast toast = Toast.makeText(this, getString(R.string.acceptTerms), Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
