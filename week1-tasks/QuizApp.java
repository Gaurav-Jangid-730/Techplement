import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

class Question {
    private final String questionText;
    private final List<String> options;
    private final int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctOptionIndex;
    }

    public void displayQuestion(int num) {
        System.out.println(num+"). "+questionText);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }
    public String getQuestionText()
    {
        return questionText;
    }
}

class Quiz {
    private final String topic;
    private final List<Question> questions;

    public Quiz(String topic) {
        this.topic = topic;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int num=0;
        for (Question question : questions) {
            question.displayQuestion(++num);
            int answer = scanner.nextInt();
            if (question.isCorrect(answer - 1)) {
                score++;
            }
        }
        System.out.println("*********************************************************************************************************");
        System.out.println("Correct Ans : " + score + "/" + questions.size());
        double per = ((double) score/ (double) questions.size())*100;
        System.out.println("Percentage : "+Math.floor(per*100)/100+"%");
        if(per>=80.00) System.out.println("Status : Passed <3");
        else System.out.println("Status : Failed :(");
        System.out.println("*********************************************************************************************************\n");
    }

    public Object getTopic() {
        return topic.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(topic, quiz.topic);
    }

    public boolean contain(String S) {
        for(Question qe : questions)
        {
            if(S.equals(qe.getQuestionText()))
            {
                return true;
            }
        }
        return false;
    }
}

public class QuizApp {
    private static final List<Quiz> quizzes = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Welcome To My Quiz App");
        System.out.print("Choose A Option To Start :-");
        System.out.println();
        while (!exit) {
            System.out.println("=========================================================================================================");
            System.out.print("\t\t\t 1. Create Quiz");
            System.out.print("\t 2. Add Questions In Quiz");
            System.out.print("\t 3. Take Quiz");
            System.out.print("\t 4. Exit\n");
            System.out.println("=========================================================================================================\n");
            String choice = scanner.nextLine();
            switch (choice) {
                case "create quiz":
                    createQuiz();
                    break;
                case "add questions":
                    addQuestion();
                    break;
                case "take quiz":
                    takeQuiz();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter A New Quiz Topic: ");
        String topic = scanner.nextLine();
        Quiz quiz = new Quiz(topic.toUpperCase());
        if(!quizzes.contains(quiz)) {
            quizzes.add(quiz);
            System.out.print("Quiz Created Successfully!\n\n");
        }
        else System.out.print("Topic Is Already Exist\n\n");
    }

    private static void addQuestion() {
        if(quizzes.isEmpty())
        {
            System.out.println("No Quiz Topics Found!\n");
            return;
        }
        System.out.println("If you not know quiz proper name you can search.\nTo search type Y or y but if you know the topic name then type N or n:-");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Want To Search Topic ? [Y/N]");
        String topic;
        char a = scanner.nextLine().charAt(0);
        if(a=='Y' || a=='y'){
            int i=0;
            for(Quiz qz : quizzes)
            {
                System.out.println(++i+")."+qz.getTopic());
            }
            System.out.println();
            System.out.println("Enter Topic Number To Select :");
            i = scanner.nextInt();
            scanner.nextLine();
            topic = quizzes.get(i-1).getTopic().toString();
        }else if(a=='N' || a=='n') {
            System.out.print("Enter quiz topic to add question to: ");
            topic = scanner.nextLine();
        }
        else return;
        for (Quiz quiz : quizzes) {
            if (quiz.getTopic().equals(topic.toUpperCase())) {
                System.out.print("Enter question: ");
                String questionText = scanner.nextLine();
                if(quiz.contain(questionText.toUpperCase())) {
                    System.out.println("Quiz Already Contain This Question");
                    return;
                }
                List<String> options = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    System.out.print("Enter option " + (i + 1) + ": ");
                    options.add(scanner.nextLine());
                }
                System.out.print("Enter correct option number (1-4): ");
                int correctOptionIndex = scanner.nextInt() - 1;
                quiz.addQuestion(new Question(questionText.toUpperCase(), options, correctOptionIndex));
                System.out.println();
                System.out.println("*********************************************************************************************************");
                System.out.println("Question added successfully!");
                System.out.println("*********************************************************************************************************\n");
                return;
            }
        }
        System.out.println("Quiz not found.");
    }

    private static void takeQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********************************************************************************************************");
        System.out.print("Enter quiz topic to take: ");
        String topic = scanner.nextLine();
        for (Quiz quiz : quizzes) {
            if (quiz.getTopic().equals(topic.toUpperCase())) {
                quiz.takeQuiz();
                System.out.println("*********************************************************************************************************");
                return;
            }
        }
        System.out.println("Quiz not found.");
        System.out.println("*********************************************************************************************************");
    }
}
