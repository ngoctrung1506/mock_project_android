package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.util.DatabaseHelper;

/**
 * Created by Admin on 19/04/2018.
 */

public class DragAndDropState extends BaseState {

    public static final int LIST_LESSON = 1;
    public static final int TIME_TABLE = 2;
    private DatabaseHelper mDatabase = new DatabaseHelper(
            mController.getView().getApplicationContext());
    private int mCurentDrag;
    private Lesson mLesson;
    private String mNameLesson;

    public DragAndDropState(MainController mController) {
        super(mController);
    }

    @Override
    public void handeMessage(Message msg) {

        switch (msg.obj.toString()) {

            case Constant.EVENT_DRAP:
                int gridWasDragged = msg.sendingUid;
                switch (gridWasDragged) {
                    case LIST_LESSON:
                        ((MainActivity) mController.getView()).getmModel()
                                .confirmIsListLessonNameItem(true);

                        break;
                    case TIME_TABLE:
                        ((MainActivity) mController.getView()).getmModel()
                                .confirmIsListLessonNameItem(false);
                        break;
                    default:
                        break;
                }
                mCurentDrag = msg.arg2;
                Log.e("CurentDrag: ", "" + mCurentDrag);
                ((MainActivity) mController.getView()).getmModel().setCurentDrag(mCurentDrag);
                ((MainActivity) mController.getView()).getmModel().setFinishedLoadData(true);
                Log.e("Size of database ", "" + mDatabase.getAllLessons().size());
//                mLesson = mDatabase.getAllLessons().get(mCurentDrag);
//                mNameLesson = mLesson.getName().toString();
//                Log.e("ListLessonName: ",""+mLesson.getName().toString());


                break;

            case Constant.EVENT_DROP:
                switch (msg.arg1) {
                    case Constant.DELETE_LESSON:
                        onDeleteLesson();
                        break;
                    case Constant.EDIT_LESSON:
                        onEditLesson(msg);
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void onEditLesson(Message msg) {
        int curentDrop = msg.arg2;
        boolean isListLessonWasDragged = ((MainActivity) ((MainActivity) mController.getView()))
                .getmModel().isListLessonNameItem();
        Lesson curLesson = null;
        if (curentDrop != mCurentDrag) {

            if (isListLessonWasDragged) {
            List<Lesson> listLessonName = (((MainActivity)mController.getView())).getmModel().getListLessonName();
             curLesson = listLessonName.get(((MainActivity)mController.getView()).getmModel().getCurentDrag());
//            ((MainActivity)mController.getView()).getmModel().setDataForEditLessonForListLesson(curentDrop, curLesson, ((MainActivity)mController.getView()).getmModel().getCurentDrag(), new Lesson());

        } else {
            ArrayList<DayWithRegistedLesson> timeTable = ((MainActivity)mController.getView()).getmModel().getTimeTable();
            curLesson = timeTable.get(((MainActivity)mController.getView()).getmModel().getCurentDrag()).getLesson();
//            ((MainActivity)mController.getView()).getmModel().setDataForEditLessonForTimeTable(curentDrop, curLesson, ((MainActivity)mController.getView()).getmModel().getCurentDrag(), new DayWithRegistedLesson());
        }

        ((MainActivity) mController.getView()).getmModel()
                .setDataForEditLesson(curentDrop, curLesson,
                        ((MainActivity) mController.getView()).getmModel().getCurentDrag(),
                        new DayWithRegistedLesson(" "));

    }

    private void onDeleteLesson() {

        boolean isListLessonWasDragged = ((MainActivity) mController.getView()).getmModel()
                .isListLessonNameItem();
        if (isListLessonWasDragged) {
            List<Lesson> listLessonName = ((MainActivity) mController.getView()).getmModel()
                    .getListLessonName();
            ((MainActivity) mController.getView()).getmModel().setDataForDeleteLesson(
                    ((MainActivity) mController.getView()).getmModel().getCurentDrag(),
                    new Lesson(), "CaseListLesson");

            Log.d("OLD SIZE BEFORE DELETE", "" + mDatabase.getAllLessons().size());
            mDatabase.delete(mDatabase.getAllLessons().get(mCurentDrag));
            //   mDatabase.delete(mLesson);
            Log.d("OLD SIZE", "" + mDatabase.getAllLessons().size());
            ((MainActivity) mController.getView()).loadData();


        } else {
            ArrayList<DayWithRegistedLesson> timeTable = ((MainActivity) mController.getView())
                    .getmModel().getTimeTable();
            ((MainActivity) mController.getView()).getmModel().setDataForDeleteLesson(
                    ((MainActivity) mController.getView()).getmModel().getCurentDrag(),
                    new Lesson(), "CaseTimeTable");
        }
    }


}
