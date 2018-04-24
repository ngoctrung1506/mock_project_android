package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayOfWeek;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
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

     //   fakeData();
//        mController.getView().getmModel().setListLessonName(mUltilites.initListLessName());
//        mController.getView().getmModel().setFinishedLoadData(true);
//        mModel.notifyObservers();
       for(int i =0;i<mDatabase.getAllLessons().size();i++){
           Log.e("Lesson",""+mDatabase.getAllLessons().get(i).getId_lesson() + " __ "+ mDatabase.getAllLessons().get(i).getName());
       }
       // unitData();
//        mList = mDatabase.getALLDayWithRegistedLesson(1);
        ((MainActivity)mController.getView()).getmModel().setDataToLoad(mUltilites.initTimeTableData(),mDatabase.getAllLessons(),true);

    }
    private void unitData(){
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(1,"Monday"),new Lesson("Toan"),2));
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(2,"Tues"),new Lesson("Ly"),3));
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(3,"Wed"),new Lesson("Hoa"),4));
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(4,"Thur"),new Lesson("Van"),5));
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(5,"Fri"),new Lesson("Su"),6));
        mDatabase.createDayRegistedLesson( new DayWithRegistedLesson
                (1,new DayOfWeek(6,"Sat"),new Lesson("Dia"),7));
    }

    private void fakeData() {
        mDatabase.deleteAllLesson();
        mDatabase.createLesson(new Lesson("Hoa"));
        mDatabase.createLesson(new Lesson("Ly"));
        mDatabase.createLesson(new Lesson("Sinh"));
        mDatabase.createLesson(new Lesson("Su"));
        mDatabase.createLesson(new Lesson("Dia"));
        mDatabase.createLesson(new Lesson("Toan"));
        ((MainActivity)mController.getView()).getmModel().setDataToLoad(mUltilites.initTimeTableData(), mDatabase.getAllLessons(), true);


    }
}
