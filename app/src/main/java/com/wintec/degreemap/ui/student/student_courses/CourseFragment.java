package com.wintec.degreemap.ui.student.student_courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wintec.degreemap.R;
import com.wintec.degreemap.data.model.Course;
import com.wintec.degreemap.ui.student.student_dashboard.DashboardFragment;
import com.wintec.degreemap.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final int ALL_COURSE = 0;
    private static final int FIRST_YEAR = 1;
    private static final int SECOND_YEAR = 2;
    private static final int THIRD_YEAR = 3;

    private RecyclerView mRecyclerView;
    private CourseAdapter mCourseAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String mSelectedPathway;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        Spinner spinner = view.findViewById(R.id.spinner_year);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chooseYear, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mCourseAdapter = new CourseAdapter();

        // get selected pathway
        mSelectedPathway = getArguments().getString(DashboardFragment.BUNDLE_PATHWAY);

        // set recyclerView data
        mRecyclerView = view.findViewById(R.id.recyclerview_all_courses);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCourseAdapter);

        // set pathway title and background color
        setPathwayTextViewFormatting(view, mSelectedPathway);

        return view;
    }

    private void setPathwayTextViewFormatting(View view, String pathway) {
        TextView pathwayTextView = view.findViewById(R.id.pathwayTextView);
        pathwayTextView.setText(Course.getPathwayLabel(pathway));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        // get all course data
        CourseViewModel courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseList().observe(getActivity(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courseList) {
                switch (position) {
                    case ALL_COURSE: {
                        mCourseAdapter.setCourses(courseList);
                        break;
                    }
                    case FIRST_YEAR:
                    case SECOND_YEAR:
                    case THIRD_YEAR: {
                        List<Course> filteredCourseList = new ArrayList<>();
                        for (Course course: courseList) {
                            if(course.getYear() == position)
                                filteredCourseList.add(course);
                        }
                        mCourseAdapter.setCourses(filteredCourseList);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
