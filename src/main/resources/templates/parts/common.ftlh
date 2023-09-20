<#macro page>
    <!DOCTYPE html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Svacancy</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
              crossorigin="anonymous">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <#--        <script defer src="https://kit.fontawesome.com/f097ef2d59.js" crossorigin="anonymous"></script>-->
        <#--        <script defer src="https://cdnjs.cloudflare.com/ajax/libs/turbolinks/5.2.0/turbolinks.js"></script>-->
    </head>

    <body>
    <#include "navbar.ftlh">
    <div>
        <#nested>
    </div>
    <noscript>
        <h2>Sorry! Your browser doesn't support Javascript</h2>
    </noscript>
    </body>
    <script src="https://kit.fontawesome.com/f097ef2d59.js" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
            crossorigin="anonymous"></script>
    </html>
</#macro>