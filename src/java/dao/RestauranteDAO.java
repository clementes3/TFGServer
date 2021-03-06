/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.DAO.c;
import dominio.Mesa;
import dominio.Restaurante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 *
 * @author Alvaro
 */
public class RestauranteDAO extends DAO{
    
    private static final String QUERY_FIND_ALL_RESTAURANTES = "SELECT * FROM restaurantes";
    private static final String QUERY_FIND_RESTAURANTE_BY_ID = "SELECT * FROM restaurantes WHERE id= ? ";
    private static final String QUERY_FIND_MESAS_BY_RESTAURANTE_ID = "SELECT id, maxComensales FROM mesas WHERE restaurante = ? ";
        
    
    public RestauranteDAO() throws ClassNotFoundException, SQLException{
        super();
    }
    
    public Collection<Restaurante> findAllRestaurantes() throws SQLException{
        TreeSet<Restaurante> set;
        try (PreparedStatement ps = c.prepareStatement(QUERY_FIND_ALL_RESTAURANTES)) {
            ResultSet rs = ps.executeQuery();
            set = new TreeSet();
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String descripcion = rs.getString("descripcion");
                String categoria = rs.getString("categoria");
                String horaInicio = rs.getString("horaInicio");
                String horaFin = rs.getString("horaFin");
                
                //String horario = rs.getString("horario");
                
                //TODO añadir horario
                set.add(new Restaurante(id, nombre, direccion, telefono, descripcion, categoria, horaInicio, horaFin));
            }
        }
        
        return set;
    }
    
    public Restaurante findRestauranteById(int id) throws SQLException{
        ResultSet rs;
        String nombre;
        String direccion;
        String telefono;
        String descripcion;
        String categoria;
        String horaInicio;
        String horaFin;
        
        try (PreparedStatement ps = c.prepareStatement(QUERY_FIND_RESTAURANTE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            nombre = null;
            direccion = null;
            telefono = null;
            descripcion = null;
            categoria = null;
            horaInicio = null;
            horaFin = null;
            while(rs.next()){
                nombre = rs.getString("nombre");
                direccion = rs.getString("direccion");
                telefono = rs.getString("telefono");
                descripcion = rs.getString("descripcion");
                categoria = rs.getString("categoria");
                horaInicio = rs.getString("horaInicio");
                horaFin = rs.getString("horaFin");
                
            }   rs.close();
        }
        //TODO añadir horario
        Restaurante restaurante = new Restaurante(id, nombre, direccion, telefono, descripcion, categoria, horaInicio, horaFin);
             
        return restaurante;
    }
    
    public Restaurante findRestauranteByName(String nombre) throws SQLException{
        /**
         * Devuelve el primer restaurante con que coincida con el nombre
         */
        int id;
        try (PreparedStatement ps = c.prepareStatement(QUERY_FIND_RESTAURANTE_BY_ID)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            id = -1;
            while(rs.next()){            
                id = rs.getInt("id");
                break;
            }
        }
        if(id != -1)
            return findRestauranteById(id);
        else{
            return null;
        }
    }
    
    public Collection<Mesa> findAllMesasFromRestaurante(int id) throws SQLException {
        ArrayList<Mesa> mesas;
        try (PreparedStatement ps = c.prepareStatement(QUERY_FIND_MESAS_BY_RESTAURANTE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            mesas = new ArrayList();
            while(rs.next()){
                int mesaId = rs.getInt("id");
                int maxComensales = rs.getInt("maxComensales");
                Mesa mesa = new Mesa(mesaId, maxComensales);
                mesas.add(mesa);
            }   rs.close();
        }
        return mesas;
    }
    
}
