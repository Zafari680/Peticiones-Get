

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ScholarController")
public class ScholarController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String scholarApiUrl = "https://scholar.google.com/citations?hl=en&view_op=search_authors&mauthors=";

        try {
            query = URLEncoder.encode(query, StandardCharsets.UTF_8);
            scholarApiUrl += query;
            request.setAttribute("scholarApiUrl", scholarApiUrl);
            request.getRequestDispatcher("ScholarView.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}



import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ScholarModel {
    public String fetchScholarProfile(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Error while fetching profile");
        }
    }
}


// View (ScholarView.jsp)
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Google Scholar Author Profile</title>
</head>
<body>
    <h1>Google Scholar Author Profile</h1>
    <a href="${scholarApiUrl}">View Google Scholar Author Profile</a>
</body>
</html>