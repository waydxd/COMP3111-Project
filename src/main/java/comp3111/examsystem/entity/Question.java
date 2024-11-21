    package comp3111.examsystem.entity;

    import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.property.StringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;

    public class Question extends Entity{



        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
        private String type;
        private float score;



        public Question(String question, String[] options, String answer, String type, String score) {

            this.question = question;
            this.optionA = options[0];
            this.optionB = options[1];
            this.optionC = options[2];
            this.optionD = options[3];
            this.answer = answer;
            this.type = type;
            this.score = Float.parseFloat(score);
        }
        public Question(String question, String[] options, String answer, String type, float score) {

            this.question = question;
            this.optionA = options[0];
            this.optionB = options[1];
            this.optionC = options[2];
            this.optionD = options[3];
            this.answer = answer;
            this.type = type;
            this.score = score;
        }
        public Question() {
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOptionA() {
            return optionA;
        }

        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        public String getOptionB() {
            return optionB;
        }

        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        public String getOptionC() {
            return optionC;
        }

        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        public String getOptionD() {
            return optionD;
        }

        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }
    }