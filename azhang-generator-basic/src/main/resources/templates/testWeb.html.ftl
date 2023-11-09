<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${author}官网</title>
</head>
<body>
    <h1>欢迎来到${author}官网</h1>
    <#-- 循环渲染 -->
    <ul>
        <#list menuItems as menuItem>
            <li>
                <a href="${menuItem.url}">${menuItem.name}</a>
            </li>
        </#list>
    </ul>
    <footer>
        ${currentYear} <p>版权所有：${author} All Rights Reserved.</p>
    </footer>
</body>
</html>
