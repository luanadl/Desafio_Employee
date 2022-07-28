package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.excecoes.FeedbackNaoEncontradoException;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private String repositorioPath = "src/main/resources/data/feedback/";

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
    public List<Feedback> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                feedbacks.add(buscar(file));
            }

            paths.close();
        } catch (IOException e) {
            throw new EntidadeNaoSerializavelException();
        } catch (FeedbackNaoEncontradoException e) {
            throw new RuntimeException(e);
        }

        return feedbacks;
    }

    public Feedback buscar(String id) throws IOException, EntidadeNaoSerializavelException, FeedbackNaoEncontradoException {
        Feedback feedback;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            feedback = (Feedback) inputStream.readObject();

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                throw new IOException("Feedback não encontrado");
            }

            throw new ArquivoException("");
        }

        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws IOException, EntidadeNaoSerializavelException {
        ObjectOutputStream outputStream = null;

        try {
            outputStream = getOutputStream(feedback.arquivo);
            outputStream.writeObject(feedback);

            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return feedback;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        try{
            buscar(id);
            new File(String.format("%s%s.byte", repositorioPath, id)).delete();
        }catch (IOException | FeedbackNaoEncontradoException e){
            throw new EntidadeNaoSerializavelException();
        }
        return true;
    }

}
