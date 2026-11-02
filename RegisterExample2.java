import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import lesson1.Student;

/**
 * Lesson 1 - Example 2: Student registration using an HTML form and a Java class.
 * 
 * The servlet reads POST parameters, validates them and creates a Student object
 * only when all three fields are valid.
 */
public class RegisterExample2 extends HttpServlet {

  private static final long serialVersionUID = -6191018103935410558L;

  /**
   * Handles HTTP POST requests (the form in example2.html uses method="POST").
   *
   * @param request  contains the data submitted by the browser
   * @param response used to send the generated HTML page back to the browser
   *
   * @throws IOException      if an input or output error is detected when the
   *                          servlet handles the POST request
   * @throws ServletException if the request for the POST could not be handled
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    // Tell the browser that the response contains HTML encoded in UTF-8.
    response.setContentType("text/html; charset=UTF-8");
    // PrintWriter writes the HTML response to the client (browser).
    PrintWriter out = new PrintWriter(response.getWriter(), true);

    // Read form parameters. Each name must match the corresponding HTML input's "name".
    // getParameter() returns a String, or null if the parameter is missing.
    String name = request.getParameter("firstname");
    String surname = request.getParameter("lastname");
    String am = request.getParameter("am");
    
    // Count how many fields fail validation (0 means no validation errors).
    int counter = 0;    

    try {
      // Build the HTML response dynamically using Java.
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Example 2: Student Registration</title>");
      out.println("    <meta charset='UTF-8'>");
      out.println("  </head>");
      out.println("  <body>");

      out.println("  <h1>Example2: Reading form data</h1>");      
      out.println("  <ul>");

      // Validate first name: must exist and contain at least 2 non-surrounding-space characters.      
      if (name == null || name.trim().length() < 2) {
          out.println("  <li><b>Firstname</b> is not valid</li>");
          counter++;
      }

       // Validate last name using the same rule.
      if (surname == null || surname.trim().length() < 2) {
          out.println("  <li><b>Last name</b> is not valid</li>");
          counter++;
      }

      // Validate AM (student registration number): exactly 7 characters after trim().
      // Note: this checks only the length, not whether every character is a digit.
      if (am == null || am.trim().length() != 7) {
          out.println("  <li><b>AM: </b> is not valid</li>");
          counter++;
      }
      
      // Create a Student only if all checks passed.
      if (counter == 0) {
        Student student = new Student(am, name, surname);
        out.println("<p>Student with <b>" + student.toString() + "</b> Registered successfully!</p>");
      }
      
      
      out.println("    </body>");
      out.println(" </html>");      

    } catch (Exception e) {
      out.println("Exception: " + e.getMessage());
      out.println("</body>");
      out.println("</html>");
    }
  } // End of doPost

  /**
   * Handles HTTP GET requests.
   * Here we reuse the POST handler for demonstration purposes.
   *
   * @param request  the request object
   * @param response the response object
   *
   * @throws IOException if an input or output error is detected when the
   *                     servlet handles the GET request
   * @throws ServletException if the request for the GET could not be handled
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

          doPost(request, response);

  } // End of doGet

}// End of class