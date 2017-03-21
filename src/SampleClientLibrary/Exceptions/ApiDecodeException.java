/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SampleClientLibrary.Exceptions;

/**
 *
 * @author ranjdar.abass
 */
public class ApiDecodeException extends Exception {

    public ApiDecodeException() {
        super("Failed reading from API");
    }
    public ApiDecodeException(String message) {
        super("Failed reading from API: " + message);
    }

    public ApiDecodeException(Exception innerException) {
        super("Failed reading from API", innerException);
    }
}
