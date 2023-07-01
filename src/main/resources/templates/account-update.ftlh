<#import "/spring.ftl" as spring/>
<!DOCTYPE HTML>
<html lang="ru">
<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Меню</title>
</head>
<body>
<header>
    <h1 align="center">Изменение аккаунта</h1>
    <br>
</header>
<div class="row">
    <div class="col-md-4"></div>
    <div class="col-md-4">
        <form action="/account/update" method="post">
            <div class="form-group">
                <label for="FormControlSelect1">Example select</label>
                <@spring.formSingleSelect "form.account", accounts, "class=\"form-control\" id=\"FormControlSelect1\""/>
            </div>
            <div class="form-outline mb-4">
                <@spring.formSingleSelect "form.param", param, "class=\"form-control\" id=\"form2Example0\""/>
                <label class="form-label" for="form2Example0">Параметр</label>
            </div>
            <div class="form-outline mb-4">
                <@spring.formInput "form.value" "class=\"form-control\" id=\"form2Example1\"" "text"/>
                <@spring.showErrors "<br>"/>
                <label class="form-label" for="form2Example1">Значение</label>
            </div>
            <button type="submit" class="btn btn-primary btn-block mb-4">Изменить</button>
            <div class="text-center">
                <p><a href="/account/menu">Назад</a></p>
            </div>
        </form>
    </div>
</div>
<div class="col-md-4">.col-md-3</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>