package bu22.fga.mockproject_group2.screen.home.event;

import android.graphics.Color;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;


public class DragDropListenter implements View.OnDragListener {

    private int mTypeView;
    private MainController mController;
    private int curentDrop;

    public DragDropListenter(MainController mController, int curentDrop, int typeView) {
        this.mController = mController;
        this.curentDrop = curentDrop;
        this.mTypeView = typeView;
    }

    public DragDropListenter(MainController mController, int curentDrop) {
        this.mController = mController;
        this.curentDrop = curentDrop;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorBlack));
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                view.setBackgroundColor(Color.WHITE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                break;
            case DragEvent.ACTION_DROP:
                if(mTypeView == 0){
                    onDrop(curentDrop);
                }
                view.setBackgroundColor(Color.WHITE);
                break;
            default:
                view.setBackgroundColor(Color.WHITE);
                break;
        }
        return true;
    }

    private void onDrop(int curentDrop) {

            if (this.mTypeView == 0) {
                Toast.makeText(mController.getView(), "table item", Toast.LENGTH_SHORT).show();
            }/* else {
                Toast.makeText(mController.getView(), "list item", Toast.LENGTH_SHORT).show();
            }*/
//        new ConfirmDialog(mController.getView(), mController, curentDrop).show();
//        int lessonPosition = (int) curentDrop / 7;
//        int day = (curentDrop % 7) + 1;
//        Toast.makeText(mController.getView(), "position: " + lessonPosition + "day: " + day, Toast.LENGTH_LONG).show();
    Message msg = new Message();
      msg.what = Constant.DRAP_AND_DROP;
      msg.obj = Constant.EVENT_DROP;
      msg.arg1 = Constant.EDIT_LESSON;
      msg.arg2 = curentDrop;
      mController.sendMessage(msg);
    }

}
