package com.caravanas.api.v1;

import com.caravanas.db.Query;
import com.caravanas.util.Json;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/v1/users")
public final class Users extends HttpServlet {
  private static final long serialVersionUID = 0L;

  public Users() {
    super();
  }
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType(Json.CONTENT_TYPE);

    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    Json json = new Json();

    try {
      ResultSet resultSet = new Query("SELECT * FROM Users").execute();
      
      json.put("success", "true");

      while (resultSet.next()) {
        json.put("payload", resultSet.getString("name"));
      }
    } catch (Exception exception) {
      json.put("success", "false");

      json.put("payload", "");

      json.put("error", exception.getMessage());
    } finally {
      response.getWriter().print(json);

      response.getWriter().flush();
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType(Json.CONTENT_TYPE);

    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    Json json = new Json();

    try {
      String name = request.getHeader("name");

      if (name == null || name.isEmpty()) {
        throw new Exception("You must send a name in the request.");
      }

      Query query = new Query("INSERT Users (name) VALUES (?)");

      query.setString(1, name);

      query.update();

      json.put("success", "true");

      json.put("payload", name);
    } catch (Exception exception) {
      json.put("error", exception.getMessage());
    } finally {
      response.getWriter().print(json);

      response.getWriter().flush();
    }
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType(Json.CONTENT_TYPE);

    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    Json json = new Json();

    try {
      String name = request.getHeader("name");

      if (name == null || name.isEmpty()) {
        throw new Exception("You must send a name in the request.");
      }

      String id = request.getHeader("id");

      if (id == null || id.isEmpty()) {
        throw new Exception("You must send an id in the request.");
      }

      Query query = new Query("UPDATE Users SET name = ? WHERE id = ?");

      query.setString(1, name);

      query.setString(2, id);

      query.update();

      json.put("success", "true");

      json.put("payload", name);
    } catch (Exception exception) {
      json.put("error", exception.getMessage());
    } finally {
      response.getWriter().print(json);

      response.getWriter().flush();
    }
  }
}
