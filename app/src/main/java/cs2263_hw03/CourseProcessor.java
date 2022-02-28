/**
 * MIT License
 *
 * Copyright (c) 2022 browsco3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package cs2263_hw03;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import java.io.*;

import java.awt.event.TextEvent;
import java.util.ArrayList;

import com.google.gson.Gson;

public class CourseProcessor extends Application {

    private ArrayList<Course> courseList = new ArrayList<>();
    private GridPane courseBox;
    private String selectedDept;
    private String filepath_save;
    private String filepath_load;
    Gson jason = new Gson();

    @Override
    public void start(Stage stage) throws Exception{
        // Set the title for the window
        stage.setTitle("Course Planner");

        BorderPane border = new BorderPane();
    
        border.setTop(addTop());
        border.setCenter(addCenter());
        border.setBottom(addBottom());

        // // Settings for the styles
        // submitBut.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        // exitBut.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        
        // grid.setStyle("-fx-background-color: BEIGE;");

        Scene scene = new Scene(border);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ESCAPE:
                        stage.close();
                    default: 
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public HBox addTop() throws Exception {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 5, 15, 5)); // Top, Right, Bottom, Left
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        hbox.setAlignment(Pos.CENTER);

        // Creating my objects
        Text deptCombo = new Text("Department");
        Text textNum = new Text("Number");
        Text textName = new Text("Name");
        Text textCreds = new Text("Credits");

        ComboBox<String> deptOptions = new ComboBox<>();
        deptOptions.getItems().addAll("Computer Science",
        "Mathematics",
        "Chemistry",
        "Physics",
        "Biology",
        "Electrical Engineering");;
        deptOptions.setOnAction((ActionEvent updated) -> {
            if (deptOptions.getValue() != null) {
                selectedDept = deptOptions.getValue().toString();
            }
        });
        TextField courseNumber = new TextField();
        TextField courseName = new TextField();
        TextField courseCreds = new TextField();

        Button addBut = new Button("Add Course");
        addBut.setOnAction((ActionEvent addEvent) -> { 
            String selectedDept = deptOptions.getValue();
            String selectedNum = courseNumber.getText();
            String selectedName = courseName.getText();
            String selectedCred = courseCreds.getText();

            if (selectedDept != null && !selectedNum.isEmpty() && !selectedName.isEmpty() && !selectedCred.isEmpty()) {
                addCourse(selectedDept.toString(), selectedNum, selectedName, selectedCred);
                courseNumber.clear();;
                courseName.clear();;
                courseCreds.clear();
            }
            else {
                alert("One or more of your fields is empty!");
            }
         });

        deptCombo.setStyle("-fx-font: normal bold 12px 'sansserif' ");
        textNum.setStyle("-fx-font: normal bold 12px 'sansserif' ");
        textName.setStyle("-fx-font: normal bold 12px 'sansserif' ");
        textCreds.setStyle("-fx-font: normal bold 12px 'sansserif' ");

        // Set sizes
        addBut.setPrefSize(100, 20);
        courseNumber.setPrefSize(60, 20);
        courseName.setPrefSize(200, 20);
        courseCreds.setPrefSize(30, 20);

        hbox.getChildren().addAll(
            deptCombo, deptOptions, textNum, courseNumber, textName, courseName, textCreds, courseCreds, addBut  
            );

        return hbox;
    }

    public StackPane addCenter() {
        StackPane stack = new StackPane();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 50, 10, 50));
        //vbox.setSpacing(8);
    
        Text title = new Text("Planned Courses:");
        Text dept = new Text("Department");
        Text num = new Text("Number");
        Text name = new Text("Name");
        Text cred = new Text("Credits");
        GridPane headerBox = new GridPane();
        courseBox = new GridPane();
        StackPane deptPane = new StackPane(dept);
        StackPane numPane = new StackPane(num);
        StackPane namePane = new StackPane(name);
        StackPane credPane = new StackPane(cred);

        headerBox.setStyle("-fx-background-color: #889; -fx-border-wisth: 5px; -fx-border-color: #00C; -fx-background-insets: 0, 1 ;");
        headerBox.setPrefSize(700, 30);
        headerBox.setAlignment(Pos.CENTER);
        deptPane.setAlignment(Pos.BASELINE_LEFT);
        numPane.setAlignment(Pos.BASELINE_LEFT);
        namePane.setAlignment(Pos.BASELINE_LEFT);
        credPane.setAlignment(Pos.BASELINE_LEFT);
        ColumnConstraints deptConstraints = new ColumnConstraints();
        ColumnConstraints numConstraints = new ColumnConstraints();
        ColumnConstraints nameConstraints = new ColumnConstraints();
        ColumnConstraints credConstraints = new ColumnConstraints();

        deptConstraints.setPercentWidth(25);
        numConstraints.setPercentWidth(15);
        nameConstraints.setPercentWidth(50);
        credConstraints.setPercentWidth(10);
        headerBox.getColumnConstraints().addAll(deptConstraints, numConstraints, nameConstraints, credConstraints);
        courseBox.getColumnConstraints().addAll(deptConstraints, numConstraints, nameConstraints, credConstraints);
        
        courseBox.setStyle("-fx-background-color: #CCF; -fx-border-width: 0,0,1,0; -fx-border-color: #CCF;");
        courseBox.setPrefSize(700, 400);
        title.setStyle("-fx-font: normal bold 14px 'sanserif' ");
        deptPane.setPadding(new Insets(10, 5, 10, 5));
        numPane.setPadding(new Insets(10, 5, 10, 5));
        namePane.setPadding(new Insets(10, 5, 10, 5));
        credPane.setPadding(new Insets(10, 5, 10, 5));
        deptPane.setStyle("-fx-background-fill: black, white ; -fx-border-color: #AAF; -fx-border-width-right: 3px; -fx-font: normal bold 14px 'sansserif'");
        numPane.setStyle("-fx-background-fill: black, white ; -fx-border-color: #AAF; -fx-border-width-right: 3px; -fx-font: normal bold 14px 'sansserif'");
        namePane.setStyle("-fx-background-fill: black, white ; -fx-border-color: #AAF; -fx-border-width-right: 3px; -fx-font: normal bold 14px 'sansserif'");
        credPane.setStyle("-fx-background-fill: black, white ; -fx-border-color: #AAF; -fx-border-width-right: 3px; -fx-font: normal bold 14px 'sansserif'");

        headerBox.add(deptPane, 0, 0);
        headerBox.add(numPane, 1, 0);
        headerBox.add(namePane, 2, 0);
        headerBox.add(credPane, 3, 0);

        vbox.getChildren().addAll(title, headerBox, courseBox);
        stack.getChildren().addAll(vbox);

        return stack;
    }

    public HBox addBottom() {
        HBox hbox = new HBox();
        
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.BASELINE_RIGHT);
        hbox.setStyle("-fx-background-color: #336699;");

        Button displayAllBut = new Button("Display (all)");

        displayAllBut.setOnAction((ActionEvent dispAllEvent) -> {
            displayCourse("all");           
        });

        Button displayDeptBut = new Button("Display (dept.)");

        displayDeptBut.setOnAction((ActionEvent dispDeptEvent) -> {
            displayCourse(selectedDept);
        });
        
        Button saveBut = new Button("Save File");

        saveBut.setOnAction((ActionEvent saveEvent) -> {
            confirmSave();           
        });

        Button loadBut = new Button("Load File");

        loadBut.setOnAction((ActionEvent loadEvent) -> {
            confirmLoad();
        });

        Button exitBut = new Button("Exit");

        exitBut.setOnAction((ActionEvent event) -> { 
            confirmExit();;
         });

        hbox.getChildren().addAll(displayAllBut, displayDeptBut, saveBut, loadBut, exitBut);

        return hbox;
    }   

    public void confirmSave() {
        Stage save = new Stage();
        save.setTitle("Save File");
        GridPane grid = new GridPane();
        HBox hBox = new HBox();

        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setMinSize(300, 100);
        grid.setStyle("-fx-background-color: #EEE;");
        grid.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5, 0, 5, 0));

        Text textName = new Text("Enter the name of a file to save to:");
        TextField filename = new TextField("output.json");
        Button submit = new Button("Save");
        submit.setOnAction((ActionEvent saveEvent) -> { 
            filepath_save = filename.getText();
            saveData();
            save.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction((ActionEvent cancelEvent) -> { save.close();});

        hBox.getChildren().addAll(submit, cancel);

        grid.add(textName, 0, 0);
        grid.add(filename, 1, 0);
        grid.add(hBox, 1, 1);
        

        Scene saveScene = new Scene(grid);
        save.setScene(saveScene);
        save.show();
    }

    public void confirmLoad() {
        Stage load = new Stage();
        load.setTitle("Load File");
        GridPane grid = new GridPane();
        HBox hBox = new HBox();

        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setMinSize(300, 100);
        grid.setStyle("-fx-background-color: #EEE;");
        grid.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5, 0, 5, 0));

        Text textName = new Text("Enter the name of a file to load from:");
        TextField filename = new TextField("output.json");
        Button submit = new Button("Load");
        submit.setOnAction((ActionEvent loadEvent) -> { 
            filepath_load = filename.getText();
            loadData();
            load.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction((ActionEvent cancelEvent) -> { load.close();});

        hBox.getChildren().addAll(submit, cancel);

        grid.add(textName, 0, 0);
        grid.add(filename, 1, 0);
        grid.add(hBox, 1, 1);
        

        Scene loadScene = new Scene(grid);
        load.setScene(loadScene);
        load.show();
    }

    public void confirmExit() {
        Stage confirmClose = new Stage();
            confirmClose.setTitle("Exit?");
            VBox exitBox = new VBox();

            exitBox.setPrefSize(150, 100);
            exitBox.setStyle("-fx-background-color: #CCC;");
            exitBox.setPadding(new Insets(10, 10, 10, 10));;
            exitBox.setSpacing(10);
            exitBox.setAlignment(Pos.CENTER);

            Text confirm = new Text("Are you sure you want to exit?");
            Button exit = new Button("Okay");

            exit.setOnAction((ActionEvent exitEvent) -> {
                Platform.exit();
            });

            Button cancel = new Button("Cancel");

            cancel.setOnAction((ActionEvent cancelEvent) -> {
                confirmClose.close();
            });

            exitBox.getChildren().addAll(confirm, exit, cancel);

            Scene exitScene = new Scene(exitBox);
            confirmClose.setScene(exitScene);
            confirmClose.show();
    }

    public void loadData() {
        String fileLocation = System.getProperty("user.dir") + "/" + filepath_load;
        String json = "";
        String[] unjson;
        ArrayList<Course> inputCourses = new ArrayList<>();
        //ArrayList<Course> inData = new ArrayList<>();
        try {
            FileReader inputFile = new FileReader(fileLocation);
            int ioCode;
            while ((ioCode = inputFile.read()) != -1) {
                json += (char) ioCode;
            }
            inputFile.close();
            System.out.println(json);
            unjson = json.split("}");
            for (String jsonData: unjson) {
                jsonData += "}";
                Course inputData = jason.fromJson(jsonData, Course.class);
                inputCourses.add(inputData);
            }
            courseBox.getChildren().clear();
            for (Course currCourse: inputCourses) {
                addCourse(currCourse.getDept(), currCourse.getNum(), currCourse.getName(), currCourse.getCred());
            }
        }
        catch (IOException ioe) {
            System.out.println("Writing Failed!");
        }

    }

    public void saveData() {
        String json = "";
        for (Course currCourse: courseList) {
            json += jason.toJson(currCourse);
        }
        System.out.println(json);
        String fileLocation = System.getProperty("user.dir") + "/" + filepath_save;

        try {
            FileWriter outputFile = new FileWriter(fileLocation);
            outputFile.write(json);
            outputFile.flush();
            outputFile.close();
        }
        catch (IOException ioe) {
            System.out.println("Writing Failed!");
        }
        
    }

    public void alert(String message) {
        Stage alert = new Stage();
        alert.setTitle("Error!");
        HBox alertBox = new HBox();

        alertBox.setPrefSize(150, 100);
        alertBox.setStyle("-fx-background-color: #CCC;");
        alertBox.setPadding(new Insets(10, 10, 10, 10));;
        alertBox.setSpacing(10);
        alertBox.setAlignment(Pos.CENTER);

        Text textAlert = new Text(message);

        alertBox.getChildren().add(textAlert);

        Scene alertScene = new Scene(alertBox);
        alert.setScene(alertScene);
        alert.show();
    }

    public void addCourse(String dept, String num, String name, String cred) {
        Course course = new Course(dept, num, name, cred);
        courseList.add(course);
        for (int ind = 0; ind < courseList.size(); ind++) {
            Course currCourse = courseList.get(ind);
            Text textDept = new Text(currCourse.getDept());
            Text textNum = new Text(currCourse.getDeptCode() + " " + currCourse.getNum());
            Text textName = new Text(currCourse.getName());
            Text textCred = new Text(currCourse.getCred());
            courseBox.add(textDept, 0, ind);
            courseBox.add(textNum, 1, ind);
            courseBox.add(textName, 2, ind);
            courseBox.add(textCred, 3, ind);
        }
    }

    public void displayCourse(String option) {
        ArrayList<Course> holderList = new ArrayList<>(courseList);
        ArrayList<Course> dupeList = new ArrayList<>(courseList);
        System.out.println("Holder: " + holderList.toString() + "\nCourseList: " + courseList.toString());
        if (option == "all") {
            courseBox.getChildren().clear();
            courseList.clear();
            for (Course allCourse: dupeList) {
                addCourse(allCourse.getDept(), allCourse.getNum(), allCourse.getName(), allCourse.getCred());
            }
        }
        else {
            courseBox.getChildren().clear();
            courseList.clear();
            for (Course filtCourse: holderList) {
                String selDept = filtCourse.getDept();
                if (selDept == option) {
                    
                    addCourse(filtCourse.getDept(), filtCourse.getNum(), filtCourse.getName(), filtCourse.getCred());
                }
            }
            courseList = holderList;
        }
    }

    public static void main(String[] args) {
        ArrayList<Course> courseList = new ArrayList<>();


        // Testing the arraylist with the course class.
//        courseList.add(new Course("Computer Science", "CS"));
//        courseList.add(new Course("Mathematics", "MATH"));
//        courseList.add(new Course("Chemistry", "CHEM"));
//        courseList.add(new Course("Physics", "PHYS"));
//        courseList.add(new Course("Biology", "BIOL"));
//        courseList.add(new Course("Electrical Engineering", "EE"));
//
//        System.out.println(courseList);

        Application.launch();
        
    }
}
