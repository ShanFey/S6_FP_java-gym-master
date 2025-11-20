import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
    private Timetable timetable;
    private Coach coach,coach1,coach2;
    private Group groupAdult,groupChild;
    private TrainingSession mondayChildTrainingSession;
    private TrainingSession thursdayChildTrainingSession, thursdayAdultTrainingSession;
    private TrainingSession saturdayChildTrainingSession;

    private TrainingSession
            mondayAdultTrainingSessionF,
            wednesdayAdultTrainingSessionF,
            mondayAdultTrainingSessionK,
            wednesdayAdultTrainingSessionK,
            fridayAdultTrainingSessionK,
            saturdayAdultTrainingSessionK;

    @BeforeEach
    void setUp() {
        timetable = new Timetable();

        coach = new Coach("Васильев", "Николай", "Сергеевич");
        coach1 = new Coach("Шварценеггер", "Арнольд", "Густавович");
        coach2 = new Coach("Чан", "Джеки", "Чарльзович");

        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        groupAdult = new Group("Фитнес", Age.ADULT, 90);
        mondayAdultTrainingSessionF = new TrainingSession(groupAdult, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0));

        groupAdult = new Group("Фитнес", Age.ADULT, 90);
        wednesdayAdultTrainingSessionF = new TrainingSession(groupAdult, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0));

        groupAdult = new Group("Кунг-фу", Age.ADULT, 90);
        mondayAdultTrainingSessionK = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0));

        groupAdult = new Group("Кунг-фу", Age.ADULT, 90);
        wednesdayAdultTrainingSessionK = new TrainingSession(groupAdult, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(18, 0));

        groupAdult = new Group("Кунг-фу", Age.ADULT, 90);
        fridayAdultTrainingSessionK = new TrainingSession(groupAdult, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(18, 0));

        groupAdult = new Group("Кунг-фу", Age.ADULT, 90);
        saturdayAdultTrainingSessionK = new TrainingSession(groupAdult, coach2,
                DayOfWeek.SATURDAY, new TimeOfDay(18, 0));

        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        timetable.addNewTrainingSession(mondayChildTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        assertNotNull(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY),"Не удалось добавить список занятий!");
        //Проверить, что за вторник не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY),"Занятие добавлено некорректно: день недели!");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertNotNull(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY),"Не удалось добавить список занятий!");
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>>  thursdayTrainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        NavigableSet<TimeOfDay> thursdayTrainingSessionNavigable = thursdayTrainingSession.navigableKeySet();
        assertEquals(thursdayTrainingSessionNavigable.first(),new TimeOfDay(13, 0),"Порядок по времени не соблюден");
        // Проверить, что за вторник не вернулось занятий
        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY),"Занятие добавлено некорректно: день недели!");

    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>>  trainingSession = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        int countTrainingSession13 = trainingSession.get(new TimeOfDay(13,0)).size();
        assertEquals(countTrainingSession13,1,"Ошибка добавления занятий в список");
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        boolean containTrainingSession14 = trainingSession.containsKey(new TimeOfDay(14,0));
        assertFalse(containTrainingSession14,"Ошибка добавления занятий в список");
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWhichDontExist() {
       timetable.addNewTrainingSession(thursdayChildTrainingSession);

        // проверить, что в timetable в заданный день и время нет занятий
        ArrayList<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(9, 0)
        );

        assertNull(sessions,"Создана сессия в незаданный по условию день или время");
    }

    @Test
    void testMultipleSessionsSameTime() {
        // Создаем два занятия на одно время
        TrainingSession session1 = new TrainingSession(
                groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(9, 0)
        );

        TrainingSession session2 = new TrainingSession(
                groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(9, 0)
        );

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        ArrayList<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.THURSDAY, new TimeOfDay(9, 0)
        );

        assertEquals(2, sessions.size(),"Не удалось создать 2 сесии в один день и в одно время");
    }

    @Test
    void testNullTimetable() {
        for (DayOfWeek day : DayOfWeek.values()) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(day);
            assertNull(sessions,"Таблица не пуста");
        }
    }

    @Test
    void testCoachStatsCount() {
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSessionF);
        timetable.addNewTrainingSession(wednesdayAdultTrainingSessionF);
        timetable.addNewTrainingSession(mondayAdultTrainingSessionK);
        timetable.addNewTrainingSession(wednesdayAdultTrainingSessionK);
        timetable.addNewTrainingSession(fridayAdultTrainingSessionK);
        //timetable.addNewTrainingSession(saturdayAdultTrainingSessionK);

        List<CounterOfTrainings> coachStats = timetable.getCountByCoaches();

        assertNotNull(timetable.getCountByCoaches(),"Нет добавленных сессий с тренерами");
        assertEquals(4,coachStats.get(0).getCount(),"Неверный подсчет сессий");
        assertEquals(2,coachStats.get(2).getCount(),"Неверный подсчет сессий");
        assertEquals(3,coachStats.get(1).getCount(),"Неверный подсчет сессий");
    }

}
