package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;
import android.widget.Toast;

import java.util.ArrayList;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayOfWeek;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.entity.Week;
import bu22.fga.mockproject_group2.util.DatabaseHelper;

public class LoadDataState extends BaseState {

    public static final int LOAD_TIME_TABLE = 1;
    public static final int LIST_LESSON_NAME = 2;
    private ArrayList<DayWithRegistedLesson> mList ;
    private DatabaseHelper mDatabase = new DatabaseHelper(mController.getView().getApplicationContext());

    public LoadDataState(MainController mController) {
        super(mController);
    }

    @Override
    public void handeMessage(Message msg) {

        fakeData();
        int count = 0;
        String timestart = ((MainActivity) mController.getView()).getDaytimestrat();
        String timeend = ((MainActivity) mController.getView()).getDaytimeend();

        int cout = mDatabaseHelper.getCoutWeek(timestart, timeend);
        ArrayList<DayWithRegistedLesson> DayWithRegistedLessons = mUltilites.initTimeTableData();

        if (cout == 1) {
           // Toast.makeText(mController.getView(), timestart + " " + timeend, Toast.LENGTH_LONG).show();
            ArrayList<DayWithRegistedLesson> arrayListDay = new ArrayList<>();
            Week week = mDatabaseHelper.getWeek(timestart, timeend);
            ArrayList<DayOfWeek> arrayList = week.getArrayList();
            ArrayList<Lesson> arrayListlesson = (ArrayList<Lesson>) mDatabaseHelper.getAllLessons();
            for (int i = 0; i < arrayList.size(); i++) {
                arrayListDay.addAll(mDatabaseHelper.getALLDayWithRegisteDayOfWeek(arrayList.get(i).getId_DayOfWeek()));
            }
            for (int i = 0; i < arrayListDay.size(); i++) {
                int pos = arrayListDay.get(i).getPosition();
                int day = Integer.parseInt(String.valueOf(arrayListDay.get(i).getDayOfWeek().getName().charAt(3)));
                int location = pos * 7 + day - 1;
                DayWithRegistedLessons.set(location, arrayListDay.get(i));

            }
        ((MainActivity) mController.getView()).getmModel().setDataToLoad(DayWithRegistedLessons, mDatabaseHelper.getAllLessons(), true);

    }else {
            Toast.makeText(mController.getView(),"Khoi dong",Toast.LENGTH_LONG).show();

            ((MainActivity)mController.getView()).getmModel().setDataToLoad(mUltilites.initTimeTableData(), mDatabaseHelper.getAllLessons(), true);
        }



    }
    private void fakeData() {
        mDatabaseHelper.deleteAllLesson();
        mDatabaseHelper.createLesson(new Lesson("Hoa"));
        mDatabaseHelper.createLesson(new Lesson("Ly"));
        mDatabaseHelper.createLesson(new Lesson("Sinh"));
        mDatabaseHelper.createLesson(new Lesson("Su"));
        mDatabaseHelper.createLesson(new Lesson("Dia"));
        mDatabaseHelper.createLesson(new Lesson("Toan"));


    }
}
