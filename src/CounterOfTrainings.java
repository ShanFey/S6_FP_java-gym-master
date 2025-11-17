public class CounterOfTrainings {
    private Coach coach;
    private Integer count;

    public CounterOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Тренер: " + coach + ", Тренировок: " + count;
    }


}

