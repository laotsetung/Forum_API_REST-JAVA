package br.com.lao.forum.controller;

import br.com.lao.forum.dao.TopicoDAO;
import br.com.lao.forum.dto.TopicoDTO;
import br.com.lao.forum.modelos.Topico;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import com.google.gson.JsonParser;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    //NOVO TÓPICO
    @PostMapping
    public ResponseEntity cadastroTopico(@RequestBody TopicoDTO tpco, UriComponentsBuilder uriBuilder){

        Topico novoTopico = new Topico(tpco.titulo(),tpco.msg(),tpco.autor(), tpco.curso());
        boolean podeInserir = false;

        try {
            TopicoDAO topDAO = new TopicoDAO();

            //Verifica se não existe um Tópico com este Titulo E Mensagem!
            podeInserir = topDAO.ValidaInserir(novoTopico.getTitulo(), novoTopico.getMsg());
            if(podeInserir){
                int novoID = topDAO.Inserir(novoTopico);
                if (novoID > 0){
                    topDAO.fechaConexao();
                    //return "Tópico Válido e incluido com Sucesso";

                    var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(novoID).toUri();

                    return ResponseEntity.created(uri).body(ListaUmTopico(novoID));
                }else{
                    topDAO.fechaConexao();
                    //return "Tópico Válido, porém não foi incluido devido a algum erro, por favor tente novamente.";
                    return ResponseEntity.internalServerError().build();
                }
            }else{
                topDAO.fechaConexao();
                //return "Um tópico com este TÍTULO e MENSAGEM já existe, portanto este tópico NÃO FOI CRIADO.";
                return ResponseEntity.badRequest().build();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //LISTAR TÓPICOS
    @GetMapping
    public ResponseEntity ListaTopicos(@RequestParam(required = false) int page){

        System.out.println(page);

        String txt = "[";
        try {
            System.out.println("Inicia DAO");
            TopicoDAO topDAO = new TopicoDAO();
            List<Topico> topicos = topDAO.SelectTopicos10(page);

            for(Topico t : topicos){
                if(txt.length()>1){
                    txt+=",\n";
                }
                txt += t.toString();

            }
            topDAO.fechaConexao();

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        txt += "]";
        //System.out.println(txt);

        //transforma o texto em JSON
        //e o JSON em texto e da return
        Gson g = new Gson();
        JsonArray jArray = (JsonArray) JsonParser.parseString(txt);

        if(txt.equals("[]")){
            //return "Tópico não encontrado!";
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(g.toJson(jArray));
        }
    }

    //LISTAR 1 TÓPICO
    @GetMapping("/{id}")
    public ResponseEntity ListaUmTopico(@PathVariable int id) {
        String txt = "[";
        try {
            System.out.println("Inicia DAO");
            TopicoDAO topDAO = new TopicoDAO();
            List<Topico> topicos = topDAO.SelectUmTopico(id);

            for(Topico t : topicos){
                txt += t.toString();
            }
            topDAO.fechaConexao();

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        txt += "]";
        //System.out.println(txt);

        //transforma o texto em JSON
        Gson g = new Gson();
        JsonArray jArray = (JsonArray) JsonParser.parseString(txt);

        if(txt.equals("[]")){
            //return "Tópico não encontrado!";
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(g.toJson(jArray));
        }
    }

    //DELETAR TÓPICO
    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable int id) {
        try {
            System.out.println("Inicia DAO");
            TopicoDAO topDAO = new TopicoDAO();
            int resultado = topDAO.deleteTopico(id);

            topDAO.fechaConexao();

            /* Retorno de String
            if(resultado == 0){
                return "Tópico não foi excluido! Provavelmente este ID de tópico não existe, verifique esta informação!";
            }else{
                return "Tópico Excluido com Sucesso!";
            }*/
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //ATUALIZAR TÓPICO
    @PutMapping("{id}")
    public ResponseEntity atualizarTopico(@RequestBody TopicoDTO tpco, @PathVariable int id){

        Topico atualizaTopico = new Topico(tpco.titulo(),tpco.msg(), tpco.autor(), tpco.curso());
        atualizaTopico.setId(id);
        boolean podeInserir = false;

        try {
            TopicoDAO topDAO = new TopicoDAO();

            //Verifica se não existe um Tópico com este Titulo E Mensagem!
            podeInserir = topDAO.ValidaInserir(atualizaTopico.getTitulo(), atualizaTopico.getMsg());
            if(podeInserir){
                if (topDAO.Atualiza(atualizaTopico) > 0){
                    topDAO.fechaConexao();
                    //return "Tópico Válido e Alterado com Sucesso";
                    return ResponseEntity.ok(ListaUmTopico(id));
                }else{
                    topDAO.fechaConexao();
                    //return "Tópico Válido, porém não foi alterado devido a algum erro, por favor verifique as informações e tente novamente.";
                    return ResponseEntity.internalServerError().build();
                }
            }else{
                topDAO.fechaConexao();
                //return "Um tópico com este TÍTULO e MENSAGEM já existe, portanto este tópico NÃO FOI CRIADO.";
                return ResponseEntity.internalServerError().build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
