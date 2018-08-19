package com.grupo1.tweetwood_back.services;



import com.grupo1.tweetwood_back.modules.Genero;
import com.grupo1.tweetwood_back.modules.KeyWord;
import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.modules.Tweet;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
//import javafx.geometry.Pos;
import javassist.compiler.ast.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.*;

@RestController
@RequestMapping(value = "/peliculas")
public class PeliculaServices {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private KeyWordRepository keyWordRepository;
    @Autowired
    private GeneroRepository generoRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getPeliculas(){
        return this.peliculaRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Pelicula getPelicula(@PathVariable Long id){
        return this.peliculaRepository.findPeliculaById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createPeliculas(@RequestBody Pelicula pelicula){
        Map<String,Object> response = new HashMap<>();
        List<KeyWord> keyWords = pelicula.getKeywords();
        Pelicula peliFromRepo;
        if(this.peliculaRepository.findPeliculaByNombre(pelicula.getNombre()) == null){

            for(Genero genero: pelicula.getGeneros()){
                Genero gen = this.generoRepository.findGeneroById(genero.getId());
                System.out.println("Nombre: "+gen.getNombre());
                pelicula.addGenero(gen);
                gen.addPelicula(pelicula);
            }
            for(KeyWord key: keyWords){
                System.out.println("Key: "+key.getPalabra());
                key.setPelicula(pelicula);
            }
            pelicula.setDisponible(true);
            Pelicula peli = this.peliculaRepository.save(pelicula);
            this.keyWordRepository.saveAll(keyWords);
            response.put("status","Added");
            response.put("pelicula",peli);
        }else{
            response.put("status","Error, the movie exists");
            response.put("pelicula",pelicula);
        }
        return response;

    }


    @CrossOrigin
    @RequestMapping(value = "/disponibles",method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getDisponibles(){
        List<Pelicula> peliculas = this.peliculaRepository.findAll();
        List<Pelicula> peliculasDisponibles = new ArrayList<>();
        for(Pelicula peli: peliculas){
            if(peli.isDisponible()){
                peliculasDisponibles.add(peli);
            }
        }
        return peliculasDisponibles;
    }

    @CrossOrigin
    @RequestMapping(value = "/disponible",method = RequestMethod.PUT)
    @ResponseBody
    public Pelicula setDisponible(@RequestBody Map<String,Long> msg){
        Pelicula peli = this.peliculaRepository.findPeliculaById(msg.get("id_pelicula"));
        peli.setDisponible(true);
        return this.peliculaRepository.save(peli);
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAll(){
        this.peliculaRepository.deleteAll();
    }

    @CrossOrigin
    @RequestMapping(value = "{id}/getKeywords",method = RequestMethod.GET)
    @ResponseBody
    public List<KeyWord> getKeywords(@PathVariable Long id){
        return this.peliculaRepository.findPeliculaById(id).getKeywords();
    }

    @CrossOrigin
    @RequestMapping(value = "{id}/addKeyword", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula addKeyword(@PathVariable Long id, @Valid @RequestBody  KeyWord keyWord){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);
        keyWord.setPelicula(pelicula);
        this.keyWordRepository.save(keyWord);
        List<KeyWord> keywords = pelicula.getKeywords();
        keywords.add(keyWord);
        pelicula.setKeywords(keywords);
        return pelicula;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/addKeywords", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula addKeywords(@PathVariable Long id, @Valid @RequestBody List<KeyWord> keywords){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);
        List<KeyWord> keywordsFromPelicula = pelicula.getKeywords();
        ArrayList<KeyWord> keyWordsToRemove = new ArrayList<>();

        for( KeyWord keyWord : keywords ){
            if(this.keyWordRepository.existsKeyWordByPalabra(keyWord.getPalabra())){
                keyWordsToRemove.add(keyWord);
            }
        }
        keywords.removeAll(keyWordsToRemove);
        this.keyWordRepository.saveAll(keywords);

        keywordsFromPelicula.addAll(keywords);
        pelicula.setKeywords(keywordsFromPelicula);
        return pelicula;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/addGeneros")
    @ResponseBody
    public Pelicula addGeneros(@PathVariable Long id, @Valid @RequestBody List<Genero> generos ){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);

        ArrayList<Genero> generosToRemove = new ArrayList<>();

        for(Genero genero: generos){
            //Si no esta en el repositorio quiere decir que no existe.
            if(!this.generoRepository.existsGeneroByNombre(genero.getNombre())){
                generosToRemove.add(genero);
            }else{
                if(genero.getPeliculas() == null){
                    ArrayList<Pelicula> peliculas = new ArrayList<>();
                    peliculas.add(pelicula);
                    genero.setPeliculas(peliculas);
                }else{
                    List<Pelicula> peliculas = genero.getPeliculas();
                    peliculas.add(pelicula);
                    genero.setPeliculas(peliculas);
                }

            }
        }
        //Se remueven aquellos que no existen
        generos.removeAll(generosToRemove);
        List<Genero> generosFromPelicula = pelicula.getGeneros();
        generosFromPelicula.addAll(generos);

        return pelicula;
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,Object> deletePelicula(@RequestBody Map<String,String> msg){
        Long id_pelicula = Long.parseLong(msg.get("id_pelicula"));
        Pelicula peli = this.peliculaRepository.findPeliculaById(id_pelicula);
        Map<String,Object> response = new HashMap<>();
        if(peli != null){
            for(Genero genero: peli.getGeneros()){
                genero.removePelicula(peli);
            }
            for(KeyWord key: peli.getKeywords()){
                this.keyWordRepository.delete(key);//Se elimina la keyword
            }
            peli.setDisponible(false);
            response.put("status","deleted");
            response.put("pelicula",peli);
            this.peliculaRepository.save(peli);
        }else{
            response.put("status","Error: doesn't exist");
            response.put("pelicula",peli);
        }
        return response;


    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updatePelicula(@RequestBody Pelicula pelicula){
        Pelicula peliculaFromRepo;
        Map<String,Object> response = new HashMap<>();
        Pelicula peli = this.peliculaRepository.findPeliculaById(pelicula.getId());
        peli.setNombre(pelicula.getNombre());
        peli.setRestriccion(pelicula.getRestriccion());
        peli.setIdApi(pelicula.getIdApi());
        if(pelicula.getImg() != "") {
            peli.setImg(pelicula.getImg());
        }
        List<KeyWord> keysToAdd = new ArrayList<>();
        List<KeyWord> keysFromFront = pelicula.getKeywords();
        for(KeyWord key: peli.getKeywords()){
            key.setPelicula(null);
        }
        for(int x = 0; x<keysFromFront.size(); x++){
            KeyWord actualKey = keysFromFront.get(x);
            if(this.keyWordRepository.existsKeyWordByPalabraAndAndPelicula(actualKey.getPalabra(),peli)){
                System.out.println("Existe aqui");
                KeyWord keyToAdd =this.keyWordRepository.findKeyWordByPalabraAndPelicula(actualKey.getPalabra(),peli);
                keyToAdd.setPelicula(peli);
                keysToAdd.add(keyToAdd);
            }else{
                System.out.println("else");
                actualKey.setPelicula(peli);
                keysToAdd.add(this.keyWordRepository.save(actualKey));

            }
        }
        //Se quitan aquellas que no estan en la consulta.

        peli.setKeywords(keysToAdd);
        //Se procesan los generos
        //Se quitan todos los generos.
        for(Genero gen: peli.getGeneros()){
            gen.removePelicula(peli);
        }
        peli.setGeneros(null);
        //Se toman los generos que existen.
        List<Genero> generosToAdd = new ArrayList<>();
        for(Genero gen: pelicula.getGeneros()){
            Genero genFromRepo =this.generoRepository.findGeneroById(gen.getId());
            genFromRepo.addPelicula(peli);
            generosToAdd.add(genFromRepo);
        }
        peli.setGeneros(generosToAdd);


        this.peliculaRepository.save(peli);
        response.put("pelicula",peli);



//
//        if((peliculaFromRepo = this.peliculaRepository.findPeliculaById(pelicula.getId())) != null){
//
//            //Si efectivamente la pelicula que se esta alterando existe.
//            //updateKeywords
//            List<KeyWord> keyWords = peliculaFromRepo.getKeywords();
//            for(int x = 0; x<pelicula.getKeywords().size(); x++){
//                for(int y=0; y<keyWords.size(); y++){
//                    if(keyWords.get(y).getPalabra().compareTo(pelicula.getKeywords().get(x).getPalabra()) == 0){
//                        //Si la keyword ya existe
//
//
//                    }
//                }
//            }
//
//
//
//            for(KeyWord keyWord: pelicula.getKeywords()){
//                if(!this.keyWordRepository.existsKeyWordByPalabraAndAndPelicula(keyWord.getPalabra(),peliculaFromRepo)){
//                    keyWord.setPelicula(peliculaFromRepo);
//                    this.keyWordRepository.save(keyWord);
//                }else{
//                    System.out.println("La keyword ya Existe!");
//                }
//            }
//            pelicula.setKeywords(pelicula.getKeywords());
//            List<Genero> repoGeneros = peliculaFromRepo.getGeneros();
//            for(Genero repoGen: repoGeneros){
//                repoGen.removePelicula(peliculaFromRepo); //Se elimina la pelicula.
//                peliculaFromRepo.removeGenero(repoGen); //Se elimina el genero
//            }
//            for(Genero gen: pelicula.getGeneros()){
//                Genero generoFromRepo = this.generoRepository.findGeneroById(gen.getId());
//                generoFromRepo.addPelicula(peliculaFromRepo);
//                peliculaFromRepo.addGenero(generoFromRepo);
//            }
//            peliculaFromRepo.setNombre(pelicula.getNombre());
//            peliculaFromRepo.setRestriccion(pelicula.getRestriccion());
//
//            this.peliculaRepository.save(peliculaFromRepo);
//            response.put("status","updated");
//            response.put("pelicula",peliculaFromRepo);
//
//        }else{
//            response.put("status","Error, no existe");
//            response.put("pelicula",peliculaFromRepo);
//        }
        return response;


    }


    @CrossOrigin
    @RequestMapping(value = "/mostValuated",method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getMostValuated(){
        List<Pelicula> peliculas = this.peliculaRepository.findAll();
        List<Pelicula> peliculasResponse = new ArrayList<>();
        //Se ordena
        if(peliculas == null){
            System.out.println("peliculas nulas");
        }else{
            Collections.sort(peliculas,new SortByValuePelicula());
        }

        for(int x = 0; (x<peliculas.size() && x<10); x++){
            peliculasResponse.add(peliculas.get(x));
        }
        return peliculasResponse;
    }

    @CrossOrigin
    @RequestMapping(value = "/mostTweeted",method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getMostTweets(){
        List<Pelicula> peliculas = this.peliculaRepository.findAll();
        List<Pelicula> peliculasResponse = new ArrayList<>();
        Collections.sort(peliculas,new SortByNumTweets());
        for(int x = 0; ( x<10 && x<peliculas.size()); x++){
            peliculasResponse.add(peliculas.get(x));
        }
        return peliculasResponse;
    }


}
class SortByValuePelicula implements Comparator<Pelicula>{

    @Override
    public int compare(Pelicula o1, Pelicula o2) {
        
        return (o2.getValue().intValue() - o1.getValue().intValue());
    }
}
class SortByNumTweets implements Comparator<Pelicula>{

    @Override
    public int compare(Pelicula o1, Pelicula o2) {
        return o2.getNumTweets().intValue() - o1.getNumTweets().intValue();
    }
}

