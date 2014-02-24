json_fileupload
===============

This is a sample of a file upload java app that provides a simple GUI to choose a file to upload.  The expected file format is JSON.  The app breaks the JSON down by elements in the array as we stream and sends a TestMessage representation of the individual items.  The listener is in the same app and just prints the message that was received.  We use ActiveMQ as our embedded messaging service.

To build you must have maven installed.  Just run mvn clean install

The url for the upload page is:  http://localhost:8080/SpringMVC/fileupload.htm
