package bu22.fga.mockproject_group2.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;

public class TimeTableModel
{
    public static final String TAG = TimeTableModel.class.getName();

    private String result;

    public static final String EVENT_LOAD_DATA = "EVENT_LOAD_DATA";

    private PropertyChangeSupport mPropertyChangeSupport;

    private static TimeTableModel mModel = null;
    private ArrayList<DayWithRegistedLesson> mTimeTable;
    private ArrayList<Lesson> mListLessonName;

    private int mCurentDrag =-1;
    private boolean mIsListLessonNameItem =false;
    private boolean finishedLoadData = false;

    public TimeTableModel() {
        this.mPropertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void setPropertyChangeSupportListenner(PropertyChangeListener listenner) {
        mPropertyChangeSupport.addPropertyChangeListener(listenner);
    }

    public boolean isListLessonNameItem() {
        return mIsListLessonNameItem;
    }

    public void confirmIsListLessonNameItem(boolean mIsListLessonNameItem) {
        this.mIsListLessonNameItem = mIsListLessonNameItem;
    }

    public int getCurentDrag() {
        return mCurentDrag;
    }

    public void setCurentDrag(int mCurentDrag) {
        this.mCurentDrag = mCurentDrag;
    }

    public boolean isFinishedLoadData() {
        return finishedLoadData;
    }

    public ArrayList<DayWithRegistedLesson> getTimeTable() {
        if (mTimeTable == null) {
            mTimeTable = new ArrayList<>();
        }
        return mTimeTable;
    }

    public void setFinishedLoadData(boolean finishedLoadData) {
        this.finishedLoadData = finishedLoadData;
    }

    public ArrayList<Lesson> getListLessonName() {
        if (mListLessonName == null) {
            mListLessonName = new ArrayList<>();
        }
        return mListLessonName;
    }

    public static TimeTableModel newInstance() {
        if (mModel == null) {
            mModel = new TimeTableModel();
        }
        return mModel;
    }

    public void setDataToLoad(ArrayList<DayWithRegistedLesson> listTimeTableData, ArrayList<Lesson> listLessonName, boolean finishedLoadData) {
        this.mTimeTable = listTimeTableData;
        this.mListLessonName = listLessonName;
        this.finishedLoadData = finishedLoadData;
        mPropertyChangeSupport.firePropertyChange(EVENT_LOAD_DATA, null, null);
    }

    public void setDataForEditLesson(int curentDrop, Lesson curLesson, int curentDrag, DayWithRegistedLesson lesson) {
        this.mTimeTable.set(curentDrop, new DayWithRegistedLesson(curLesson));
        this.mTimeTable.set(curentDrag, lesson);
//        Log.d(TAG, "setDataForEditLesson: " + mTimeTable.get(curentDrop).getLesson().getName() + " " + mTimeTable.get(curentDrag).getLesson().getName());
        mPropertyChangeSupport.firePropertyChange(EVENT_LOAD_DATA, null, null);
    }

    public void setDataForDeleteLesson(int curentDrag, Lesson lesson, String caseDelete) {
        if(caseDelete.equals("CaseListLesson")){
            mListLessonName.set(curentDrag, new Lesson(""));
        }
        else if(caseDelete.equals("CaseTimeTable")){
            mTimeTable.set(curentDrag, new DayWithRegistedLesson(lesson));
        }
        mPropertyChangeSupport.firePropertyChange(EVENT_LOAD_DATA, null, null);
    }

    public void setmListLessonName(ArrayList<Lesson> mListLessonName) {
        this.mListLessonName = mListLessonName;
    }
}
