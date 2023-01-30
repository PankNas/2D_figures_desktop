package com.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import panknas.figs2D.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MainController {
    @FXML
    private Text nameOperation;
    @FXML
    private Canvas canvas;
    @FXML
    public Label result;
    public static CanvasDrawer canvasDrawer;

    @FXML
    public void initialize() throws Exception {
        canvasDrawer = new CanvasDrawer(canvas);
        canvasDrawer.update();
        log.info("initialized drawer");

        value = new ArrayList<>();
    }

    private void setDefaultValues() {
        nameOperation.setText("");
        result.setText("");
        value.clear();
    }

    public void openWindow(String nameFXML, String title) throws Exception {
        log.info("loading {} window", nameFXML);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(nameFXML));
        Scene scene = new Scene(fxmlLoader.load());

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle(title);
        window.showAndWait();

        canvasDrawer.update();
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("add button pressed");
        setDefaultValues();
        openWindow("addFigure.fxml", "Создание новой фигуры");
    }

    public void onSaveButtonClick(ActionEvent actionEvent) throws IOException {
        log.info("save button pressed");
        setDefaultValues();

        int size = HelloApplication.listFigures.size();
        try {
            if (size > 0) {
                FileWriter file = new FileWriter("figures.txt", false);
                file.append(Integer.toString(size)).append("\n");
                for (IShape fig : HelloApplication.listFigures) {
                    file.append(fig.toString()).append("\n");
                }
                file.flush();
                HelperFuncs.successAlert("Файл сохранён");
                log.info("saved to file successfully");
            }
            else
                throw new IOException("Ни одной фигуры не создано");
        }
        catch(IOException ex) {
            log.warn("file save error", ex);
            HelperFuncs.failAlert(ex.getMessage());
        }
    }

    public void onLoadButtonClick(ActionEvent actionEvent) {
        log.info("file load button pressed");
        setDefaultValues();
        try {
            HelloApplication.listFigures.clear();

            BufferedReader text = new BufferedReader(new FileReader("figures.txt"));
            String line;
            while((line = text.readLine()) != null) {
                updateListFiguresFrom_(line);
            }

            canvasDrawer.update();
            log.info("file loaded");
            HelperFuncs.successAlert("Файл загружен");
        } catch (IOException e) {
            HelperFuncs.failAlert(e.getMessage());
            log.warn("load file error", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateListFiguresFrom_(String line) throws Exception {
        Pattern pattern = Pattern.compile("\\.Circle|\\.NGon|\\.Polyline|\\.QGon|\\.Rectangle|\\.Segment" +
                "|\\.TGon|\\.Trapeze|\\d+(\\.\\d+)|-\\d+(\\.\\d+)");
        Matcher matcher = pattern.matcher(line);

        ArrayList<String> ints = new ArrayList<>();
        while (matcher.find()) {
            ints.add(matcher.group());
        }

        if (!ints.isEmpty()) {
            IShape figure = null;
            Point2D[] points = coords(ints);
            switch (ints.get(0)) {
                case ".Segment" -> figure = new Segment(points);
                case ".Polyline" -> figure = new Polyline(points);
                case ".NGon" -> figure = new NGon(points, String.valueOf(NGon.class));
                case ".TGon" -> figure = new TGon(points);
                case ".QGon" -> figure = new QGon(points);
                case ".Rectangle" -> figure = new Rectangle(points);
                case ".Trapeze" -> figure = new Trapeze(points);
                case ".Circle" -> figure = new Circle(points[0], Double.parseDouble(ints.get(ints.size() - 1)));
            }
            HelloApplication.listFigures.add(figure);
        }
    }

    private Point2D[] coords(List<String> ints) throws Exception {
        Point2D[] points = new Point2D[(ints.size() - 1) / 2];
        for (int i = 1, j = 0; i < ints.size() - 1; i += 2, j++) {
            double x = Double.parseDouble(ints.get(i));
            double y = Double.parseDouble(ints.get(i + 1));
            points[j] = new Point2D(new double[] {x, y});
        }
        return points;
    }

    public void onSaveImageButtonClick(ActionEvent actionEvent) {
        log.info("image save button pressed");
        setDefaultValues();

        File file = new File("figures.png");

        WritableImage im = new WritableImage((int)canvas.getWidth(), (int)this.canvas.getHeight());
        canvas.snapshot(null, im);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(im, null), "png", file);
            HelperFuncs.successAlert("Изображение сохранено");
            log.info("image saved successfully");
        } catch (IOException e) {
            log.warn("image save error", e);
            HelperFuncs.failAlert("Не удалось сохранить изображение");
        }
    }

    public void onMoveButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("move button pressed");
        setDefaultValues();
        openWindow("move.fxml", "Движение фигуры");
    }

    public void onDeleteButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("delete figure button pressed");
        setDefaultValues();
        openWindow("delete.fxml", "Удаление фигуры");
    }

    public void onClearButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("clear button pressed");
        setDefaultValues();
        if (HelloApplication.listFigures.size() == 0)
            HelperFuncs.failAlert("Ни одной фигуры не создано");
        else {
            HelloApplication.listFigures.clear();
            HelperFuncs.successAlert("Все фигуры успешно удалены");
        }
        canvasDrawer.update();
    }

    public static List<Object> value;

    private void setData(String str) {
        if (!value.isEmpty()) {
            nameOperation.setText(str);
            result.setText(String.valueOf(value.get(0)));
        }
    }

    public void onSquareButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("square button pressed");
        value.clear();
        openWindow("squareFig.fxml", "Площадь фигуры");
        setData("Площадь:");
    }

    public void onLengthButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("length button pressed");
        value.clear();
        openWindow("lengthFig.fxml", "Периметр фигуры");
        setData("Периметр:");
    }

    public void onCrossButtonClick(ActionEvent actionEvent) throws Exception {
        log.info("cross figures button pressed");
        value.clear();
        openWindow("crossFigs.fxml", "Пересечение фигур");
        setData("Пересечение:");
    }

    private final String dbClientURI = "mongodb://localhost:27017/";

    @FXML
    void onLoadDB(ActionEvent event) throws Exception {
        log.info("load database button pressed");
        MongoClient client = null;
        try {
            MongoClientURI uri = new MongoClientURI(dbClientURI);
            client = new MongoClient(uri);
            var collection = client.getDatabase("db").getCollection("shapes");
            Bson projections = Projections.exclude("_id");
            MongoCursor<Document> cur = collection.find().projection(projections).iterator(); // !!!
            var items = new ArrayList<IShape>();
            HelloApplication.listFigures.clear();

            while (cur.hasNext()) {
                var doc = cur.next();
                updateListFiguresFrom_(doc.toJson());
            }

            canvasDrawer.update();
            HelperFuncs.successAlert("Фигуры загружены из БД");
        }catch (MongoSocketException | MongoTimeoutException e){
            log.warn("connection to db failed");
            HelperFuncs.failAlert("Ошибка при подключении к базе данных");
        }
        catch (Throwable e) {
            HelperFuncs.failAlert("Ошибка при чтении фигур: " + e.getMessage());
            throw e;
        }

        finally {
            if (client != null){
                client.close();
            }
        }
    }

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @FXML
    void onSaveDB(ActionEvent event) throws JsonProcessingException {
        log.info("save database button pressed");
        MongoClient client = null;
        try {
            MongoClientURI uri = new MongoClientURI(dbClientURI);
            client = new MongoClient(uri);
            var collection = client.getDatabase("figures").getCollection("shapes");
            List<Document> shapes = new ArrayList<>();
            for (var fig : HelloApplication.listFigures) {
                var shape = mapper.writeValueAsString(fig);
                shapes.add(Document.parse(shape));
            }

            collection.drop();
            collection.insertMany(shapes);
            HelperFuncs.successAlert("Фигуры сохранены в БД");
        }catch (MongoSocketException | MongoTimeoutException e){
            log.warn("connection to db failed");
            HelperFuncs.failAlert("Ошибка при подключении к базе данных");
        }
        catch (Throwable e) {
            HelperFuncs.failAlert("Ошибка при записи фигур: " + e.getMessage());
            throw e;
        }
        finally {
            if (client != null){
                client.close();
            }

        }
    }
}