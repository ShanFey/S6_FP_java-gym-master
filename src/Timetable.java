import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsPerDay;
        ArrayList<TrainingSession> sessionsForHour;
        sessionsPerDay = timetable.get(trainingSession.getDayOfWeek());
        if (sessionsPerDay == null) {
            sessionsPerDay = new TreeMap<>();
            sessionsForHour = new ArrayList<>();
            sessionsForHour.add(trainingSession);
        } else {
            if (!sessionsPerDay.containsKey(trainingSession.getTimeOfDay())) {
                sessionsForHour = new ArrayList<>();
            } else {
                sessionsForHour = sessionsPerDay.get(trainingSession.getTimeOfDay());
            }
            sessionsForHour.add(trainingSession);
        }
        sessionsPerDay.put(trainingSession.getTimeOfDay(), sessionsForHour);
        timetable.put(trainingSession.getDayOfWeek(), sessionsPerDay);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public List<CounterOfTrainings> getCountByCoaches(){
        HashMap<Coach, Integer> coachStats = new HashMap<>();

        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsPerDay : timetable.values()) {
            for (ArrayList<TrainingSession> sessionsForHour : sessionsPerDay.values()) {
                for (TrainingSession session: sessionsForHour) {
                    Coach coach = session.getCoach();
                    if (coachStats.containsKey(coach)) {
                        int count = coachStats.get(coach);
                        int newCount = count + 1;
                        coachStats.put(coach, newCount);
                    } else {
                        coachStats.put(coach,1);
                    }
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry: coachStats.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(), entry.getValue());
            counterOfTrainingsList.add(counterOfTrainings);
        }

        counterOfTrainingsList.sort(new CountComparator());

        return counterOfTrainingsList;
    }



}

