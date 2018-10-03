/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.inli.microservices.axon.event;

/**
 *
 * @author indika
 */
public class AccountCreatedEvent {

    public final String id;
    public final String accountCreator;
    public final double balance;

    public AccountCreatedEvent(String id, String accountCreator, double balance) {
        this.id = id;
        this.accountCreator = accountCreator;
        this.balance = balance;
    }
}
