-- 1. Todos los empleados
SELECT * FROM empleado;

-- 2. Nombres, apellidos y cargo
SELECT nombres, apellidos, cargo FROM empleado;

-- 3. Empleados de un departamento
SELECT * FROM empleado WHERE departamento = 'Tecnología';

-- 4. Salario mayor al valor indicado
SELECT * FROM empleado WHERE salario > 20000;

-- 5. Ordenados por salario descendente
SELECT * FROM empleado ORDER BY salario DESC;

-- 6. Cantidad total de empleados
SELECT COUNT(*) AS total_empleados FROM empleado;

-- 7. Salario promedio
SELECT AVG(salario) AS salario_promedio FROM empleado;

-- 8. Suma de salarios
SELECT SUM(salario) AS suma_salarios FROM empleado;

-- 9. Empleados activos
SELECT * FROM empleado WHERE estado = 'Activo';

-- 10. Cantidad de empleados por departamento
SELECT departamento, COUNT(*) AS cantidad
FROM empleado
GROUP BY departamento
ORDER BY departamento;

-- Consultas adicionales para probar desde Java
SELECT * FROM empleado WHERE estado = 'Activo' ORDER BY apellidos;
SELECT * FROM empleado WHERE departamento = 'Finanzas';
SELECT * FROM empleado WHERE salario > 20000 ORDER BY salario DESC;
SELECT * FROM empleado ORDER BY apellidos, nombres;
