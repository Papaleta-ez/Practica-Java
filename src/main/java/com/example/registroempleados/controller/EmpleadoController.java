package com.example.registroempleados.controller;

import com.example.registroempleados.database.DatabaseConnection;
import com.example.registroempleados.model.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmpleadoController {
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtCedula;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCargo;
    @FXML private ComboBox<String> cmbDepartamento;
    @FXML private TextField txtSalario;
    @FXML private DatePicker dpFechaContratacion;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, Long> colId;
    @FXML private TableColumn<Empleado, String> colNombres;
    @FXML private TableColumn<Empleado, String> colApellidos;
    @FXML private TableColumn<Empleado, String> colCedula;
    @FXML private TableColumn<Empleado, String> colCorreo;
    @FXML private TableColumn<Empleado, String> colTelefono;
    @FXML private TableColumn<Empleado, String> colCargo;
    @FXML private TableColumn<Empleado, String> colDepartamento;
    @FXML private TableColumn<Empleado, BigDecimal> colSalario;
    @FXML private TableColumn<Empleado, java.time.LocalDate> colFechaContratacion;
    @FXML private TableColumn<Empleado, String> colEstado;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cmbDepartamento.setItems(FXCollections.observableArrayList(
                "Administración", "Finanzas", "Recursos Humanos", "Tecnología", "Ventas", "Operaciones"));
        cmbEstado.setItems(FXCollections.observableArrayList("Activo", "Inactivo"));
        cargarEmpleados();
    }

    @FXML
    private void guardarEmpleado() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String departamento = cmbDepartamento.getValue();
        String estado = cmbEstado.getValue();
        if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || departamento == null
                || txtSalario.getText().isBlank() || dpFechaContratacion.getValue() == null || estado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Revise el formulario", "Complete nombres, apellidos, cédula, departamento, salario, fecha y estado.");
            return;
        }

        BigDecimal salario;
        try {
            salario = new BigDecimal(txtSalario.getText().trim());
            if (salario.signum() < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Salario inválido", "Ingrese un salario numérico igual o mayor que cero.");
            return;
        }

        String sql = "INSERT INTO empleado (nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombres);
            statement.setString(2, apellidos);
            statement.setString(3, cedula);
            setNullableString(statement, 4, txtCorreo.getText());
            setNullableString(statement, 5, txtTelefono.getText());
            setNullableString(statement, 6, txtCargo.getText());
            statement.setString(7, departamento);
            statement.setBigDecimal(8, salario);
            statement.setDate(9, Date.valueOf(dpFechaContratacion.getValue()));
            statement.setString(10, estado);
            statement.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro guardado", "El empleado se guardó correctamente.");
            limpiarFormulario();
            cargarEmpleados();
        } catch (SQLException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudo guardar", detalleError(ex));
        }
    }

    @FXML
    private void cargarEmpleados() {
        ObservableList<Empleado> empleados = FXCollections.observableArrayList();
        String sql = "SELECT id, nombres, apellidos, cedula, correo, telefono, cargo, departamento, salario, fecha_contratacion, estado FROM empleado ORDER BY id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Date fecha = result.getDate("fecha_contratacion");
                empleados.add(new Empleado(result.getLong("id"), result.getString("nombres"),
                        result.getString("apellidos"), result.getString("cedula"), result.getString("correo"),
                        result.getString("telefono"), result.getString("cargo"), result.getString("departamento"),
                        result.getBigDecimal("salario"), fecha == null ? null : fecha.toLocalDate(), result.getString("estado")));
            }
            tblEmpleados.setItems(empleados);
        } catch (SQLException ex) {
            tblEmpleados.setItems(empleados);
            mostrarAlerta(Alert.AlertType.ERROR, "No se pudieron cargar los empleados", detalleError(ex));
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCargo.clear();
        cmbDepartamento.getSelectionModel().clearSelection();
        txtSalario.clear();
        dpFechaContratacion.setValue(null);
        cmbEstado.getSelectionModel().clearSelection();
    }

    private void setNullableString(PreparedStatement statement, int index, String value) throws SQLException {
        String clean = value == null ? "" : value.trim();
        if (clean.isEmpty()) statement.setNull(index, java.sql.Types.VARCHAR);
        else statement.setString(index, clean);
    }

    private String detalleError(SQLException ex) {
        String message = Optional.ofNullable(ex.getMessage()).orElse("Error de base de datos.");
        if (message.contains("empleado_cedula_key")) return "La cédula ya está registrada. Ingrese una cédula diferente.";
        return message;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
