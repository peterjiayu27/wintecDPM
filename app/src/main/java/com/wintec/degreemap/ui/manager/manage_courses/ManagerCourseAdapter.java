package com.wintec.degreemap.ui.manager.manage_courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wintec.degreemap.R;
import com.wintec.degreemap.data.model.Course;

import java.util.List;

import static com.wintec.degreemap.util.Constants.PATHWAY_DATABASE_ARCHITECTURE;
import static com.wintec.degreemap.util.Constants.PATHWAY_NETWORK_ENGINEERING;
import static com.wintec.degreemap.util.Constants.PATHWAY_SOFTWARE_ENGINEERING;
import static com.wintec.degreemap.util.Constants.PATHWAY_WEB_DEVELOPMENT;

public class ManagerCourseAdapter extends RecyclerView.Adapter<ManagerCourseAdapter.CourseViewHolder> {
    private List<Course> mCourseList;
    private Course mSelectedCourse;
    private String mSelectedPathway;
    private OnItemClickListener mListener;

    public ManagerCourseAdapter(String selectedPathway) {
        super();
        mSelectedPathway = selectedPathway;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView courseCard;
        public LinearLayout courseCardLayout;
        public TextView courseCodeTextView;
        public TextView courseNameTextView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseCard = itemView.findViewById(R.id.courseCard);
            courseCardLayout = itemView.findViewById(R.id.courseCardLayout);
            courseCodeTextView = itemView.findViewById(R.id.courseCode);
            courseNameTextView = itemView.findViewById(R.id.courseLongName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
    }

    public void setCourses(List<Course> courseList) {
        this.mCourseList = courseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        CourseViewHolder evh = new CourseViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        mSelectedCourse = mCourseList.get(position);
        holder.courseCodeTextView.setText(mSelectedCourse.getCode());
        holder.courseNameTextView.setText(mSelectedCourse.getLongName());

        switch (mSelectedPathway) {
            case PATHWAY_NETWORK_ENGINEERING:
                holder.courseCardLayout.setBackgroundResource(R.drawable.bg_course_item_purple);
                break;
            case PATHWAY_WEB_DEVELOPMENT:
                holder.courseCardLayout.setBackgroundResource(R.drawable.bg_course_item_blue);
                break;
            case PATHWAY_DATABASE_ARCHITECTURE:
                holder.courseCardLayout.setBackgroundResource(R.drawable.bg_course_item_green);
                break;
            case PATHWAY_SOFTWARE_ENGINEERING:
                holder.courseCardLayout.setBackgroundResource(R.drawable.bg_course_item_red);
                break;
        }

        holder.itemView.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return mCourseList == null ? 0 : mCourseList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}