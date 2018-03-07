
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static javafx.application.Application.launch;
import javafx.stage.FileChooser;

public class Encryption extends Application {

    StringBuilder outputString = new StringBuilder();
    StringBuilder sb = new StringBuilder();
    TextField tf0 = new TextField("");
    TextField tf1 = new TextField("");
    TextField tf2 = new TextField("");
    TextField tf3 = new TextField("");
    ArrayList<TextField> keys = new ArrayList<>();
    int[][] preTransform10 = new int[10][10];
    int[][] postTransform10 = new int[10][10];
    Button btEncrypt = new Button("Perform Encryption");

    //File chooser 
    TextField tfPath = new TextField();
    TextField tfOutputPath = new TextField();
    FileInputStream fis = null;
    Button btInputFile = new Button("Input File ->");
    Button btOutputFile = new Button("Output File ->");
    FileChooser fcInput = new FileChooser();
    String outputPath = "";
    FileChooser fcOutput = new FileChooser();

    public void start(Stage primaryStage) throws IOException {

        fcInput.setInitialDirectory(new File(".\\"));
        btInputFile.setOnAction(e -> {
            fcInput.setTitle("Source File");
            tfPath.setText(fcInput.showOpenDialog(primaryStage).toString());
        });

        fcOutput.setInitialDirectory(new File(".\\"));
        btOutputFile.setOnAction(e -> {
            fcOutput.setTitle("Save File Source");
            tfOutputPath.setText(fcOutput.showSaveDialog(primaryStage).toString());
        });
        GridPane gp0 = new GridPane();
        HBox hb0 = new HBox();
        VBox vb0 = new VBox();

        gp0.setHgap(5);
        gp0.setVgap(5);
        gp0.setPadding(new Insets(10, 10, 10, 10));

        for (int j = 0; j < 19; j++) {
            keys.add(new TextField(""));
            hb0.getChildren().add(keys.get(j));
        }
        hb0.setMaxWidth(520);
        hb0.setAlignment(Pos.CENTER);

        Label lbl0 = new Label("Key 1 --->");
        Label lbl1 = new Label("Key 2 --->");
        Label lbl2 = new Label("Key 3 --->");
        Label lbl3 = new Label("Key 4 --->");
        Label lbl4 = new Label("Main Key:");

        gp0.add(btInputFile, 0, 0);
        gp0.add(tfPath, 1, 0);
        gp0.add(btOutputFile, 2, 0);
        gp0.add(tfOutputPath, 3, 0);

        gp0.add(lbl0, 0, 2);
        gp0.add(lbl1, 2, 2);
        gp0.add(lbl2, 0, 4);
        gp0.add(lbl3, 2, 4);
        gp0.add(lbl4, 0, 6);

        gp0.add(tf0, 1, 2);
        gp0.add(tf1, 3, 2);
        gp0.add(tf2, 1, 4);
        gp0.add(tf3, 3, 4);

        //Add instructions
        VBox vbI = new VBox();

        Label lblIns0 = new Label("Instructions:");
        Label lblIns1 = new Label("1. Choose text file to be encrypted.");
        Label lblIns2 = new Label("2. Choose location to save encrypted file.");
        Label lblIns3 = new Label("3. Enter appropriate encryption keys(1-4 and main key).");
        Label lblIns4 = new Label("4. Press perform encryption button to encrypt file.");

        vbI.getChildren().addAll(lblIns0, lblIns1, lblIns2, lblIns3, lblIns4);
        vbI.setAlignment(Pos.TOP_LEFT);
        vbI.setSpacing(5);

        vb0.getChildren().add(vbI);
        vb0.getChildren().add(gp0);
        vb0.getChildren().add(hb0);
        vb0.getChildren().add(btEncrypt);
        vb0.setSpacing(5);
        vb0.setAlignment(Pos.CENTER);

        btEncrypt.setOnAction(e -> {
            int i = 0;
            String c;
            int missingzeros = 0;
            try {
                //New file input stream with file name

                fis = new FileInputStream(tfPath.getText());
                // read till the end of the file
                while ((i = fis.read()) != -1) {
                    // converts integer to binary char
                    c = Integer.toBinaryString(i);

                    //Appends character;
                    missingzeros = 8 - c.length();
                    for (int inc = 0; inc < missingzeros; inc++) {
                        sb.append("0");
                    }
                    sb.append(c);
                }

                System.out.println(sb);

            } catch (Exception ex) {
                // if any error occurs
                ex.printStackTrace();
            } finally {

                // releases all system resources from the streams
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception ex) {
                    }
                }
            }

            if (!checkKey5(tf0.getText())) {

                Label lblE0 = new Label("Error: Key 1 is invalid");
                Button bt0 = new Button("OK");

                BorderPane bp0 = new BorderPane();

                VBox vbE0 = new VBox();
                vbE0.getChildren().addAll(lblE0, bt0);
                vbE0.setAlignment(Pos.CENTER);
                vbE0.setSpacing(5);

                bp0.setCenter(vbE0);

                Scene scene0 = new Scene(bp0, 150, 100);
                Stage s0 = new Stage();
                s0.setScene(scene0);
                s0.show();
                bt0.setOnAction(a -> {
                    tf0.setText("");
                    s0.close();
                });

            }
            if (!checkKey5(tf1.getText())) {

                Label lblE1 = new Label("Error: Key 2 is invalid");
                Button bt1 = new Button("OK");

                BorderPane bp1 = new BorderPane();

                VBox vbE1 = new VBox();
                vbE1.getChildren().addAll(lblE1, bt1);
                vbE1.setAlignment(Pos.CENTER);
                vbE1.setSpacing(5);

                bp1.setCenter(vbE1);

                Scene scene1 = new Scene(bp1, 150, 100);
                Stage s1 = new Stage();
                s1.setScene(scene1);
                s1.show();
                bt1.setOnAction(a -> {
                    tf1.setText("");
                    s1.close();
                });
            }
            if (!checkKey5(tf2.getText())) {

                Label lblE2 = new Label("Error: Key 3 is invalid");
                Button bt2 = new Button("OK");

                BorderPane bp2 = new BorderPane();

                VBox vbE2 = new VBox();
                vbE2.getChildren().addAll(lblE2, bt2);
                vbE2.setAlignment(Pos.CENTER);
                vbE2.setSpacing(5);

                bp2.setCenter(vbE2);

                Scene scene2 = new Scene(bp2, 150, 100);
                Stage s2 = new Stage();
                s2.setScene(scene2);
                s2.show();
                bt2.setOnAction(a -> {
                    tf2.setText("");
                    s2.close();
                });
            }
            if (!checkKey5(tf3.getText())) {

                Label lblE3 = new Label("Error: Key 4 is invalid");
                Button bt3 = new Button("OK");

                BorderPane bp3 = new BorderPane();

                VBox vbE3 = new VBox();
                vbE3.getChildren().addAll(lblE3, bt3);
                vbE3.setAlignment(Pos.CENTER);
                vbE3.setSpacing(5);

                bp3.setCenter(vbE3);

                Scene scene3 = new Scene(bp3, 150, 100);
                Stage s3 = new Stage();
                s3.setScene(scene3);
                s3.show();
                bt3.setOnAction(a -> {
                    tf3.setText("");
                    s3.close();
                });
            }
            if (!bigBlockCheck(keys)) {

                Label lblE4 = new Label("Error: Main key is invalid");
                Button bt4 = new Button("OK");

                BorderPane bp4 = new BorderPane();

                VBox vbE4 = new VBox();
                vbE4.getChildren().addAll(lblE4, bt4);
                vbE4.setAlignment(Pos.CENTER);
                vbE4.setSpacing(5);

                bp4.setCenter(vbE4);

                Scene scene4 = new Scene(bp4, 150, 100);
                Stage s4 = new Stage();
                s4.setScene(scene4);
                s4.show();
                bt4.setOnAction(a -> {
                    for (int p = 0; p < keys.size(); p++) {
                        keys.get(p).setText("");
                    }
                    s4.close();
                });
            }
            buildMatrix(sb);

            //pad to multiple of 8
            if (outputString.length() % 200 == 100) {
                outputString.append("0000");
            }
            try {
                //Convert to chars and write to file
                BinaryStringToChars();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
            }
            Label lblSuccess = new Label("Successful Encryption");
            Button bt5 = new Button("OK");
            
            //Clearing string builder in case more than one run happens 
            sb.delete(0, sb.length() - 1);
            outputString.delete(0, sb.length() - 1);

            BorderPane bp5 = new BorderPane();

            VBox vbSuccess = new VBox();
            vbSuccess.getChildren().addAll(lblSuccess, bt5);
            vbSuccess.setAlignment(Pos.CENTER);
            vbSuccess.setSpacing(5);

            bp5.setCenter(vbSuccess);

            Scene scene4 = new Scene(bp5, 150, 100);
            Stage s5 = new Stage();
            s5.setScene(scene4);
            s5.show();
            bt5.setOnAction(a -> {
                s5.close();
            });
        });
        // Create a scene and place the pane in the stage
        Scene scene = new Scene(vb0, 550, 325);

        primaryStage.setTitle("Diagonal Transposition Encryption"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

    }

    public static void main(String[] args) {
        launch(args);
    }

    //Build a 10x10 matrix and then 5x5 matrix
    public void buildMatrix(StringBuilder s) {

        String[][] matrixTen = new String[10][10];

        //Capacity of the binary string and padding size determined
        int cap = s.length();
        int padding = 100 - (cap % 100);

        //Padding final matrix so that it is full
        for (int i = 0; i < padding; i++) {
            s.append("0");
        }

        //Determing number of sub matrices
        int numOfMatrices = s.length() / 100;

        //Stack of sub matrices to be filled as 10x10 is broken down
        String[][][] matrix = new String[numOfMatrices][10][10];

        int counter = 0;
        String ss = s.toString();

        //Filling each sub matrix with contents from 10x10 matrix
        for (int m = 0; m < numOfMatrices; m++) {
            for (int row = 0; row < 10; row++) {
                for (int column = 0; column < 10; column++) {
                    matrix[m][row][column] = String.valueOf(ss.charAt(counter));
                    counter++;
                }
            }
        }

        for (int m = 0; m < numOfMatrices; m++) {
            copyTo5x5(matrix, 0, 0, m, tf0.getText());    //Quad 1
            copyTo5x5(matrix, 0, 5, m, tf1.getText());    //Quad 2
            copyTo5x5(matrix, 5, 0, m, tf2.getText());    //Quad 3
            copyTo5x5(matrix, 5, 5, m, tf3.getText());    //Quad 4

            outputString.append(transform10x10());

        }
    }

    StringBuilder transform10x10() {
        StringBuilder tempString = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            System.arraycopy(preTransform10[i], 0, postTransform10[i], 0, 10);
        }

        int key;

        for (int i = 0; i < 9; i++) {
            key = Integer.parseInt(keys.get(i).getText());

            if (key != (i + 1)) {
                switch (key) {
                    case 19:
                        postTransform10[0][0] = preTransform10[9][9];
                        postTransform10[9][9] = preTransform10[0][0];
                        break;
                    case 18:
                        postTransform10[1][0] = preTransform10[9][8];
                        postTransform10[0][1] = preTransform10[8][9];
                        postTransform10[9][8] = preTransform10[1][0];
                        postTransform10[8][9] = preTransform10[0][1];
                        break;
                    case 17:
                        postTransform10[1][2] = preTransform10[7][9];
                        postTransform10[1][1] = preTransform10[8][8];
                        postTransform10[2][0] = preTransform10[9][7];
                        postTransform10[7][9] = preTransform10[1][2];
                        postTransform10[8][8] = preTransform10[1][1];
                        postTransform10[9][7] = preTransform10[2][0];
                        break;
                    case 16:
                        postTransform10[0][3] = preTransform10[6][9];
                        postTransform10[1][2] = preTransform10[7][8];
                        postTransform10[2][1] = preTransform10[8][7];
                        postTransform10[3][0] = preTransform10[9][6];
                        postTransform10[9][6] = preTransform10[3][0];
                        postTransform10[8][7] = preTransform10[2][1];
                        postTransform10[7][8] = preTransform10[1][2];
                        postTransform10[6][9] = preTransform10[0][3];
                        break;
                    case 15:
                        postTransform10[0][4] = preTransform10[5][9];
                        postTransform10[1][3] = preTransform10[6][8];
                        postTransform10[2][2] = preTransform10[7][7];
                        postTransform10[3][1] = preTransform10[8][6];
                        postTransform10[4][0] = preTransform10[9][5];
                        postTransform10[5][9] = preTransform10[0][4];
                        postTransform10[6][8] = preTransform10[1][3];
                        postTransform10[7][7] = preTransform10[2][2];
                        postTransform10[8][6] = preTransform10[3][1];
                        postTransform10[9][5] = preTransform10[4][0];
                        break;
                    case 14:
                        postTransform10[0][5] = preTransform10[4][9];
                        postTransform10[1][4] = preTransform10[5][8];
                        postTransform10[2][3] = preTransform10[6][7];
                        postTransform10[3][2] = preTransform10[7][6];
                        postTransform10[4][1] = preTransform10[8][5];
                        postTransform10[5][0] = preTransform10[9][4];
                        postTransform10[4][9] = preTransform10[0][5];
                        postTransform10[5][8] = preTransform10[1][4];
                        postTransform10[6][7] = preTransform10[2][3];
                        postTransform10[7][6] = preTransform10[3][2];
                        postTransform10[8][5] = preTransform10[4][1];
                        postTransform10[9][4] = preTransform10[5][0];
                        break;
                    case 13:
                        postTransform10[0][6] = preTransform10[3][9];
                        postTransform10[1][5] = preTransform10[4][8];
                        postTransform10[2][4] = preTransform10[5][7];
                        postTransform10[3][3] = preTransform10[6][6];
                        postTransform10[4][2] = preTransform10[7][5];
                        postTransform10[5][1] = preTransform10[8][4];
                        postTransform10[6][0] = preTransform10[9][3];
                        postTransform10[3][9] = preTransform10[0][6];
                        postTransform10[4][8] = preTransform10[1][5];
                        postTransform10[5][7] = preTransform10[2][4];
                        postTransform10[6][6] = preTransform10[3][3];
                        postTransform10[7][5] = preTransform10[4][2];
                        postTransform10[8][4] = preTransform10[5][1];
                        postTransform10[9][3] = preTransform10[6][0];
                        break;
                    case 12:
                        postTransform10[0][7] = preTransform10[2][9];
                        postTransform10[1][6] = preTransform10[3][8];
                        postTransform10[2][5] = preTransform10[4][7];
                        postTransform10[3][4] = preTransform10[5][6];
                        postTransform10[4][3] = preTransform10[6][5];
                        postTransform10[5][2] = preTransform10[7][4];
                        postTransform10[6][1] = preTransform10[8][3];
                        postTransform10[7][0] = preTransform10[9][2];
                        postTransform10[2][9] = preTransform10[0][7];
                        postTransform10[3][8] = preTransform10[1][6];
                        postTransform10[4][7] = preTransform10[2][5];
                        postTransform10[5][6] = preTransform10[3][4];
                        postTransform10[6][5] = preTransform10[4][3];
                        postTransform10[7][4] = preTransform10[5][2];
                        postTransform10[8][3] = preTransform10[6][1];
                        postTransform10[9][2] = preTransform10[7][0];
                        break;
                    case 11:
                        postTransform10[0][8] = preTransform10[1][9];
                        postTransform10[1][7] = preTransform10[2][8];
                        postTransform10[2][6] = preTransform10[3][7];
                        postTransform10[3][5] = preTransform10[4][6];
                        postTransform10[4][4] = preTransform10[5][5];
                        postTransform10[5][3] = preTransform10[6][4];
                        postTransform10[6][2] = preTransform10[7][3];
                        postTransform10[7][1] = preTransform10[8][2];
                        postTransform10[8][0] = preTransform10[9][1];
                        postTransform10[9][1] = preTransform10[8][0];
                        postTransform10[8][2] = preTransform10[7][1];
                        postTransform10[7][3] = preTransform10[6][2];
                        postTransform10[6][4] = preTransform10[5][3];
                        postTransform10[5][5] = preTransform10[4][4];
                        postTransform10[4][6] = preTransform10[3][5];
                        postTransform10[3][7] = preTransform10[2][6];
                        postTransform10[2][8] = preTransform10[1][7];
                        postTransform10[1][9] = preTransform10[0][8];
                        break;
                    default:
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tempString.append(postTransform10[i][j]);
            }
        }
        return tempString;
    }

    //Method responsible for transforming a 10x10 to four 5x5 matrices
    public void copyTo5x5(String[][][] s, int row, int column, int matrixNum, String key) {

        String[][] preTransform5 = new String[5][5];
        String[][] postTransform5 = new String[5][5];

        //Splitting 10x10 matrix into 5x5 and copying into a 5x5
        for (int r = row, i = 0; r < row + 5; r++, i++) {
            for (int c = column, j = 0; c < column + 5; c++, j++) {
                preTransform5[i][j] = s[matrixNum][r][c];
            }
        }
        for (int r0 = 0; r0 < 5; r0++) {
            System.arraycopy(preTransform5[r0], 0, postTransform5[r0], 0, 5);
        }
        for (int i = 0; i < 4; i++) {
            if (Character.getNumericValue(key.charAt(i)) != (i + 1)) {
                //Swap happens here
                switch (Character.getNumericValue(key.charAt(i))) {
                    case 9:
                        postTransform5[0][0] = preTransform5[4][4];
                        postTransform5[4][4] = preTransform5[0][0];
                        break;
                    case 8:
                        postTransform5[0][1] = preTransform5[3][4];
                        postTransform5[1][0] = preTransform5[4][3];
                        postTransform5[3][4] = preTransform5[0][1];
                        postTransform5[4][3] = preTransform5[1][0];
                        break;
                    case 7:
                        postTransform5[2][0] = preTransform5[4][2];
                        postTransform5[1][1] = preTransform5[3][3];
                        postTransform5[0][2] = preTransform5[2][4];
                        postTransform5[4][2] = preTransform5[2][0];
                        postTransform5[3][3] = preTransform5[1][1];
                        postTransform5[2][4] = preTransform5[0][2];
                        break;
                    case 6:
                        postTransform5[0][3] = preTransform5[1][4];
                        postTransform5[1][2] = preTransform5[2][3];
                        postTransform5[2][1] = preTransform5[3][2];
                        postTransform5[3][0] = preTransform5[4][1];
                        postTransform5[4][1] = preTransform5[3][0];
                        postTransform5[3][2] = preTransform5[2][1];
                        postTransform5[2][3] = preTransform5[1][2];
                        postTransform5[1][4] = preTransform5[0][3];
                        break;
                    default:

                }
            }
        }

        //Write transforms to 10 x 10 pretransformed matrix
        for (int r = row, i = 0; r < row + 5; r++, i++) {
            for (int c = column, j = 0; c < column + 5; c++, j++) {
                preTransform10[r][c] = Integer.parseInt(postTransform5[i][j]);
            }
        }
    }

    public boolean checkKey5(String key) {

        if (key.length() == 9) {

            if (Integer.parseInt("" + key.charAt((key.length() - 1) / 2)) == 5) {
            }

        } else {
            return false;
        }

        return true;
    }

    boolean bigBlockCheck(ArrayList<TextField> fields) {
        if (!fields.get(9).getText().equals("10")) {
            return false;
        }

        for (int i = 0; i < 19; i++) {
            if (fields.get(i).getText().equals(Integer.toString(i + 1))
                    || fields.get(i).getText().equals(Integer.toString(19 - i))) {
            } else {
                return false;
            }
        }

        return true;
    }

    void BinaryStringToChars() throws FileNotFoundException {

        java.io.File file = new java.io.File(tfOutputPath.getText());
        if (file.exists()) {
            // auto-delete file for convenience
            file.delete();
        }

        // Create a file
        java.io.PrintWriter output = new java.io.PrintWriter(file);

        StringBuilder b = new StringBuilder();
        int len = outputString.length();
        int i = 0;

        while (i + 8 < len) {
            char c = convert(outputString.substring(i, i + 8));
            i += 8;
            b.append(c);
            output.append(c);
        }
        // Close the file
        output.close();

    }

    private static char convert(String bs) {
        return (char) Integer.parseInt(bs, 2);
    }
}
