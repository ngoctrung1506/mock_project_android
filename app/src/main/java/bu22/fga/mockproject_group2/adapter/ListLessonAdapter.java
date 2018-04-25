package bu22.fga.mockproject_group2.adapter;

import android.content.ClipData;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bu22.fga.mockproject_group2.R;
import bu22.fga.mockproject_group2.constant.Constant;
import bu22.fga.mockproject_group2.controller.MainController;
import bu22.fga.mockproject_group2.entity.Lesson;
import bu22.fga.mockproject_group2.screen.home.event.DragDropListenter;

public class ListLessonAdapter extends BaseAdapter {

    private List<Lesson> mDatasource;
    private MainController mController;
    private boolean mIsEditable;
    private OnSendLessonNameBackToMainScreen mOnSendName;

    public ListLessonAdapter(List<Lesson> mDatasource, MainController mController, OnSendLessonNameBackToMainScreen onSendName) {
        this.mOnSendName = onSendName;
        this.mDatasource = mDatasource;
        this.mController = mController;
    }

    public void setListData(List<Lesson> mDatasource) {
        this.mDatasource = mDatasource;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return Constant.MAX_LIST_LESSON_SIZE;
    }

    @Override
    public Lesson getItem(int i) {
        return mDatasource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

           Context context = viewGroup.getContext();
           Lesson lesson = null;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder vh;
            if (view == null) {
                vh = new ViewHolder();
                view = inflater.inflate(R.layout.item_row, viewGroup, false);
                vh.mTvName = view.findViewById(R.id.it_tv_lesson_name);
                view.setTag(vh);
                Log.d("edit", "addListener: " + mIsEditable);
                if(i < mDatasource.size()) {
                    lesson = getItem(i);
                    view.setId(R.id.always + i + 49);
                    vh.mTvName.setText(lesson.getName());
                    addListener(view.getRootView(), i, lesson.getName());
                }
            } else {
                vh = (ViewHolder) view.getTag();
            }

        return view;
    }

    public void setEditable(boolean editable) {
        mIsEditable = editable;
//        this.notifyDataSetChanged();
    }

    private void addListener(View view, final int i, final String lessonName) {
        Log.d("edit", "addListener: " + mIsEditable);
        if (!mIsEditable) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        onDragBegin(view, i);
                        view.startDrag(data, shadowBuilder, view, 0);
                    }
                    return true;
                }
            });
            view.setOnDragListener(new DragDropListenter(mController, i));
        } else {
            view.setOnTouchListener(null);
            view.setOnDragListener(null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnSendName.onSendLessonName(lessonName);
                }
            });

        }
    }

    private void onDragBegin(View view, int curentDrag) {
        Message msg=new Message();
        msg.what= Constant.DRAP_AND_DROP;
        msg.obj = Constant.EVENT_DRAP;
        msg.arg2 = curentDrag;
        msg.sendingUid= Constant.LIST_LESSON;
        mController.sendMessage(msg);
    }

    public void setListLesson(List<Lesson> lessons) {
        this.mDatasource = lessons;
        notifyDataSetChanged();
    }


    public class ViewHolder {
        private TextView mTvName;
    }

    public interface OnSendLessonNameBackToMainScreen{
        void onSendLessonName(String lessonName);
    }
}
