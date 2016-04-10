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
<html>
    <head>
        <title>Simple upload page</title>
        <script src='resources/js/upload.js'> </script>
        <script src='dwr/interface/UploadMonitor.js'> </script>
        <script src='dwr/engine.js'> </script>
        <script src='dwr/util.js'> </script>
        <style type="text/css">
            body { font: 11px Lucida Grande, Verdana, Arial, Helvetica, sans serif; }
            #progressBar { padding-top: 5px; }
            #progressBarBox { width: 350px; height: 20px; border: 1px inset; background: #eee;}
            #progressBarBoxContent { width: 0; height: 20px; border-right: 1px solid #444; background: #9ACB34; }
        </style>
    </head>
<body>

<form action="upload.jsp" enctype="multipart/form-data" method="post" onsubmit="startProgress()">
    <p><h1>Web upload</h1>

    <p>
        <input class="default" type="file" id="file1" name="file1"/><br/>
        <input class="default" type="file" id="file2" name="file2"/><br/>
        <input class="default" type="file" id="file3" name="file3"/><br/>
        <input class="default" type="file" id="file4" name="file4"/><br/>
        <input type="submit" value="begin upload" id="uploadbutton"/>

        <br/>

        <div id="progressBar" style="display: none;">

            <div id="theMeter">
                <div id="progressBarText"></div>

                <div id="progressBarBox">
                    <div id="progressBarBoxContent"></div>
                </div>
            </div>
        </div>

</form>
</body>
</html>