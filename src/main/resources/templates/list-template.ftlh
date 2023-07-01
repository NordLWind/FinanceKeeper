<#import "/spring.ftl" as spring/>
<!DOCTYPE HTML>
<html lang="ru">
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Меню</title>
</head>
<body>
<header>
    <h1 align="center">Авторизация</h1>
    <br>
</header>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <#list options as o>
            <li class="list-group-item">
                <br>
                ${o}
                <br>
            </li>
        </#list>
        <div class="btn-group">
            <button class="btn btn-primary btn-lg" role="button" type="button" data-toggle="dropdown"
                    aria-expanded="false">
                <a href="/index" class="list-group-item list-group-item-action">Назад</a>
            </button>
        </div>
    </div>
    <div class="col-md-4"></div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>