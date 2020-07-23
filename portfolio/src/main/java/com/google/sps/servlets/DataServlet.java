// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//   
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.Constants;
import com.google.sps.data.commentobject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. */
@WebServlet("/comments")
public class DataServlet extends HttpServlet 
{

   private DatastoreService datastore;

  @Override
  public void init()
  {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
  {

    Query query = new Query(Constants.COMMENTOBJECT).addSort(Constants.TIMESTAMP, SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);

    List<commentobject> comment_list= new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String comment = (String) entity.getProperty(Constants.COMMENT);
      long timestamp = (long) entity.getProperty(Constants.TIMESTAMP);
      commentobject com_ = new commentobject(id, comment, timestamp);
      comment_list.add(com_);
    }
    //converting to GSON
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comment_list));
  }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
{
    String comment = request.getParameter(Constants.COMMENT);
    long timestamp = System.currentTimeMillis();
    Entity commentEntity = new Entity(Constants.COMMENTOBJECT);
    commentEntity.setProperty(Constants.COMMENT, comment);
    commentEntity.setProperty(Constants.TIMESTAMP, timestamp);
      //storing the comments
    datastore.put(commentEntity);

   //sending response back to page
    response.sendRedirect("/");
  }

}