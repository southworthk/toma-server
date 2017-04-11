package com.usana.autotom;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kerrysouthworth on 1/3/17.
 */
public interface TomElementRepository extends GraphRepository<TomElement>
{
    //@Query ("MATCH p=shortestPath((b:BaseURL{name:'Hub Login'})-[*]-(s:Form{name:'oeStartForm'})) RETURN p")
    @Query ("MATCH p=shortestPath((b:BaseURL{name:'ESC Login'})-[*]->(s:Form{name:'Order Completed'})) RETURN p")
    public ArrayList<TomElement> getShortestPath();

    @Query("MATCH (h:Form{name:{0}})-[:CONTAINS]->(f:InputElement) RETURN f")
    public List<InputElement> getInputElements(String formName);

    @Query("MATCH (h:Form{name:{0}})-[:CONTAINS]->(f:SelectList) RETURN f")
    public List<SelectList> getSelectElements(String formName);

    @Query("MATCH (h:Form{name:{0}})-[:CONTAINS]->(f:Button) RETURN f")
    public Button getButton(String formName);

    @Query("MATCH (f:Form{name:\"hubLoginForm\"}) RETURN f")
    public Form getFormByName(String name);

    @Query("MATCH (i:InputElement{name:{0}})-[:ACCEPTS]->(v:ValidInput) RETURN v")
    public List<ValidInput> getValidInputElements(String name);
}
