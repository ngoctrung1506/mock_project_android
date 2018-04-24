package bu22.fga.mockproject_group2.controller;

import android.os.Message;
import android.util.Log;

import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.entity.Lesson;
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

      default:
        break;
    }
  }

  private void onUpdateLesson(Message msg) {

    String oldName = msg.obj.toString().split(" ")[0];
    String newName = msg.obj.toString().split(" ")[1];

    int lessonId = mDatabaseHelper.getLessonIdByName(oldName);
    mDatabaseHelper.updateLesson(new Lesson(newName), (int) lessonId);
    int result = mDatabaseHelper.updateDayRegistedLessonName(lessonId);
    Log.d("result", "onUpdateLesson: " + result);

    ((ListLessonModel)mController.getModel()).setResultListData(mDatabaseHelper.getAllLessons());
  }
}
