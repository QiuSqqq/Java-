# exit2和exit3文件夹命名交换命令详解

在这个任务中，我们需要交换两个文件夹`exit2`和`exit3`的命名，包括文件夹名、文件名和文件内部的包名与类名。以下是使用PowerShell命令实现这一目标的详细过程。

## 1. 创建临时目录

```powershell
mkdir -p src/main/java/com/qius/temp
mkdir -p src/main/java/com/qius/temp/exit3
mkdir -p src/main/java/com/qius/temp/exit2
```

这些命令创建了临时工作区域，用于存放重命名的文件。

## 2. 处理exit2文件夹中的文件并转换为exit3

```powershell
Get-ChildItem -Path src/main/java/com/qius/exit2 -Recurse -Filter *.java | ForEach-Object { 
    $content = Get-Content $_.FullName -Raw; 
    $content = $content -replace "package\s+com\.qius\.exit2", "package com.qius.exit3" 
                       -replace "package\s+exit2", "package exit3" 
                       -replace "exit2_(\d+)", "exit3_`$1"; 
    Set-Content -Path "src/main/java/com/qius/temp/exit3/$($_.Name -replace 'exit2_(\d+)', 'exit3_$1')" -Value $content 
}
```

这个命令是最核心的部分，它：
1. 使用`Get-ChildItem`查找所有Java文件
2. 对每个文件使用`ForEach-Object`进行处理
3. 使用`Get-Content`读取文件内容
4. 使用`-replace`正则表达式进行多次替换:
   - `"package\s+com\.qius\.exit2"`替换为`"package com.qius.exit3"`（处理完整包名）
   - `"package\s+exit2"`替换为`"package exit3"`（处理简化包名）
   - `"exit2_(\d+)"`替换为`"exit3_$1"`（处理类名，保留数字部分）
5. 将处理后的内容写入新位置，同时在文件名中也进行相应替换

## 3. 处理exit3文件夹中的文件并转换为exit2

```powershell
Get-ChildItem -Path src/main/java/com/qius/exit3 -Recurse -Filter *.java | ForEach-Object { 
    $content = Get-Content $_.FullName -Raw; 
    $content = $content -replace "package\s+com\.qius\.exit3", "package com.qius.exit2" 
                       -replace "package\s+exit3", "package exit2" 
                       -replace "exit3_(\d+)", "exit2_`$1"; 
    Set-Content -Path "src/main/java/com/qius/temp/exit2/$($_.Name -replace 'exit3_(\d+)', 'exit2_$1')" -Value $content 
}
```

这个命令与上一个类似，但方向相反，将exit3转换为exit2。

## 4. 删除原始目录

```powershell
Remove-Item -Recurse -Force src/main/java/com/qius/exit2
Remove-Item -Recurse -Force src/main/java/com/qius/exit3
```

这些命令删除了原始目录，为移动重命名后的文件做准备。

## 5. 移动重命名后的文件

```powershell
Move-Item -Path src/main/java/com/qius/temp/exit2 -Destination src/main/java/com/qius/
Move-Item -Path src/main/java/com/qius/temp/exit3 -Destination src/main/java/com/qius/
```

这些命令将临时目录中处理好的文件移回原始位置。

## 6. 清理临时目录

```powershell
Remove-Item -Recurse -Force src/main/java/com/qius/temp
```

最后，这个命令删除了临时工作目录。

## 正则表达式的关键作用

在这个任务中，正则表达式起到了核心作用：

1. `package\s+com\.qius\.exit2` - 匹配包声明，其中`\s+`匹配一个或多个空格
2. `exit2_(\d+)` - 匹配类名并捕获数字部分，`(\d+)`捕获一个或多个数字
3. 在重命名文件时使用`$_.Name -replace 'exit2_(\d+)', 'exit3_$1'`保留了原文件名中的数字

PowerShell的`-replace`操作符使用正则表达式进行模式匹配和替换，大大简化了这个任务。通过一条命令，它可以同时处理文件内容和文件名的修改，提高了工作效率。

## 完整流程总结

1. 创建临时目录结构
2. 处理exit2目录中的文件，将其转换为exit3相关文件并放入临时目录
3. 处理exit3目录中的文件，将其转换为exit2相关文件并放入临时目录
4. 删除原始的exit2和exit3目录
5. 将临时目录中处理好的文件移回原位
6. 删除临时目录

这种方法的优点是通过使用临时目录进行转换，避免了在替换过程中出现文件覆盖或丢失的风险，是一种安全可靠的实现方式。 