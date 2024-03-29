/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isp.lab9.exercise1.services;
import java.util.TreeMap;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Uses Lombok to get rid of boilerplate code.
 *
 * @author mihai.hulea
 * @author radu.miron
 */
@Data // it creates getters, setters, equals(), hashCode() and toString() (at compile time) // it creates the constructor with arguments for all the attributes (at compile time)
public class UserPortfolio {
    private BigDecimal cash;
    private Map<String, Integer> shares=new TreeMap<>(); // a map of number of shares by stock symbol


    public UserPortfolio(BigDecimal cash)
    {
        this.cash=cash;
    }

    public void addShares(String name,int nr)
    {

        shares.put(name,nr);
    }
}
