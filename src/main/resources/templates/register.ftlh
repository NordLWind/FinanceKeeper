<#import "/spring.ftl" as Spring>
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
        <form action="/register" method="post">
            <div class="form-outline mb-4">
                <@Spring.formInput "form.name" "class=\"form-control\" id=\"form2Example0\"" "text"/>
                <label class="form-label" for="form2Example0">Имя</label>
            </div>
            <div class="form-outline mb-4">
                <@Spring.formInput "form.email" "class=\"form-control\" id=\"form2Example1\"" "email"/>
                <@Spring.showErrors "<br>"/>
                <label class="form-label" for="form2Example1">Электронная почта</label>
            </div>
            <div class="form-outline mb-4">
                <@Spring.formInput "form.password" "class=\"form-control\" id=\"form2Example2\"" "password"/>
                <label class="form-label" for="form2Example2">Пароль</label>
            </div>
            <button type="submit" class="btn btn-primary btn-block mb-4">Зарегистрироваться</button>
            <div class="text-center">
                <p>Уже заполняли это все? <a href="/login">Войдите!</a></p>
            </div>
        </form></div>
    <div class="col-md-4">.col-md-3</div>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</body>
</html>