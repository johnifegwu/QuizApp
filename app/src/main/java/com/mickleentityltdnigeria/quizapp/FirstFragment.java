package com.mickleentityltdnigeria.quizapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class FirstFragment extends Fragment {

    TextView questionLabel, numberOfQuestionsLabel, scoreLabel;
    Button submitButton;
    EditText answerText;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArrayList;
    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;
    MediaPlayer mp;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.questionLabel = view.findViewById(R.id.Question);
        this.numberOfQuestionsLabel = view.findViewById(R.id.NumberOfQuestions);
        this.scoreLabel = view.findViewById(R.id.Score);
        this.answerText = view.findViewById(R.id.Answer);
        this.progressBar = view.findViewById(R.id.ProgressBar);
        this.submitButton = view.findViewById(R.id.Submit);
        this.questionModelArrayList = new ArrayList<>();
        this.setupQuestions();
        this.setData();
        this.answerText.requestFocus();

        view.findViewById(R.id.Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                checkAnswer();
            }
        });
    }

    public void playHappySound(){
        if(module.isPlaySound) {
            mp = MediaPlayer.create(this.getContext(), R.raw.happy);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void playFailedSound(){
        if(module.isPlaySound) {
            mp = MediaPlayer.create(this.getContext(), R.raw.failed);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    public void checkAnswer(){
        String answer = this.answerText.getText().toString().trim();
        if(answer.equalsIgnoreCase( this.questionModelArrayList.get(this.currentPosition).getAnswer())){
            this.numberOfCorrectAnswer ++;
            this.playHappySound();
            new SweetAlertDialog( this.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Good Job!")
                    .setContentText("Right Answer")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
           this.answerText.requestFocus();
           // Log.e("Answer","Right");
        }else{
            this.playFailedSound();
            new SweetAlertDialog( this.getContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Wrong Answer!")
                    .setContentText("The right answer is: \n" + questionModelArrayList.get(currentPosition).getAnswer())
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
            this.answerText.requestFocus();
           // Log.e("Answer","Wrong");
        }
        this.currentPosition++;
        this.setData();
        this.answerText.setText("");
        int x = ((this.currentPosition)*100) / this.questionModelArrayList.size();
        this.progressBar.setProgress(x);
    }

    public void setupQuestions(){
        this.generateMathTables();
/*        this.questionModelArrayList.add(new QuestionModel("What is 1+2 ?","3"));
        this.questionModelArrayList.add(new QuestionModel("What is 3*3 ?","9"));
        this.questionModelArrayList.add(new QuestionModel("What is 1+40 ?","41"));
        this.questionModelArrayList.add(new QuestionModel("What is 5*3 ?","15"));
        this.questionModelArrayList.add(new QuestionModel("What is 21*8 ?","168"));
        this.questionModelArrayList.add(new QuestionModel("What is 120*120 ?","14400"));
        QuestionModel q = new QuestionModel("What is the Capital of Nigeria ?", "Lagos");
        q.setQuestionString("What is the Capital of United Kingdom ?");
        q.setAnswer("London");
        this.questionModelArrayList.add(q);*/
    }

    public void generateMathTables(){
        for (int i = 2; i <13 ; i++) {
            for (int x = 1; x <13 ; x++) {
                this.questionModelArrayList.add(new QuestionModel("What is " + i + "x" + x + " ?","" + (i*x)));
            }
        }
    }

    public void setData(){
        if(this.questionModelArrayList.size() > this.currentPosition) {
            this.questionLabel.setText(this.questionModelArrayList.get(this.currentPosition).getQuestionString());
            this.numberOfQuestionsLabel.setText("Question no: " + (this.currentPosition + 1));
            this.scoreLabel.setText("Score : " + this.numberOfCorrectAnswer + "/" + this.questionModelArrayList.size());
        }else{

            new SweetAlertDialog(this.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("You have Successfully completed the Math Table Quiz!")
                    .setContentText("You scored: " + numberOfCorrectAnswer + "/" + questionModelArrayList.size())
                    .setConfirmText("Restart?")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            currentPosition = 0;
                            numberOfCorrectAnswer = 0;
                            progressBar.setProgress(0);
                            setData();
                        }
                    })
                    .setCancelText("Close")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            getActivity().finish();
                        }
                    })
                    .show();

        }

    }
}