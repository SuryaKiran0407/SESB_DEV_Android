package com.enstrapp.fieldtekpro.notifications;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.enstrapp.fieldtekpro.R;
import com.enstrapp.fieldtekpro.errordialog.Error_Dialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Notifications_Create_Task_Fragment extends Fragment
{

    private List<Task_Object> task_list = new ArrayList<>();
    List cc_list = new ArrayList();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    TASK_ADAPTER task_adapter;
    TextView noData_tv;
    Error_Dialog error_dialog = new Error_Dialog();
    int add_task_type = 1;
    String selected_pos = "", selected_status = "I", item_key = "", completedby = "", completion_time_formatted = "", completion_time = "", completion_date_formatted = "", completion_date = "", planned_end_time_formatted = "", planned_st_time_formatted = "", success_status = "", completed_status = "", release_status = "", planned_end_time = "", planned_end_date_formatted = "", planned_end_date = "", planned_st_time = "", planned_st_date_formatted = "", planned_st_date = "", task_responsible = "", taskprocessor_text = "", taskprocessor_id = "", task_text = "", taskcodegroup_id = "", taskcodegroup_text ="", taskcode_id ="", taskcode_text = "";
    ArrayList<HashMap<String, String>> selected_tasks_custom_info_arraylist = new ArrayList<>();
    int count = 0, selected_position = 0;
    boolean isSelected = false;
    Notifications_Create_Activity nca;


    public Notifications_Create_Task_Fragment()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.notifications_task_fragment, container, false);

        noData_tv = (TextView)rootView.findViewById(R.id.noData_tv);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        nca = (Notifications_Create_Activity) this.getActivity();

        recyclerView.setVisibility(View.GONE);
        noData_tv.setVisibility(View.VISIBLE);

        return rootView;
    }


   @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
            onResume();
    }



    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
            return;
        Notifications_Create_Activity mainActivity = (Notifications_Create_Activity) getActivity();
        mainActivity.fab.show();
        mainActivity.fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isSelected)
                {
                    final Dialog delete_decision_dialog = new Dialog(getActivity());
                    delete_decision_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    delete_decision_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    delete_decision_dialog.setCancelable(false);
                    delete_decision_dialog.setCanceledOnTouchOutside(false);
                    delete_decision_dialog.setContentView(R.layout.decision_dialog);
                    TextView description_textview = (TextView) delete_decision_dialog.findViewById(R.id.description_textview);
                    description_textview.setText("Do you want to delete the selected task?");
                    Button ok_button = (Button) delete_decision_dialog.findViewById(R.id.yes_button);
                    Button cancel_button = (Button) delete_decision_dialog.findViewById(R.id.no_button);
                    delete_decision_dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            ArrayList<Task_Object> rmoop = new ArrayList<>();
                            rmoop.addAll(task_list);

                            for (Task_Object oo : rmoop)
                            {
                                if (oo.isSelected())
                                {
                                    task_list.remove(oo);
                                }
                                else
                                {
                                    oo.setSelected(false);
                                }
                            }

                            nca.animateFab(false);
                            isSelected = false;

                            if (task_list.size() > 0)
                            {
                                task_adapter = new TASK_ADAPTER(getActivity(),task_list);
                                recyclerView.setAdapter(task_adapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                noData_tv.setVisibility(View.GONE);
                            }
                            else
                            {
                                recyclerView.setVisibility(View.GONE);
                                noData_tv.setVisibility(View.VISIBLE);
                            }
                            delete_decision_dialog.dismiss();
                        }
                    });
                    cancel_button.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            delete_decision_dialog.dismiss();
                        }
                    });
                }
                else
                {
                    Intent intent = new Intent(getActivity(), Notifications_Tasks_Add_Activity.class);
                    intent.putExtra("request_id", Integer.toString(add_task_type));
                    startActivityForResult(intent, add_task_type);
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && !data.equals(""))
        {
            if(requestCode == add_task_type)
            {
                taskcodegroup_id = data.getStringExtra("taskcodegroup_id");
                taskcodegroup_text = data.getStringExtra("taskcodegroup_text");
                taskcode_id = data.getStringExtra("taskcode_id");
                taskcode_text = data.getStringExtra("taskcode_text");
                task_text = data.getStringExtra("task_text");
                taskprocessor_id = data.getStringExtra("taskprocessor_id");
                taskprocessor_text = data.getStringExtra("taskprocessor_text");
                task_responsible = data.getStringExtra("task_responsible");
                planned_st_date =  data.getStringExtra("planned_st_date");
                planned_st_date_formatted =  data.getStringExtra("planned_st_date_formatted");
                planned_st_time = data.getStringExtra("planned_st_time");
                planned_st_time_formatted = data.getStringExtra("planned_st_time_formatted");
                planned_end_date = data.getStringExtra("planned_end_date");
                planned_end_date_formatted = data.getStringExtra("planned_end_date_formatted");
                planned_end_time = data.getStringExtra("planned_end_time");
                planned_end_time_formatted = data.getStringExtra("planned_end_time_formatted");
                release_status = data.getStringExtra("release_status");
                completed_status = data.getStringExtra("completed_status");
                success_status = data.getStringExtra("success_status");
                completion_date = data.getStringExtra("completion_date");
                completion_date_formatted = data.getStringExtra("completion_date_formatted");
                completion_time = data.getStringExtra("completion_time");
                completion_time_formatted = data.getStringExtra("completion_time_formatted");
                completedby = data.getStringExtra("completedby");
                selected_tasks_custom_info_arraylist = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("selected_tasks_custom_info_arraylist");
                String itemkey = data.getStringExtra("task_itemkey");
                String status = data.getStringExtra("status");
                if (itemkey != null && !itemkey.equals(""))
                {
                    if(status.equalsIgnoreCase("U"))
                    {
                        selected_status = "U";
                        item_key = data.getStringExtra("task_itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        task_list.remove(selected_position);
                    }
                    else
                    {
                        selected_status = "I";
                        item_key = data.getStringExtra("task_itemkey");
                        String pos = data.getStringExtra("position");
                        selected_position = Integer.parseInt(pos);
                        selected_pos = pos;
                        task_list.remove(selected_position);
                    }
                }
                else
                {
                    if (task_list.size() > 0)
                    {
                        for (Task_Object bean : task_list)
                        {
                            cc_list.add(bean.getItem_key());
                        }
                        String max_id = Collections.max(cc_list).toString();
                        int last_num = Integer.parseInt(max_id);
                        int new_num = last_num + 1;
                        String new_item_number = "";
                        if(new_num >= 10)
                        {
                            new_item_number = "00"+new_num;
                        }
                        else
                        {
                            new_item_number = "000"+new_num;
                        }
                        item_key = new_item_number;
                    }
                    else
                    {
                        item_key = "0001";
                    }
                    selected_status = "I";
                }
                new Get_Added_Task_Data().execute();
            }
        }
    }


    private class Get_Added_Task_Data extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                if (selected_pos != null && !selected_pos.equals(""))
                {
                    Task_Object to = new Task_Object(item_key, taskcodegroup_id, taskcodegroup_text, taskcode_id, taskcode_text, task_text, taskprocessor_id, taskprocessor_text, task_responsible, planned_st_date, planned_st_date_formatted, planned_st_time, planned_st_time_formatted, planned_end_date, planned_end_date_formatted, planned_end_time, planned_end_time_formatted, release_status, completed_status, success_status, completion_date, completion_date_formatted, completion_time, completion_time_formatted, completedby, selected_tasks_custom_info_arraylist, false);
                    task_list.add(selected_position,to);
                }
                else
                {
                    Task_Object to = new Task_Object(item_key, taskcodegroup_id, taskcodegroup_text, taskcode_id, taskcode_text, task_text, taskprocessor_id, taskprocessor_text, task_responsible, planned_st_date, planned_st_date_formatted, planned_st_time, planned_st_time_formatted, planned_end_date, planned_end_date_formatted, planned_end_time, planned_end_time_formatted, release_status, completed_status, success_status, completion_date, completion_date_formatted, completion_time, completion_time_formatted, completedby, selected_tasks_custom_info_arraylist, false);
                    task_list.add(to);
                }
            }
            catch (Exception e)
            {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (task_list.size() > 0)
            {
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                task_adapter = new TASK_ADAPTER(getActivity(),task_list);
                recyclerView.setAdapter(task_adapter);
                recyclerView.setVisibility(View.VISIBLE);
                noData_tv.setVisibility(View.GONE);
            }
            else
            {
                recyclerView.setVisibility(View.GONE);
                noData_tv.setVisibility(View.VISIBLE);
            }
        }
    }



    public class Task_Object
    {
        private String item_key = "", completedby = "", completion_time_formatted = "", completion_time = "", completion_date_formatted = "", completion_date = "", planned_end_time_formatted = "", planned_st_time_formatted = "", success_status = "", completed_status = "", release_status = "", planned_end_time = "", planned_end_date_formatted = "", planned_end_date = "", planned_st_time = "", planned_st_date_formatted = "", planned_st_date = "", task_responsible = "", taskprocessor_text = "", taskprocessor_id = "", task_text = "", taskcodegroup_id = "", taskcodegroup_text ="", taskcode_id ="", taskcode_text = "";
        ArrayList<HashMap<String, String>> selected_tasks_custom_info_arraylist;
        public boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getItem_key() {
            return item_key;
        }

        public void setItem_key(String item_key) {
            this.item_key = item_key;
        }

        public String getCompletedby() {
            return completedby;
        }

        public void setCompletedby(String completedby) {
            this.completedby = completedby;
        }

        public String getCompletion_time_formatted() {
            return completion_time_formatted;
        }

        public void setCompletion_time_formatted(String completion_time_formatted) {
            this.completion_time_formatted = completion_time_formatted;
        }

        public String getCompletion_time() {
            return completion_time;
        }

        public void setCompletion_time(String completion_time) {
            this.completion_time = completion_time;
        }

        public String getCompletion_date_formatted() {
            return completion_date_formatted;
        }

        public void setCompletion_date_formatted(String completion_date_formatted) {
            this.completion_date_formatted = completion_date_formatted;
        }

        public String getCompletion_date() {
            return completion_date;
        }

        public void setCompletion_date(String completion_date) {
            this.completion_date = completion_date;
        }

        public String getPlanned_end_time_formatted() {
            return planned_end_time_formatted;
        }

        public void setPlanned_end_time_formatted(String planned_end_time_formatted) {
            this.planned_end_time_formatted = planned_end_time_formatted;
        }

        public String getPlanned_st_time_formatted() {
            return planned_st_time_formatted;
        }

        public void setPlanned_st_time_formatted(String planned_st_time_formatted) {
            this.planned_st_time_formatted = planned_st_time_formatted;
        }

        public String getSuccess_status() {
            return success_status;
        }

        public void setSuccess_status(String success_status) {
            this.success_status = success_status;
        }

        public String getCompleted_status() {
            return completed_status;
        }

        public void setCompleted_status(String completed_status) {
            this.completed_status = completed_status;
        }

        public String getRelease_status() {
            return release_status;
        }

        public void setRelease_status(String release_status) {
            this.release_status = release_status;
        }

        public String getPlanned_end_time() {
            return planned_end_time;
        }

        public void setPlanned_end_time(String planned_end_time) {
            this.planned_end_time = planned_end_time;
        }

        public String getPlanned_end_date_formatted() {
            return planned_end_date_formatted;
        }

        public void setPlanned_end_date_formatted(String planned_end_date_formatted) {
            this.planned_end_date_formatted = planned_end_date_formatted;
        }

        public String getPlanned_end_date() {
            return planned_end_date;
        }

        public void setPlanned_end_date(String planned_end_date) {
            this.planned_end_date = planned_end_date;
        }

        public String getPlanned_st_time() {
            return planned_st_time;
        }

        public void setPlanned_st_time(String planned_st_time) {
            this.planned_st_time = planned_st_time;
        }

        public String getPlanned_st_date_formatted() {
            return planned_st_date_formatted;
        }

        public void setPlanned_st_date_formatted(String planned_st_date_formatted) {
            this.planned_st_date_formatted = planned_st_date_formatted;
        }

        public String getPlanned_st_date() {
            return planned_st_date;
        }

        public void setPlanned_st_date(String planned_st_date) {
            this.planned_st_date = planned_st_date;
        }

        public String getTask_responsible() {
            return task_responsible;
        }

        public void setTask_responsible(String task_responsible) {
            this.task_responsible = task_responsible;
        }

        public String getTaskprocessor_text() {
            return taskprocessor_text;
        }

        public void setTaskprocessor_text(String taskprocessor_text) {
            this.taskprocessor_text = taskprocessor_text;
        }

        public String getTaskprocessor_id() {
            return taskprocessor_id;
        }

        public void setTaskprocessor_id(String taskprocessor_id) {
            this.taskprocessor_id = taskprocessor_id;
        }

        public String getTask_text() {
            return task_text;
        }

        public void setTask_text(String task_text) {
            this.task_text = task_text;
        }

        public String getTaskcodegroup_id() {
            return taskcodegroup_id;
        }

        public void setTaskcodegroup_id(String taskcodegroup_id) {
            this.taskcodegroup_id = taskcodegroup_id;
        }

        public String getTaskcodegroup_text() {
            return taskcodegroup_text;
        }

        public void setTaskcodegroup_text(String taskcodegroup_text) {
            this.taskcodegroup_text = taskcodegroup_text;
        }

        public String getTaskcode_id() {
            return taskcode_id;
        }

        public void setTaskcode_id(String taskcode_id) {
            this.taskcode_id = taskcode_id;
        }

        public String getTaskcode_text() {
            return taskcode_text;
        }

        public void setTaskcode_text(String taskcode_text) {
            this.taskcode_text = taskcode_text;
        }

        public ArrayList<HashMap<String, String>> getSelected_tasks_custom_info_arraylist() {
            return selected_tasks_custom_info_arraylist;
        }

        public void setSelected_tasks_custom_info_arraylist(ArrayList<HashMap<String, String>> selected_tasks_custom_info_arraylist) {
            this.selected_tasks_custom_info_arraylist = selected_tasks_custom_info_arraylist;
        }

        public Task_Object(String item_key, String taskcodegroup_id, String taskcodegroup_text, String taskcode_id, String taskcode_text, String task_text, String taskprocessor_id, String taskprocessor_text, String task_responsible, String planned_st_date, String planned_st_date_formatted, String planned_st_time, String planned_st_time_formatted, String planned_end_date, String planned_end_date_formatted, String planned_end_time, String planned_end_time_formatted, String release_status, String completed_status, String success_status, String completion_date, String completion_date_formatted, String completion_time, String completion_time_formatted, String completedby, ArrayList<HashMap<String, String>> selected_tasks_custom_info_arraylist, boolean selected)
        {
            this.item_key = item_key;
            this.taskcodegroup_id = taskcodegroup_id;
            this.taskcodegroup_text = taskcodegroup_text;
            this.taskcode_id = taskcode_id;
            this.taskcode_text = taskcode_text;
            this.task_text = task_text;
            this.taskprocessor_id = taskprocessor_id;
            this.taskprocessor_text = taskprocessor_text;
            this.task_responsible = task_responsible;
            this.planned_st_date = planned_st_date;
            this.planned_st_date_formatted = planned_st_date_formatted;
            this.planned_st_time = planned_st_time;
            this.planned_st_time_formatted = planned_st_time_formatted;
            this.planned_end_date = planned_end_date;
            this.planned_end_date_formatted = planned_end_date_formatted;
            this.planned_end_time = planned_end_time;
            this.planned_end_time_formatted = planned_end_time_formatted;
            this.release_status = release_status;
            this.completed_status = completed_status;
            this.success_status = success_status;
            this.completion_date = completion_date;
            this.completion_date_formatted = completion_date_formatted;
            this.completion_time = completion_time;
            this.completion_time_formatted = completion_time_formatted;
            this.completedby = completedby;
            this.selected_tasks_custom_info_arraylist = selected_tasks_custom_info_arraylist;
            this.selected = selected;
        }

    }



    public class TASK_ADAPTER extends RecyclerView.Adapter<TASK_ADAPTER.MyViewHolder>
    {
        private Context mContext;
        private List<Task_Object> type_details_list;
        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            public TextView planned_start_date_textview, tasks_text_textview, tasks_code_group_textview, tasks_code_textview;
            LinearLayout data_layout;
            RadioButton release_radiobutton, completed_radiobutton, success_radiobutton;
            CheckBox checkbox;
            public MyViewHolder(View view)
            {
                super(view);
                tasks_code_group_textview = (TextView) view.findViewById(R.id.tasks_code_group_textview);
                tasks_code_textview = (TextView) view.findViewById(R.id.tasks_code_textview);
                tasks_text_textview = (TextView) view.findViewById(R.id.tasks_text_textview);
                planned_start_date_textview = (TextView) view.findViewById(R.id.planned_start_date_textview);
                release_radiobutton = (RadioButton)view.findViewById(R.id.release_radiobutton);
                completed_radiobutton = (RadioButton)view.findViewById(R.id.completed_radiobutton);
                success_radiobutton = (RadioButton)view.findViewById(R.id.success_radiobutton);
                checkbox = (CheckBox)view.findViewById(R.id.checkbox);
                data_layout = (LinearLayout)view.findViewById(R.id.data_layout);
            }
        }
        public TASK_ADAPTER(Context mContext, List<Task_Object> list)
        {
            this.mContext = mContext;
            this.type_details_list = list;
        }
        @Override
        public TASK_ADAPTER.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_task_list_data, parent, false);
            return new TASK_ADAPTER.MyViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(final TASK_ADAPTER.MyViewHolder holder, final int position)
        {
            final Task_Object nto = type_details_list.get(position);
            holder.tasks_code_group_textview.setText(nto.getTaskcodegroup_id()+" - "+nto.getTaskcodegroup_text());
            holder.tasks_code_textview.setText(nto.getTaskcode_id()+" - "+nto.getTaskcode_text());
            holder.tasks_text_textview.setText(nto.getTask_text());
            holder.planned_start_date_textview.setText(nto.getPlanned_st_date()+"  "+nto.getPlanned_st_time());
            if(nto.getRelease_status().equalsIgnoreCase("X"))
            {
                holder.release_radiobutton.setChecked(true);
                holder.completed_radiobutton.setChecked(false);
                holder.success_radiobutton.setChecked(false);
            }
            else if(nto.getCompleted_status().equalsIgnoreCase("X"))
            {
                holder.release_radiobutton.setChecked(false);
                holder.completed_radiobutton.setChecked(true);
                holder.success_radiobutton.setChecked(false);
            }
            else if(nto.getSuccess_status().equalsIgnoreCase("X"))
            {
                holder.release_radiobutton.setChecked(false);
                holder.completed_radiobutton.setChecked(false);
                holder.success_radiobutton.setChecked(true);
            }
            else
            {
                holder.release_radiobutton.setChecked(false);
                holder.completed_radiobutton.setChecked(false);
                holder.success_radiobutton.setChecked(false);
            }

            holder.data_layout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), Notifications_Tasks_Add_Activity.class);
                    intent.putExtra("position", Integer.toString(position));
                    intent.putExtra("request_id", Integer.toString(add_task_type));
                    intent.putExtra("item_key", nto.getItem_key());
                    intent.putExtra("taskcodegroup_id", nto.getTaskcodegroup_id());
                    intent.putExtra("taskcodegroup_text", nto.getTaskcodegroup_text());
                    intent.putExtra("taskcode_id", nto.getTaskcode_id());
                    intent.putExtra("taskcode_text", nto.getTaskcode_text());
                    intent.putExtra("task_text", nto.getTask_text());
                    intent.putExtra("taskprocessor_id", nto.getTaskprocessor_id());
                    intent.putExtra("taskprocessor_text", nto.getTaskprocessor_text());
                    intent.putExtra("task_responsible", nto.getTask_responsible());
                    intent.putExtra("planned_st_date", nto.getPlanned_st_date());
                    intent.putExtra("planned_st_date_formatted", nto.getPlanned_st_date_formatted());
                    intent.putExtra("planned_st_time", nto.getPlanned_st_time());
                    intent.putExtra("planned_st_time_formatted", nto.getPlanned_st_time_formatted());
                    intent.putExtra("planned_end_date", nto.getPlanned_end_date());
                    intent.putExtra("planned_end_date_formatted", nto.getPlanned_end_date_formatted());
                    intent.putExtra("planned_end_time", nto.getPlanned_end_time());
                    intent.putExtra("planned_end_time_formatted", nto.getPlanned_end_time_formatted());
                    intent.putExtra("release_status", nto.getRelease_status());
                    intent.putExtra("completed_status", nto.getCompleted_status());
                    intent.putExtra("success_status", nto.getSuccess_status());
                    intent.putExtra("completion_date", nto.getCompletion_date());
                    intent.putExtra("completion_date_formatted", nto.getCompletion_date_formatted());
                    intent.putExtra("completion_time", nto.getCompletion_time());
                    intent.putExtra("completion_time_formatted", nto.getCompletion_time_formatted());
                    intent.putExtra("completedby", nto.getCompletedby());
                    intent.putExtra("status", "I");
                    startActivityForResult(intent, add_task_type);
                }
            });


            holder.checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (holder.checkbox.isChecked())
                    {
                        count = 0;
                        type_details_list.get(position).setSelected(true);
                        for (Task_Object oop : type_details_list)
                        {
                            if (oop.isSelected())
                            {
                                count = count + 1;
                                isSelected = true;
                            }
                        }
                        if (count == 1)
                            nca.animateFab(true);
                    }
                    else
                    {
                        count = 0;
                        type_details_list.get(position).setSelected(false);
                        for (Task_Object oop : type_details_list)
                        {
                            if (oop.isSelected())
                            {
                                count = count + 1;
                            }
                        }
                        if (count == 0)
                        {
                            nca.animateFab(false);
                            isSelected = false;
                        }
                    }
                }
            });

        }
        @Override
        public int getItemCount()
        {
            return type_details_list.size();
        }
    }


    public List<Task_Object> getTaskData()
    {
        return task_list;
    }


}
