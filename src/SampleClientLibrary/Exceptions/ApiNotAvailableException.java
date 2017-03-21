/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleClientLibrary.Exceptions;

/**
 *
 * @author ranjdar.abass
 */
public class ApiNotAvailableException extends Exception {

    public ApiNotAvailableException() {
        super("Failed accessing API");
    }

    public ApiNotAvailableException(Exception innerException) {
        super("Failed accessing API", innerException);
    }
}
