package bu22.fga.mockproject_group2.screen.home.state;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import bu22.fga.mockproject_group2.MainActivity;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.DayOfWeek;
import bu22.fga.mockproject_group2.entity.DayWithRegistedLesson;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.entity.Week;
import bu22.fga.mockproject_group2.model.ListLessonModel;

public class SaveDataState extends BaseState {

  public SaveDataState(MainController mController) {
    super(mController);
  }

  // tim cai cu theo ten -> id
  // thay the ten do bang ham updateLesson voi id o tren -> dong 204
  // load lai database len
  @Override
  public void handeMessage(Message msg) {

    switch (msg.arg1) {
      case Constant.LIST_LESSON:
        onUpdateLesson(msg);
        break;
      case Constant.TIME_TABLE:
        onUpdateTimeTable(msg);
        break;
      default:
        break;
    }
  }

  private void onUpdateLesson(Message msg) {

    String oldName = msg.obj.toString().split(" ")[0];
    String newName = msg.obj.toString().split(" ")[1];

    int lessonId = mDatabaseHelper.getLessonIdByName(oldName);
    mDatabaseHelper.updateLesson(new Lesson(newName), (int) lessonId);

    ((ListLessonModel)mController.getModel()).setResultListData(mDatabaseHelper.getAllLessons());


  }

  private  void onUpdateTimeTable(Message msg)
  {

    Week week  = ((MainActivity)mController.getView()).getNewWeek();
    ArrayList<DayWithRegistedLesson> arrayList = ((MainActivity) mController.getView()).getmTimeTableDatasource();
    String timestart = week.getStartDay();
    String timeend = week.getEndDay();
    ArrayList<DayWithRegistedLesson> arrayList2=new ArrayList<>();
    for(int i=0 ; i < arrayList.size() ; i++)
    {
      if(arrayList.get(i).getDayOfWeek()!= null)
      {
        //  Toast.makeText(mController.getView(),"Xoa"+arrayList.size()+arrayList.get(i).getLesson().getName(),Toast.LENGTH_SHORT).show();
        arrayList2.add(arrayList.get(i));
      }
    }

    HashSet<String> hashSet = new HashSet();
    for(int i = 0 ; i <arrayList2.size() ;i++) {
      hashSet.add(arrayList2.get(i).getDayOfWeek().getName());
    }
    int week_time = mDatabaseHelper.getCoutWeek(timestart,timeend);

    if(week_time == 0) {
      Toast.makeText(mController.getView(), "Khoi tao", Toast.LENGTH_LONG).show();
      mDatabaseHelper.createweek(week);
      Toast.makeText(mController.getView(), "Khoi tao" + mDatabaseHelper.getCoutWeek(timestart,timeend), Toast.LENGTH_LONG).show();
      Week week_by_day = mDatabaseHelper.getWeek(timestart, timeend);

      for (String str : hashSet) {
        DayOfWeek dayOfWeek = new DayOfWeek(str);
        mDatabaseHelper.createDayOfWeek(week_by_day.getId_Week(), dayOfWeek);
      }
//
//
      ArrayList<DayOfWeek> DayOfWeeks = (ArrayList<DayOfWeek>) mDatabaseHelper.getAllDayOfWeek(week_by_day.getId_Week());
      for(int i =0;i< DayOfWeeks.size();i++)
      {
        Log.d("Day of week",DayOfWeeks.get(i).getName());
      }
      for (int i = 0; i < DayOfWeeks.size(); i++)
        for (int j = 0; j < arrayList2.size(); j++) {
          if (DayOfWeeks.get(i).getName().equals(arrayList2.get(j).getDayOfWeek().getName())) {
            arrayList2.get(j).setDayOfWeek(DayOfWeeks.get(i));
          }
        }

      for (int i = 0; i < arrayList2.size(); i++) {
        DayWithRegistedLesson dayWithRegistedLesson = arrayList2.get(i);
        mDatabaseHelper.createDayRegistedLesson(dayWithRegistedLesson);
      }
    }
    else
    {
      Toast.makeText(mController.getView(),"Sua" ,Toast.LENGTH_LONG).show();
      Week week_by_day = mDatabaseHelper.getWeek(timestart, timeend);
      ArrayList<DayOfWeek> DayOfWeeks = week_by_day.getArrayList();
      for(int i=0;i< DayOfWeeks.size();i++)
      {
     mDatabaseHelper.deleteDayOfWeek(DayOfWeeks.get(i).getId_DayOfWeek());
    }

      for (String str : hashSet) {
        DayOfWeek dayOfWeek = new DayOfWeek(str);
        mDatabaseHelper.createDayOfWeek(week_by_day.getId_Week(), dayOfWeek);
        Log.d("dayOfWeek",str);
      }
//
      ArrayList<DayOfWeek> DayOf_Weeks = (ArrayList<DayOfWeek>) mDatabaseHelper.getAllDayOfWeek(week_by_day.getId_Week());
      for(int i =0;i< DayOf_Weeks.size();i++)
      {
        Log.d("Day of week",DayOf_Weeks.get(i).getName());
      }

      for (int i = 0; i < DayOf_Weeks.size(); i++)
        for (int j = 0; j < arrayList2.size(); j++) {
          if (DayOf_Weeks.get(i).getName().equals(arrayList2.get(j).getDayOfWeek().getName())) {
            arrayList2.get(j).setDayOfWeek(DayOf_Weeks.get(i));
            Log.d("day",arrayList2.get(j).getDayOfWeek().getName()+arrayList2.get(j).getLesson());
          }
        }

      for (int i = 0; i < arrayList2.size(); i++) {
        DayWithRegistedLesson dayWithRegistedLesson = arrayList2.get(i);
        mDatabaseHelper.createDayRegistedLesson(dayWithRegistedLesson);
      }


   }
  }
}
