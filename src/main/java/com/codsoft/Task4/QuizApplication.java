package com.codsoft.Task4;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuizApplication extends JFrame {

    private final String[] questions = {
        "Which keyword is used to inherit a class in Java?",
        "Which method is the entry point of a Java program?",
        "Which type stores true or false values?",
        "Which collection allows duplicate elements?",
        "Which keyword creates an object in Java?"
    };

    private final String[][] options = {
        {"implements", "extends", "inherits", "super"},
        {"start()", "run()", "main()", "execute()"},
        {"int", "char", "boolean", "String"},
        {"Set", "Map", "List", "None of these"},
        {"class", "new", "this", "static"}
    };

    // Correct answers are stored as option indexes.
    private final int[] correctAnswers = {1, 2, 2, 2, 1};

    private final int[] userAnswers = {-1, -1, -1, -1, -1};
    private final boolean[] timedOut = new boolean[5];

    private final JLabel questionLabel = new JLabel();
    private final JLabel timerLabel = new JLabel();
    private final JLabel progressLabel = new JLabel();

    private final JRadioButton[] optionButtons = new JRadioButton[4];
    private final ButtonGroup optionGroup = new ButtonGroup();

    private final JButton submitButton = new JButton("Submit Answer");

    private int currentQuestion = 0;
    private int score = 0;
    private int timeLeft = 10;

    private Timer timer;

    public QuizApplication() {

        setTitle("Java Quiz Application");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        topPanel.add(progressLabel);
        topPanel.add(timerLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(
            10, 20, 10, 20
        ));

        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(questionLabel);

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submitAnswer());

        showQuestion();

        setVisible(true);
    }

    private void showQuestion() {

        optionGroup.clearSelection();

        progressLabel.setText(
            "Question " + (currentQuestion + 1)
            + " of " + questions.length
        );

        questionLabel.setText(questions[currentQuestion]);

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[currentQuestion][i]);
            optionButtons[i].setEnabled(true);
        }

        submitButton.setEnabled(true);

        timeLeft = 10;
        timerLabel.setText("Time left: " + timeLeft + " seconds");

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, e -> {

            timeLeft--;

            timerLabel.setText(
                "Time left: " + timeLeft + " seconds"
            );

            if (timeLeft <= 0) {
                timedOut[currentQuestion] = true;
                timer.stop();
                submitAnswer();
            }
        });

        timer.start();
    }

    private void submitAnswer() {

        if (!submitButton.isEnabled()) {
            return;
        }

        timer.stop();

        int selectedAnswer = -1;

        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedAnswer = i;
                break;
            }
        }

        userAnswers[currentQuestion] = selectedAnswer;

        if (selectedAnswer == correctAnswers[currentQuestion]) {
            score++;
        }

        submitButton.setEnabled(false);

        for (JRadioButton button : optionButtons) {
            button.setEnabled(false);
        }

        currentQuestion++;

        if (currentQuestion < questions.length) {
            // Brief pause before displaying the next question.
            Timer nextQuestionTimer = new Timer(
                500, e -> showQuestion()
            );
            nextQuestionTimer.setRepeats(false);
            nextQuestionTimer.start();
        } else {
            showResults();
        }
    }

    private void showResults() {

        if (timer != null) {
            timer.stop();
        }

        StringBuilder result = new StringBuilder();

        result.append("QUIZ COMPLETED!\n\n");
        result.append("Final Score: ")
              .append(score).append("/")
              .append(questions.length).append("\n\n");

        result.append("ANSWER SUMMARY\n");

        for (int i = 0; i < questions.length; i++) {

            result.append("\nQ").append(i + 1).append(": ");

            if (userAnswers[i] == correctAnswers[i]) {
                result.append("Correct");
            } else if (timedOut[i]) {
                result.append("Time expired");
            } else if (userAnswers[i] == -1) {
                result.append("Not answered");
            } else {
                result.append("Incorrect");
            }

            result.append("\nYour answer: ");

            if (userAnswers[i] == -1) {
                result.append("No answer");
            } else {
                result.append(options[i][userAnswers[i]]);
            }

            result.append("\nCorrect answer: ")
                  .append(options[i][correctAnswers[i]])
                  .append("\n");
        }

        questionLabel.setText("<html><b>Quiz completed!</b></html>");
        timerLabel.setText("Final Score: " + score + "/" + questions.length);

        for (JRadioButton button : optionButtons) {
            button.setVisible(false);
        }

        submitButton.setVisible(false);

        JTextArea resultArea = new JTextArea(result.toString());
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(560, 300));

        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Quiz Results",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new QuizApplication());
    }
}

