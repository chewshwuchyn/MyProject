package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 10/2/2016.
 */

public class Task {

        private String taskName, taskStartDate, taskEndDate;
   //     private int totalTask, noOfUncompleteTask;
        private int taskID;

        public Task(String taskName, String taskStartDate, String taskEndDate, int taskID) {
            this.setTaskName(taskName);
            this.setTaskStartDate(taskStartDate);
            this.setTaskEndDate(taskEndDate);
            this.setTaskID(taskID);

        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskStartDate() {
            return taskStartDate;
        }

        public void setTaskStartDate(String taskStartDate) {
            this.taskStartDate = taskStartDate;
        }

        public String getTaskEndDate() {
            return taskEndDate;
        }

        public void setTaskEndDate(String taskEndDate) {
            this.taskEndDate = taskEndDate;
        }


 //       public int getTotalTask() {
 //           completion = Math.round(((double)getCurMilestone() / (double)getNoOfMilestone()) * 100);
 //           return completion;
 //       }

//        public void setCompletion(double completion) {
//            this.completion = completion;
 //       }

        public int getTaskID() {
            return taskID;
        }

        public void setTaskID(int taskID) {
            this.taskID = taskID;
        }
    }


