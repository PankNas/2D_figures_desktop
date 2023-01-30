# 2D_figures_desktop
## О проекте
Приложение позволяет проводить различные манипуляции с 2D фигурами: 
1. создать фигуру по значениям координат вершин,
2. удалить объект,
3. сдвинуть на вектор, повернуть на угол или зеркально отобразить фигуру относительно одной из осей,
4. опеределить пересечение двух однотипных фигур,
5. высчитать площадь, периметр у выбранной фигуры,
6. загрузить/сохраенить данные о координатах из/в файл(а) и БД,
7. сохраенить изображения со всеми фигурами.
## Реализация
Приложение написано при помощи Java. Для БД используется MongoDB.
## Инструкция к запуску
Замечание: действия по запуску акутальны для IntelliJ IDEA, для других IDE возможны отличия.
1. Клонировать проект на локальный компьютер и настроить maven для папок gui и model
2. Подключить библиотеку с фигурами model-1.0-SNAPSHOT<br>
2.1 В IDE перейти в <blockquote>File/Project Structure/Libraries<br></blockquote>
2.2 Нажать на значок плюса и выбрать Java<br>
2.3 Выбрать <blockquote>model\target\model-1.0-SNAPSHOT<br></blockquote>
3. Запустить на выполнение в IDE файл <blockquote>..\gui\src\main\java\com\gui\HelloApplication.java</blockquote>
