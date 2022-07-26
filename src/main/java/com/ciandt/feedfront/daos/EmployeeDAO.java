package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee> {
    private String repositorioPath = "src/main/resources/data/employee/";

    private static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    private static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Employee> employees = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                employees.add(buscar(file));
            }

            paths.close();
        } catch (IOException e) {
            throw new EntidadeNaoSerializavelException();
        } catch (EmployeeNaoEncontradoException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        Employee employee;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            employee = (Employee) inputStream.readObject();

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                throw new EmployeeNaoEncontradoException("Employee não encontrado");
            }

            throw new ArquivoException("");
        }

        return employee;
    }

    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        ObjectOutputStream outputStream = null;

        try {
            outputStream = getOutputStream(employee.arquivo);
            outputStream.writeObject(employee);

            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return employee;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        try{
            buscar(id);
            new File(String.format("%s%s.byte", repositorioPath, id)).delete();
        }catch (IOException e){
            throw new EntidadeNaoSerializavelException();
        }
        return true;
    }
}
