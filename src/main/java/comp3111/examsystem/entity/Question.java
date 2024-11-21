    package comp3111.examsystem.entity;

    public class Question extends Entity{



        private String question;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
        private String type;
        private float score;



        /**
         * Conversion constructor
         * @param question question
         * @param options options
         * @param answer answer
         * @param type type
         * @param score score
         */
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

        /**
         * Default constructor
         */
        public Question() {
        }

        /**
         * @return question
         */
        public String getQuestion() {
            return question;
        }

        /**
         * set question
         * @param question question
         */
        public void setQuestion(String question) {
            this.question = question;
        }

        /**
         * @return option A
         */
        public String getOptionA() {
            return optionA;
        }

        /**
         * set option A
         * @param optionA option A
         */
        public void setOptionA(String optionA) {
            this.optionA = optionA;
        }

        /**
         * @return option B
         */
        public String getOptionB() {
            return optionB;
        }

        /**
         * set option B
         * @param optionB option B
         */
        public void setOptionB(String optionB) {
            this.optionB = optionB;
        }

        /**
         * @return option C
         */
        public String getOptionC() {
            return optionC;
        }

        /**
         * set option C
         * @param optionC option C
         */
        public void setOptionC(String optionC) {
            this.optionC = optionC;
        }

        /**
         * @return option D
         */
        public String getOptionD() {
            return optionD;
        }

        /**
         * set option D
         * @param optionD option D
         */
        public void setOptionD(String optionD) {
            this.optionD = optionD;
        }

        /**
         * @return answer
         */
        public String getAnswer() {
            return answer;
        }

        /**
         * set answer
         * @param answer answer
         */
        public void setAnswer(String answer) {
            this.answer = answer;
        }

        /**
         * @return type
         */
        public String getType() {
            return type;
        }

        /**
         * set type
         * @param type type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return score
         */
        public double getScore() {
            return score;
        }

        /**
         * set score
         * @param score score
         */
        public void setScore(float score) {
            this.score = score;
        }
    }