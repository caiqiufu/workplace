<%@ page import="com.unieap.file.ajaxupload.MonitoredDiskFileItemFactory" %>
<%@ page import="com.unieap.file.ajaxupload.UploadListener" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.FileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
--%>
<%
    UploadListener listener = new UploadListener(request, 30);

    // Create a factory for disk-based file items
    FileItemFactory factory = new MonitoredDiskFileItemFactory(listener);

    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);

    try
    {
        // process uploads ..
        upload.parseRequest(request);
    }
    catch (FileUploadException e)
    {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
%>
<html>
<head><title>Done</title></head>
<body>
Done
</body>
</html>