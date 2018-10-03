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
public class AccountClosedEvent {

    public final String id;

    public AccountClosedEvent(String id) {
        this.id = id;
    }
}
