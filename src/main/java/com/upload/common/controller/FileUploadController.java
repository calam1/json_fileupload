package com.upload.common.controller;

import com.upload.common.messaging.Sender;
import com.upload.common.model.FileUpload;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class FileUploadController extends SimpleFormController {


  private Sender sender;

  public FileUploadController() {
    setCommandClass(FileUpload.class);
    setCommandName("fileUploadForm");
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request,
                                  HttpServletResponse response, Object command, BindException errors)
          throws Exception {

    FileUpload file = (FileUpload) command;

    MultipartFile multipartFile = file.getFile();

    String fileName = "";

    if (multipartFile != null) {
      fileName = multipartFile.getOriginalFilename();

      InputStream is = multipartFile.getInputStream();

      //jackson 2
      byte[] bytes = IOUtils.toByteArray(is);
      String input = new String(bytes);

      JsonFactory f = new MappingJsonFactory();
      JsonParser jp = f.createJsonParser(input);

      while(jp.nextToken() != null) {
        if("records".equals(jp.getCurrentName())) {
          System.out.println("Records found");
          if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            while(jp.nextToken() != JsonToken.END_ARRAY) {
              if(jp.getCurrentToken() == JsonToken.START_OBJECT) {
                sender.sendMessage(jp.readValueAsTree().toString());
              }
            }
          }
        }
      }
    }
      return new ModelAndView("FileUploadSuccess", "fileName", fileName);
    }

  public Sender getSender() {
    return sender;
  }

  public void setSender(Sender sender) {
    this.sender = sender;
  }
}
