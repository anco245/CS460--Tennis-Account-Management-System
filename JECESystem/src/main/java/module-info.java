module com.example.jecesystem {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;

  opens com.example.jecesystem to javafx.fxml;
  exports com.example.jecesystem;
}
