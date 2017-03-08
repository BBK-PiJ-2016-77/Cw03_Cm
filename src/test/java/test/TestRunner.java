package test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 * Created by vasileiosdavios on 3/8/17.
 */

/**
 * TestRunner class, runs the tests for all the classes of the project
 */

public class TestRunner {


    public static void main(String[] args){

        Result result = JUnitCore.runClasses(ContactImplTest.class);

        for(Failure failure : result.getFailures()){

            System.out.println(failure.toString());
        }

        System.out.println("ContactImpl "+result.wasSuccessful());
        System.out.println();

        result = JUnitCore.runClasses(MeetingImplTest.class);
        for(Failure failure : result.getFailures()){

            System.out.println(failure.toString());
        }

        System.out.println("MeetingImpl "+result.wasSuccessful());
        System.out.println();
    }
}