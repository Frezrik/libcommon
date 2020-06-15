## 1.check CodeStyle
* File -> Setting -> Editor -> Code Style -> Java导入SquareAndroid.xml
* File -> Setting -> Plugins安装CheckStyle-IDEA插件，restart
* File -> Setting -> Ohter Settings -> Checkstyle,勾选Google Checks
* 提交代码时选中Reformat code和Optimize imports格式化代码

## 2.编码注释
* File -> Setting -> Editor -> File and Codee Templates -> Class
```
#if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

#if (${IMPORT_BLOCK} != "")${IMPORT_BLOCK}
#end
/**
* @ClassName: ${NAME}
* @Description: java类作用描述
* @Author: ${USER}
* @Date: ${DATE} ${TIME}
*/
#parse("File Header.java")
#if (${VISIBILITY} == "PUBLIC")public #end #if (${ABSTRACT} == "TRUE")abstract #end #if (${FINAL} == "TRUE")final #end class ${NAME} #if (${SUPERCLASS} != "")extends ${SUPERCLASS} #end #if (${INTERFACES} != "")implements ${INTERFACES} #end {
}
```

* File -> Setting -> Editor -> Copyright -> Copyright Profiles新建一个
	* Apache 2.0
```
$module.name

Copyright (c) $today.year  frezrik@126.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

	* GPL-3.0
```
$module.name

Copyright (C) $today.year  frezrik@126.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
```