import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Lesson 1 - Example 1: Read and validate data submitted by an HTML form.
 * 
 *
 */
public class RegisterExample1 extends HttpServlet {

  private static final long serialVersionUID = -6191018103935410558L;

  /**
   * Handles HTTP GET requests.
   *
   * @param request  the request object
   * @param response the response object
   *
   * @throws IOException      if an input or output error is detected when the
   *                          servlet handles the GET request
   * @throws ServletException if the request for the GET could not be handled
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    // Tell the browser that the response contains HTML encoded in UTF-8.
    response.setContentType("text/html; charset=UTF-8");
    // PrintWriter writes the HTML response sent back to the browser.
    PrintWriter out = new PrintWriter(response.getWriter(), true);

    // Read form data using each field's HTML name attribute (not its id).
    // getParameter() returns a String, or null when the parameter is missing.
    String name = request.getParameter("firstname");
    String surname = request.getParameter("lastname");
    String age = request.getParameter("age");
    String country = request.getParameter("country");
    String gender = request.getParameter("gender");
    // Multiple checked boxes share the name "skills", so read all selected values.
    // getParameterValues() returns a String array, or null if no skill was selected.
    String [] skills = request.getParameterValues("skills");

    try {
      // Generate the HTML response dynamically using out.println().
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("  <head>");
      out.println("    <title>Lesson 1: Example 1</title>");
      out.println("    <meta charset='UTF-8'>");
      out.println("  </head>");
      out.println("  <body>");

      out.println("  <h1>Example1: Reading form data</h1>");
      out.println("  <h2>Personal Data</h2>");
      
      out.println("  <ul>");

      // Validate the first name: check for a missing value and require 2+ characters.
      // trim() removes leading/trailing whitespace before checking the length.
      if (name == null || name.trim().length() < 2) {
        out.println("  <li><b>Firstname: </b> Please enter a valid firstname </li>");
      } else {
        out.println("  <li><b>Firstname: </b> " + name +" </li>");
      }

       // Apply the same basic validation to the last name.
      if (surname == null || surname.trim().length() < 2) {
        out.println("  <li><b>Last name: </b> Please enter a valid Last name </li>");
      } else {
        out.println("  <li><b>Last name: </b> " + surname +" </li>");
      }

      // Form parameters are Strings, even when they represent numbers.
      // Convert age to int and then check whether it is between 18 and 100.
      int ageNumber = 0;

      try {
        ageNumber = Integer.parseInt(age);

        if(ageNumber < 18 || ageNumber > 100) {
          out.println("  <li><b>Age: </b> Please enter a valid age </li>");
        } else {
          out.println("  <li><b>Age: </b> " + age +" </li>");
        }
      } catch (Exception e) {
        // Missing or non-numeric age: display an error instead of failing.
        out.println("  <li><b>Age: </b> Please enter a valid age </li>");
      }

      // The first <option> has value=""
      if (country == null || country.equals("")) {
        out.println("  <li><b>Country: </b> Please select a country </li>");
      } else {
        out.println("  <li><b>Country: </b> " + country +" </li>");
      }

      // Radio buttons share the same name, only one value can be submitted.
      // If none is selected, the parameter is null.
      if (gender == null) {
          out.println("  <li><b>Gender: </b> Please choose gender </li>");
      } else {
        out.println("  <li><b>Gender: </b> " + gender +" </li>");
      }

      out.println("     </ul>");
      out.println("   <h2>Skills</h2>");

      // Check that at least one checkbox was selected.
      if (skills != null && skills.length > 0) {        
        out.println("  <ol>");
        // Iterate over all selected skills and print an HTML list item for each.
        for (String skill : skills) {
          out.println("  <li>" + skill + "</li>");
        }
        out.println("  </ol>");        

      } else {
        out.println("  <p><b>Skills: </b> Please select at least one skill</p>");
      }
      
      out.println("    </body>");
      out.println(" </html>");
      

    } catch (Exception e) {
      out.println("Exception: " + e.getMessage());
      out.println("</body>");
      out.println("</html>");
    }
  } // End of doGet

  /**
   * Handles HTTP POST requests.
   *
   * @param request  the request object
   * @param response the response object
   *
   * @throws IOException      if an input or output error is detected when the
   *                          servlet handles the POST request
   * @throws ServletException if the request for the POST could not be handled
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

          // Reuse the same logic for POST requests (the form currently uses GET).
          doGet(request, response);

  }  // End of doPost

}// End of class