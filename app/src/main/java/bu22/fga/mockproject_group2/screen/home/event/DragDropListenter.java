package bu22.fga.mockproject_group2.screen.home.event;

import android.graphics.Color;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.dialog.ConfirmDialog;


public class DragDropListenter implements View.OnDragListener {

    private MainController mController;
    private int curentDrop;

    public DragDropListenter(MainController mController, int curentDrop) {
        this.mController = mController;
        this.curentDrop = curentDrop;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        View view1= (View) dragEvent.getLocalState();
        int idDrag = view1.getId()-R.id.always;
        int dropViewId = view.getId()-R.id.always;
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorBlack));
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                view.setBackgroundColor(Color.WHITE);
                break;

            case DragEvent.ACTION_DROP:
                if(idDrag>=49 && dropViewId < 49){
                    onDropItemFromListLesson();
                }
                else {
                    onDropItemFromTimeTable();
                }
                view.setBackgroundColor(Color.WHITE);
                break;
            default:
                view.setBackgroundColor(Color.WHITE);
                break;
        }
        return true;
    }

    private void onDropItemFromTimeTable() {
        Message msg = new Message();
        msg.what = Constant.DRAP_AND_DROP;
        msg.obj = Constant.EVENT_DROP;
        msg.arg1 = Constant.EDIT_LESSON;
        msg.arg2 = curentDrop;
        mController.sendMessage(msg);
    }

    private void onDropItemFromListLesson() {
        new ConfirmDialog(mController.getView(), mController, curentDrop).show();
    }

}
