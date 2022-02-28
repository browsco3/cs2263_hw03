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

public class Course {
    // Instance Variables
    private String department = "";
    private String deptCode = "";
    private String courseNum = "";
    private String courseName = "";
    private String courseCreds = "";

    /* The departments are:
        Department             || DeptCode
        ----------------------------------------
        Computer Science            CS
        Mathematics                 MATH
        Chemistry                   CHEM
        Physics                     PHYS
        Biology                     BIOL
        Electrical Engineering      EE
     */

    Course(String dept, String num, String name, String cred) {
        department = dept;
        System.out.println(dept);
        switch (dept) {
            case "Computer Science":
                deptCode = "CS";
                break;
            case "Mathematics":
                deptCode = "MATH";
                break;
            case "Chemistry":
                deptCode = "CHEM";
                break;
            case "Physics":
                deptCode = "PHYS";
                break;
            case "Biology":
                deptCode = "BIOL";
                break;
            case "Electrical Engineering":
                deptCode = "EE";
                break;
            default:
                deptCode = "";
        }
        courseNum = num;
        courseName = name; 
        courseCreds = cred;
    }

    // Methods

    public String getDept() { return this.department;}
    public String getDeptCode() { return this.deptCode;}
    public String getNum() { return this.courseNum;}
    public String getName() { return this.courseName;}
    public String getCred() { return this.courseCreds;}
}
